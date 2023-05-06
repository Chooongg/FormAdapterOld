package com.chooongg.widget.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.MultiColumnForm
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.NoneTypeset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

class FormPartAdapter internal constructor(
    val globalAdapter: BaseFormAdapter,
    val style: Style
) : RecyclerView.Adapter<FormViewHolder>() {

    private var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    internal lateinit var creator: PartCreator

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)
        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) = false
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            if (oldItem is FormGroupTitle && newItem is FormGroupTitle) {
                oldItem.groupIndex == newItem.groupIndex && oldItem.name == newItem.name
            } else oldItem.antiRepeatCode == newItem.antiRepeatCode
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
        if (!this::creator.isInitialized) {
            asyncDiffer.submitList(null)
            return
        }
        val partIndex = globalAdapter.indexPartOf(this)
        val tempList = mutableListOf<MutableList<BaseForm>>()
        if (creator.dynamicPart) {
            if (creator.groups.size < creator.dynamicPartMinGroupCount) {
                if (creator.dynamicPartCreateGroupBlock != null) {
                    creator.createGroup { creator.dynamicPartCreateGroupBlock!!.invoke(this) }
                }
            }
        }
        creator.groups.forEachIndexed { groupIndex, group ->
            val groupList = ArrayList<BaseForm>()
            val partName = if (creator.dynamicPart) {
                creator.dynamicPartNameFormatBlock?.invoke(creator.partName, groupIndex)
                    ?: "${creator.partName ?: ""}${groupIndex + 1}"
            } else creator.partName
            if (creator.partName != null || creator.dynamicPart) {
                groupList.add(FormGroupTitle(partName, creator.partField).apply {

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
        asyncDiffer.submitList(ArrayList<BaseForm>().apply { tempList.forEach { addAll(it) } })
    }

    private fun disassemblyMultiColumn(finalList: ArrayList<BaseForm>, item: MultiColumnForm) {
        val lines = ArrayList<ArrayList<BaseForm>>().apply {
            add(ArrayList())
        }
        item@ for (it in item.items) {
            if (!it.isRealVisible(globalAdapter.isEditable)) continue@item
            if (lines.last().size >= item.maxColumn) {
                lines.add(ArrayList())
            }
            lines.last().add(it)
        }
        for (multiColumn in lines) {
            if (multiColumn.isEmpty()) continue
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateItemParent(parent)
        val typeInfo = globalAdapter.findItemViewTypeInfo(viewType)
        val typesetLayout = typeInfo.typeset.onCreateItemTypesetParent(styleLayout ?: parent)
        if (styleLayout != null && typesetLayout != null) styleLayout.addView(typesetLayout)
        val view = typeInfo.item.onCreateContentView(this, typesetLayout ?: styleLayout ?: parent)
        if (typesetLayout != null) {
            typeInfo.typeset.also {
                it.addContentView(typesetLayout, view)
                it.onCreateMenuButton(typesetLayout)
            }
        } else styleLayout?.addView(view)
        return FormViewHolder(styleLayout ?: typesetLayout ?: view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        getItem(position).also {
            it.partPosition = holder.bindingAdapterPosition
            it.adapterPosition = holder.absoluteAdapterPosition
            onBindParentViewHolder(holder, it)
            it.onBeforeBindContentView(this, holder)
            it.onBindContentView(this, holder)
            it.onAfterBindContentView(this, holder)
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
            it.onBeforeBindContentView(this, holder)
            it.onBindContentView(this, holder, payloads)
            it.onAfterBindContentView(this, holder)
        }
    }

    private fun onBindParentViewHolder(holder: FormViewHolder, item: BaseForm) {
        item.setItemClick(this, holder)
        style.onBindItemParentLayout(holder, item)
        globalAdapter.findItemViewTypeInfo(holder.itemViewType).typeset.also {
            it.onBindItemTypesetParentPadding(this, holder, item)
            it.onBindItemTypesetParent(this, holder, item)
            it.onBindMenuButton(this, holder, item)
        }
    }

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return globalAdapter.getItemViewType(
            style,
            if (item.isNeedToTypeset) item.typeset ?: style.defaultTypeset else NoneTypeset,
            item
        )
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