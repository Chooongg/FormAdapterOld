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
                    partName = "测试2"
                    createGroup {
                        add(FormText("FormText", null).apply {
                            content = "This is FormText4"
                        })
                        addSingleLine {
                            add(FormText("FormText", null).apply {
                                spanWeight = 2
                                content = "This is FormText"
                            })
                            add(FormText("FormText", null).apply {
                                content = "This is FormText2"
                            })
                            add(FormText("FormText", null).apply {
                                content = "This is FormText3"
                            })
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

    private class Adapter : RecyclerView.Adapter<FormViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
            return FormViewHolder(LinearLayoutCompat(parent.context).apply {
                setBackgroundColor(Color.RED)
                addView(View(parent.context))
            })
        }

        override fun getItemCount() = 4

        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            with(holder.itemView) {

            }
            with((holder.itemView as ViewGroup).getChildAt(0)) {
                setBackgroundColor(Color.YELLOW)
                layoutParams = LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    300 + 100 * position
                ).apply {
                    if (position == 0) {
                        marginStart = 30
                        marginEnd = 0
                    } else if (position == 2) {
                        marginStart = 0
                        marginEnd = 30
                    } else {
                        marginStart = 0
                        marginEnd = 0
                    }
                }
            }
        }
    }
}