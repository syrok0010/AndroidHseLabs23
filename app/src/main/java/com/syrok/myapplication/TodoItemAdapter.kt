package com.syrok.myapplication

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syrok.myapplication.TodoItemAdapter.TodoItemHolder
import com.syrok.myapplication.databinding.TodoItemBinding

class TodoItemAdapter : RecyclerView.Adapter<TodoItemHolder>() {
    private val items = mutableListOf<TodoItem>()
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return TodoItemHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TodoItemHolder,
        position: Int
    ) {
        val item = items[position]

        with(holder.binding) {
            holder.binding.checkBox.text = item.text
            holder.binding.checkBox.isChecked = item.isChecked

            holder.binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                holder.binding.checkBox.paintFlags =
                    if (isChecked)
                        holder.binding.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else
                        holder.binding.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            holder.binding.trash.setOnClickListener { _ ->
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun addTodo(text: String) {
        items.add(TodoItem(false, text))
        notifyItemInserted(itemCount)
    }

    class TodoItemHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)
}