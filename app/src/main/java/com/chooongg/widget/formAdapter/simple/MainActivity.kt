package com.chooongg.widget.formAdapter.simple

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.chooongg.widget.formAdapter.FormAdapter
import com.chooongg.widget.formAdapter.addDivider
import com.chooongg.widget.formAdapter.addText
import com.chooongg.widget.formAdapter.item.FormText
import com.chooongg.widget.formAdapter.style.Material3CardElevatedStyle
import com.chooongg.widget.formAdapter.style.Material3CardOutlinedStyle

class MainActivity : AppCompatActivity() {

    class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)

        init {
            adapter.setNewInstance {
                addPart(Material3CardElevatedStyle()) {
                    partName = "Material3CardElevatedStyle"
                    createGroup {
                        addText("FormText") {
                            content = "文本"
                        }
                        addDivider()
                        addText("FormText")
                    }
                }
                addPart(Material3CardOutlinedStyle()) {
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
        model.adapter.bind(findViewById(R.id.recyclerView))
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
}