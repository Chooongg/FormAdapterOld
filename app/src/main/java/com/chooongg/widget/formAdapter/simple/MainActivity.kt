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
import com.chooongg.widget.formAdapter.item.FormText
import com.chooongg.widget.formAdapter.style.CardElevatedStyle

class MainActivity : AppCompatActivity() {

    class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)

        init {
            adapter.setNewInstance {
                addPart(CardElevatedStyle()) {
                    createGroup {
                        add(FormText("FormText", null).apply {
                            content = "This is FormText"
                            menuIconRes = com.google.android.material.R.drawable.ic_clock_black_24dp
                        })
                    }
                }
                addPart(CardElevatedStyle()) {
                    createGroup {
                        addMultiColumn {
                            for (i in 0..1) {
                                add(FormText("FormText", null).apply {
                                    content = "This is FormText"
                                })
                            }
                        }
                    }
                }
                addPart(CardElevatedStyle()) {
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
                addPart(CardElevatedStyle()) {
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
                addPart(CardElevatedStyle()) {
                    createGroup {
                        addMultiColumn {
                            for (i in 0..4) {
                                add(FormText("FormText", null).apply {
                                    content = "1 2 3 4 5 6 7 8 9 0"
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