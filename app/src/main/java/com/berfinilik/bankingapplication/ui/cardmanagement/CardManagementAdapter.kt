package com.berfinilik.bankingapplication.ui.cardmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.bankingapplication.R

class CardManagementAdapter(private val itemList: List<String>, private val listener: CardManagementFragment) :
    RecyclerView.Adapter<CardManagementAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_management, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewItem.text = currentItem
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
