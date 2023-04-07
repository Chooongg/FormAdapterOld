package com.chooongg.widget.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

class FormPartAdapter internal constructor(
    val globalAdapter: FormAdapter,
    val style: Style
) : RecyclerView.Adapter<FormViewHolder>() {

    companion object {
        const val NOTIFY_PAYLOADS = "update"
    }

    internal var _recyclerView: WeakReference<RecyclerView>? = null

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

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<FormItem>() {
        override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem) = oldItem == newItem
        override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem) =
            oldItem.name == newItem.name && oldItem.field == newItem.field
    }).build())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val realParent = style.onCreateItemParent(parent).let { styleParent ->
            globalAdapter.findItemViewTypeForInt(viewType).let {
                (it?.typeset ?: style.defaultTypeset)?.onCreateItemTypesetParent(styleParent)
            }
        }
        return FormViewHolder(realParent)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        holder.itemView

        getItem(position).also {
            (it.typeset ?: style.defaultTypeset)?.onBindItemTypesetParent(
                holder.itemView,
                it
            )
            it.onBindItemView(this, holder)
        }
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
        return globalAdapter.getItemViewType(
            style,
            item.typeset ?: style.defaultTypeset,
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