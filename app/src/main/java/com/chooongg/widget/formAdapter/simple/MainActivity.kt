package com.chooongg.widget.formAdapter.simple

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.FormAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormText
import com.chooongg.widget.formAdapter.style.CardElevatedStyle
import kotlin.reflect.KTypeParameter

class MainActivity : AppCompatActivity() {

    class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)

        init {
            adapter.setNewInstance {
                addPart {
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
                addPart(CardElevatedStyle) {
                    createGroup {
                        addSingleLine {
                            for (i in 0..1) {
                                add(FormText("FormText", null).apply {
                                    content = "This is FormText"
                                })
                            }
                        }
                    }
                }
                addPart(CardElevatedStyle) {
                    createGroup {
                        addSingleLine {
                            for (i in 0..2) {
                                add(FormText("FormText", null).apply {
                                    content = "This is FormText"
                                })
                            }
                        }
                    }
                }
                addPart(CardElevatedStyle) {
                    createGroup {
                        addSingleLine {
                            for (i in 0..3) {
                                add(FormText("FormText", null).apply {
                                    content = "This issssss FormText"
                                })
                            }
                        }
                    }
                }
                addPart(CardElevatedStyle) {
                    createGroup {
                        addSingleLine {
                            for (i in 0..4) {
                                add(FormText("FormText", null).apply {
                                    content = "1 2 3 4 5 6 7 8 9 0"
                                })
                            }
                        }
                    }
                }
                addPart {
                    partName = "测试3"
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
                addPart {
                    partName = "测试4"
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
                addPart {
                    partName = "测试4"
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
                addPart {
                    partName = "测试4"
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
                addPart {
                    partName = "测试4"
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
    }

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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