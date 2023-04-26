package com.chooongg.widget.formAdapter.typeset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.FormManager
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.enum.FormEmsMode
import com.chooongg.widget.formAdapter.item.BaseForm
import com.google.android.material.textview.MaterialTextView

class HorizontalTypeset(
    ems: Int = FormManager.defaultEmsSize,
    emsMode: FormEmsMode = FormEmsMode.FIXED
) : Typeset(ems, emsMode) {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_horizontal, parent, false) as LinearLayoutCompat
    }

    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<MaterialTextView>(R.id.formInternalNameTextView)) {
            text = item.name
            when (emsMode) {
                FormEmsMode.NONE -> {
                    minWidth = 0
                    maxWidth = Int.MAX_VALUE
                }

                FormEmsMode.MIN -> {
                    minEms = ems
                    maxWidth = Int.MAX_VALUE
                }

                FormEmsMode.MAX -> {
                    minWidth = 0
                    maxEms = ems
                }

                FormEmsMode.FIXED -> setEms(ems)
            }
        }
    }

    override fun addContentView(parent: ViewGroup, view: View) {
        parent.addView(view, LinearLayoutCompat.LayoutParams(0, -2).apply {
            weight = 1f
        })
    }
}