package com.chooongg.widget.formAdapter.simple

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.chooongg.widget.formAdapter.FormAdapter
import com.chooongg.widget.formAdapter.FormEventListener
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.addButton
import com.chooongg.widget.formAdapter.addDivider
import com.chooongg.widget.formAdapter.addInput
import com.chooongg.widget.formAdapter.addSelector
import com.chooongg.widget.formAdapter.addText
import com.chooongg.widget.formAdapter.enum.FormEnableMode
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.item.FormButton
import com.chooongg.widget.formAdapter.item.FormText
import com.chooongg.widget.formAdapter.style.Material3CardElevatedStyle
import com.chooongg.widget.formAdapter.style.Material3CardOutlinedStyle
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), FormEventListener {

    class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)

        init {
            adapter.setNewInstance {
                addPart {
                    partName = "控制器"
                    createGroup {
                        addButton("isEditable：true", "isEditable") {
                            enableMode = FormEnableMode.ALWAYS
                        }
                    }
                }
                addPart(Material3CardElevatedStyle()) {
//                    partName = "Material3CardElevatedStyle"
                    createGroup {
                        addText("FormText") {
                            content = "文本"
                        }
                        addDivider()
                        addText("FormText") {
                            menuIconRes = com.google.android.material.R.drawable.ic_clock_black_24dp
                        }
                        addInput("FormInput") {
                            prefixText = "前缀"
                            suffixText = "后缀"
                            maxLines = 2
                        }
                        addButton("FormButton") {
                            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_END
                            iconRes = com.google.android.material.R.drawable.ic_clock_black_24dp
                        }
                        addButton("FormButton") {
                            buttonStyle = FormButton.ButtonStyle.TONAL
                            iconRes = com.google.android.material.R.drawable.ic_clock_black_24dp
                        }
                        addSelector("FormSelector"){

                        }
                    }
                }
                addPart(Material3CardOutlinedStyle()) {
                    partName = "Material3CardOutlinedStyle"
                    createGroup {
                        addMultiColumn {
                            for (i in 0..2) {
                                add(FormText("FormText", null).apply {
                                    content = "This is FormText"
                                })
                            }
                        }
                    }
                }
                addPart(Material3CardElevatedStyle()) {
                    createGroup {
                        addMultiColumn {
                            for (i in 0..3) {
                                add(FormText("FormText", null).apply {
                                    content = "This issssss FormText"
                                })
                            }
                        }
                    }
                }
                addPart(Material3CardElevatedStyle()) {
                    createGroup {
                        addMultiColumn {
                            maxColumn = 2
                            for (i in 0..4) {
                                add(FormText("FormText", null).apply {
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(inset.left, 0, inset.right, inset.bottom)
            insets
        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        model.adapter.bind(findViewById(R.id.recyclerView), this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update) {
            model.adapter.update()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFormClick(adapter: FormPartAdapter, item: BaseForm, itemView: View) {
        when (item.field) {
            "isEditable" -> {
                if (model.adapter.isEditable) {
                    item.name = "isEditable：false"
                } else {
                    item.name = "isEditable：true"
                }
                model.adapter.isEditable = !model.adapter.isEditable
                model.adapter.scrollToItem(item)
            }
        }
    }
}