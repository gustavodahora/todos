package dev.gustavodahora.todos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.gustavodahora.todos.R
import dev.gustavodahora.todos.model.Todo

class TodoAdapter(private val listTodo: List<Todo>, val context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //get view reference
//            var ssid: TextView = view.findViewById(R.id.ssid) as TextView
        var checkBox: CheckBox = view.findViewById(R.id.checkBox)
        var tvTodo: TextView = view.findViewById(R.id.tv_todo)
        var imgClose: ImageView = view.findViewById(R.id.img_close)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view holder to hold reference
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_list_cell, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set values

        holder.imgClose.visibility = View.INVISIBLE
        setupCompleted(holder, position, listTodo[position].completed)

        holder.tvTodo.text = listTodo[position].todo
        holder.checkBox.setOnClickListener {
            setupCompleted(holder, position, holder.checkBox.isChecked)
        }
    }

    override fun getItemCount(): Int {
        return listTodo.size
    }

    private fun setupCompleted(holder: ViewHolder, position: Int, completed: Boolean) {
        if (completed) {
            holder.tvTodo.setTextColor(ContextCompat.getColor(context, R.color.grey_hint))
            holder.imgClose.visibility = View.VISIBLE
            listTodo[position].completed = true
            holder.checkBox.isChecked = true
        } else {
            holder.tvTodo.setTextColor(ContextCompat.getColor(context, R.color.gray_edit_text))
            holder.imgClose.visibility = View.INVISIBLE
            listTodo[position].completed = false
            holder.checkBox.isChecked = false
        }
    }
}