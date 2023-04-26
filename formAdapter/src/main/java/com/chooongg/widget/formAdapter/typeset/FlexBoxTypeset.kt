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
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textview.MaterialTextView

class FlexBoxTypeset(
    ems: Int = FormManager.defaultEmsSize,
    emsMode: FormEmsMode = FormEmsMode.MIN
) : Typeset(ems, emsMode) {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_flexbox, parent, false) as LinearLayoutCompat
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
        parent.findViewById<FlexboxLayout>(R.id.formInternalTypesetFlexBoxLayout).apply {
            addView(view, FlexboxLayout.LayoutParams(-2, -2).apply { flexGrow = Float.MAX_VALUE })
        }
    }
}