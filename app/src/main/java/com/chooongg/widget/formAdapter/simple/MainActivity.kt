package com.chooongg.widget.formAdapter.simple

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.FormAdapter

class MainActivity : AppCompatActivity() {

    private class MainViewModel : ViewModel() {
        val adapter = FormAdapter(true)
    }

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.adapter.bind(findViewById(R.id.recyclerView))
    }
}