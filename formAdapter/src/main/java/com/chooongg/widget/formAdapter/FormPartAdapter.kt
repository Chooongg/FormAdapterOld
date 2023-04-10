package com.chooongg.widget.formAdapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.*
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.item.FormItemFactoryCache
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

    private val itemFactoryCache = FormItemFactoryCache()

    internal lateinit var creator: PartCreator

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)
        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<FormItem>() {
        override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem) = oldItem == newItem
        override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem) =
            oldItem.name == newItem.name && oldItem.field == newItem.field
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
        val tempList = mutableListOf<MutableList<FormItem>>()
        creator.partList.forEachIndexed { groupIndex, group ->
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
                if (!item.isRealVisible(globalAdapter.isEditable)) continue@group
                groupList.add(item)
            }
            while (groupList.size > 0 && !groupList[0].isShowOnEdge) {
                groupList.removeAt(0)
            }
            while (groupList.size > 1 && !groupList[groupList.lastIndex].isShowOnEdge) {
                groupList.removeAt(groupList.lastIndex)
            }
            tempList.add(groupList)
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
        if ((typesetLayout ?: styleLayout) != null) (typesetLayout ?: styleLayout)!!.addView(view)
        return FormViewHolder(styleLayout ?: typesetLayout ?: view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.updateLayoutParams<LayoutParams> {
            width = if (item.isSingleRow) LayoutParams.MATCH_PARENT else LayoutParams.WRAP_CONTENT
        }
        style.onBindItemParentLayout(holder, item)
        if (item.isNeedToTypeset) {
            (item.typeset ?: style.defaultTypeset)?.onBindItemTypesetParent(holder, item)
        }
        item.onBindItemView(this, holder)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position).onBindItemView(this, holder, payloads)
    }

    private fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val viewType = globalAdapter.getItemViewType(
            style,
            item.typeset ?: style.defaultTypeset,
            item
        )
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