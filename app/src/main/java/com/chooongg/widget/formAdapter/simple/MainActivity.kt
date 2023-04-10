package com.chooongg.widget.formAdapter.simple

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.chooongg.widget.formAdapter.FormAdapter
import com.chooongg.widget.formAdapter.item.FormText

class MainActivity : AppCompatActivity() {

    class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)

        init {
            adapter.addPart {
                partName = "测试"
                createGroup {
                    add(FormText("FormText", null).apply {
                        content = "This is FormText"
                    })
                    add(FormText("FormText", null).apply {
                        content = "This is FormText2"
                    })
                    add(FormText("FormText", null).apply {
                        content = "This is FormText3"
                    })
                    add(FormText("FormText", null).apply {
                        content = "This is FormText4"
                    })
                }
            }
        }
    }

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.adapter.bind(findViewById(R.id.recyclerView))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }
}