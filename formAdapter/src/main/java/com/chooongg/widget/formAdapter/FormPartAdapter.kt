package com.chooongg.widget.formAdapter

import android.util.Log
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
import com.chooongg.widget.formAdapter.item.MultiColumnForm
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

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    private val itemFactoryCache = ItemFactoryCache()

    internal val itemTypeLookup = ArraySet<ItemViewType>()

    var listener: FormEventListener? = null
        internal set

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
                if (item is MultiColumnForm) {
                    disassemblyMultiColumn(groupList, item)
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
        tempList.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupIndex = index
                item.positionForGroup = position
            }
        }
        asyncDiffer.submitList(ArrayList<FormItem>().apply { tempList.forEach { addAll(it) } })
    }

    private fun disassemblyMultiColumn(finalList: ArrayList<FormItem>, item: MultiColumnForm) {
        val multiColumn = ArrayList<FormItem>()
        item@ for (it in item.items) {
            if (!it.isRealVisible(globalAdapter.isEditable)) continue@item
            multiColumn.add(it)
        }
        // TODO 单行计算改为多列计算
        if (multiColumn.isEmpty()) return
        var maxWidget = 0
        multiColumn.forEachIndexed { index, formItem ->
            maxWidget += formItem.spanWeight
            formItem.singleLineCount = multiColumn.size
            formItem.singleLineIndex = index
            finalList.add(formItem)
        }
        if (multiColumn.size == 1) {
            multiColumn[0].spanSize = 120
        } else {
            multiColumn.forEach {
                it.spanSize = (120 / maxWidget) * it.spanWeight
            }
            if (120 % maxWidget != 0) {
                for (i in 0 until 120 % maxWidget) {
                    multiColumn[i % multiColumn.size].spanSize += 1
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        Log.e("FormAdapter", "onCreateViewHolder: viewType = $viewType")
        val styleLayout = style.onCreateItemParent(parent)
        val item = itemFactoryCache.get(viewType)
        val typesetLayout = if (item.isNeedToTypeset) {
            (item.typeset ?: style.defaultTypeset)
                ?.onCreateItemTypesetParent(styleLayout ?: parent)
        } else null
        if (styleLayout != null && typesetLayout != null) styleLayout.addView(typesetLayout)
        val view = item.onCreateContentView(this, typesetLayout ?: styleLayout ?: parent)
        if (typesetLayout != null) {
            (item.typeset ?: style.defaultTypeset)?.also {
                it.onCreateMenuButton(typesetLayout)
                it.addContentView(typesetLayout, view)
            }
        } else styleLayout?.addView(view)
        return FormViewHolder(styleLayout ?: typesetLayout ?: view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        getItem(position).also {
            it.partPosition = holder.bindingAdapterPosition
            it.adapterPosition = holder.absoluteAdapterPosition
            onBindParentViewHolder(holder, it)
            it.onBindContentView(this, holder)
        }
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position).also {
            it.partPosition = holder.bindingAdapterPosition
            it.adapterPosition = holder.absoluteAdapterPosition
            onBindParentViewHolder(holder, it)
            it.onBindContentView(this, holder, payloads)
        }
    }

    private fun onBindParentViewHolder(holder: FormViewHolder, item: FormItem) {
        holder.itemView.setOnClickListener { listener?.onFormClick(this, item, holder.itemView) }
        style.onBindItemParentLayout(holder, item)
        if (item.isNeedToTypeset) {
            (item.typeset ?: style.defaultTypeset)?.also {
                it.onBindItemTypesetParent(this, holder, item)
                it.onBindMenuButton(this, holder, item)
            }
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