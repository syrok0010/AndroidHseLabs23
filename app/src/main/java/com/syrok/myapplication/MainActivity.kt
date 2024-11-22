package com.syrok.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syrok.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter: TodoItemAdapter = TodoItemAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(ime.left, systemBars.top, ime.right, ime.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        binding.todoInput.editText?.doOnTextChanged { newText: CharSequence?, u: Int, i1: Int, i2: Int ->
            binding.addButton.isEnabled = !newText.isNullOrEmpty()
        }

        binding.addButton.setOnClickListener {
            adapter.addTodo(binding.todoInput.editText?.text.toString())
            binding.todoInput.editText?.text = null
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1);
        }
    }
}