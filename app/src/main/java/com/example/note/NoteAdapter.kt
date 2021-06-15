package com.example.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val list: MutableList<Note>,
    private val OnClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(item: View, private val OnClick: (Note) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val text = item.findViewById<TextView>(R.id.current_text)
        private val data = item.findViewById<TextView>(R.id.current_data)
        private val item = item.findViewById<View>(R.id.item)

        fun bind(currentInfo: Note) {
            text.text = currentInfo.text
            data.text = currentInfo.data
            item.setOnClickListener {
                OnClick(currentInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view, OnClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}