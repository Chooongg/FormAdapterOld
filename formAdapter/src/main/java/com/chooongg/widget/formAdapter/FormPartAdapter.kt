package com.chooongg.widget.formAdapter

import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.item.GroupForm
import com.chooongg.widget.formAdapter.style.Style
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

class FormPartAdapter internal constructor(
    val globalAdapter: BaseFormAdapter,
    val style: Style
) : RecyclerView.Adapter<FormViewHolder>() {

    companion object {
        const val NOTIFY_PAYLOADS = "update"
    }

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    private val itemFactoryCache = ItemFactoryCache()

    internal val itemTypeLookup = ArraySet<ItemViewType>()

    internal lateinit var creator: PartCreator

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)
        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<FormItem>() {
        override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem) = false
        override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem) =
            oldItem.name == newItem.name && oldItem.field == newItem.field && oldItem.groupIndex == newItem.groupIndex
    }).build())

    fun submitList(block: PartCreator.() -> Unit) {
        submitList(PartCreator().apply(block))
    }

    fun submitList(creator: PartCreator) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        this.creator = creator
        update()
    }

    fun update() {
        itemFactoryCache.clear()
        if (!this::creator.isInitialized) {
            asyncDiffer.submitList(null)
            return
        }
        val partIndex = globalAdapter.indexPartOf(this)
        val tempList = mutableListOf<MutableList<FormItem>>()
        if (creator.dynamicPart) {
            if (creator.groups.size < creator.dynamicPartMinGroupCount) {
                if (creator.dynamicPartCreateGroupBlock != null) {
                    creator.createGroup { creator.dynamicPartCreateGroupBlock!!.invoke(this) }
                }
            }
        }
        creator.groups.forEachIndexed { groupIndex, group ->
            val groupList = ArrayList<FormItem>()
            val partName = if (creator.dynamicPart) {
                creator.dynamicPartNameFormatBlock?.invoke(creator.partName, groupIndex)
                    ?: "${creator.partName ?: ""}${groupIndex + 1}"
            } else creator.partName
            if (creator.partName != null || creator.dynamicPart) {
                groupList.add(FormGroupTitle(partName ?: "", null).apply {

                })
            }
            group@ for (item in group) {
                item.groupIndex = -1
                if (!item.isRealVisible(globalAdapter.isEditable)) continue@group
                if (item is GroupForm) {
                    val singleGroup = ArrayList<FormItem>()
                    singleLine@ for (it in item.items) {
                        if (!it.isRealVisible(globalAdapter.isEditable)) continue@singleLine
                        singleGroup.add(it)
                    }
                    if (singleGroup.isEmpty()) continue@group
                    var maxWidget = 0
                    singleGroup.forEachIndexed { index, formItem ->
                        maxWidget += formItem.spanWeight
                        formItem.singleLineCount = singleGroup.size
                        formItem.singleLineIndex = index
                        groupList.add(formItem)
                    }
                    if (singleGroup.size == 1) {
                        singleGroup[0].spanSize = 120
                    } else {
                        singleGroup.forEach {
                            it.spanSize = (120 / maxWidget) * it.spanWeight
                        }
                        if (120 % maxWidget != 0) {
                            for (i in 0 until 120 % maxWidget) {
                                singleGroup[i % singleGroup.size].spanSize += 1
                            }
                        }
                    }
                } else groupList.add(item)
            }
            while (groupList.size > 0 && !groupList[0].isShowOnEdge) {
                groupList.removeAt(0)
            }
            while (groupList.size > 1 && !groupList[groupList.lastIndex].isShowOnEdge) {
                groupList.removeAt(groupList.lastIndex)
            }
            groupList.forEachIndexed { index, formItem ->
                formItem.boundary.top = if (index == 0) {
                    if (partIndex == 0) Boundary.GLOBAL else Boundary.LOCAL
                } else Boundary.NONE
                if (formItem.singleLineCount > 1) {
                    if (formItem.singleLineIndex == 0) {
                        formItem.boundary.start = Boundary.GLOBAL
                    } else {
                        if (index != 0) {
                            formItem.boundary.top =
                                groupList[index - formItem.singleLineIndex].boundary.top
                        }
                        formItem.boundary.start = Boundary.NONE
                    }
                } else {
                    formItem.boundary.start = Boundary.GLOBAL
                    formItem.boundary.end = Boundary.GLOBAL
                }
            }
            for (i in groupList.lastIndex downTo 0) {
                groupList[i].boundary.bottom = if (i == groupList.lastIndex) {
                    if (partIndex == globalAdapter.adapter.adapters.lastIndex) Boundary.GLOBAL else Boundary.LOCAL
                } else Boundary.NONE
                if (groupList[i].singleLineCount > 1) {
                    if (groupList[i].singleLineIndex == groupList[i].singleLineCount - 1) {
                        groupList[i].boundary.end = Boundary.GLOBAL
                    } else {
                        if (i != groupList.lastIndex) {
                            groupList[i].boundary.bottom =
                                groupList[i + groupList[i].singleLineCount - groupList[i].singleLineIndex - 1].boundary.bottom
                        }
                        groupList[i].boundary.end = Boundary.NONE
                    }
                }
            }
            tempList.add(groupList)
        }
        if (creator.dynamicPart) {
            tempList.forEachIndexed { index, group ->
                group.forEachIndexed { position, item ->
                    item.groupIndex = index
                    item.itemPosition = position
                }
            }
        }
        asyncDiffer.submitList(ArrayList<FormItem>().apply { tempList.forEach { addAll(it) } })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateItemParent(parent)
        val item = itemFactoryCache.get(viewType)
        val typesetLayout = if (item.isNeedToTypeset) {
            (item.typeset ?: style.defaultTypeset)
                ?.onCreateItemTypesetParent(styleLayout ?: parent)
        } else null
        if (styleLayout != null && typesetLayout != null) styleLayout.addView(typesetLayout)
        val view = item.onCreateItemView(this, typesetLayout ?: styleLayout ?: parent)
        if ((typesetLayout ?: styleLayout) != null) (typesetLayout ?: styleLayout)!!.addView(
            view
        )
        return FormViewHolder(styleLayout ?: typesetLayout ?: view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        getItem(position).also {
            onBindParentViewHolder(holder, it)
            it.onBindItemView(this, holder)
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position).also {
            onBindParentViewHolder(holder, it)
            it.onBindItemView(this, holder, payloads)
        }
    }

    private fun onBindParentViewHolder(holder: FormViewHolder, item: FormItem) {
        style.onBindItemParentLayout(holder, item)
        if (item.isNeedToTypeset) {
            (item.typeset ?: style.defaultTypeset)?.onBindItemTypesetParent(holder, item)
        }
    }

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val typeset = item.typeset ?: style.defaultTypeset
        val itemType = ItemViewType(
            if (typeset != null) typeset::class.java else null,
            item::class.java
        )
        if (!itemTypeLookup.contains(itemType)) {
            itemTypeLookup.add(itemType)
        }
        val viewType = itemTypeLookup.indexOf(itemType)
        if (!itemFactoryCache.contains(viewType)) {
            itemFactoryCache.register(viewType, item)
        }
        return viewType
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this._recyclerView = null
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
}