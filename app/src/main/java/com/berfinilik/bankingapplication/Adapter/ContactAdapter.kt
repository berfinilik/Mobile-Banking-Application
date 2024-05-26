package com.berfinilik.bankingapplication.Adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.bankingapplication.Domain.Contact
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.ViewholderContactBinding
import com.berfinilik.bankingapplication.ui.sendmoney.SendMoneyFragment
import com.bumptech.glide.Glide

class ContactAdapter(private var contactList: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ViewholderContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = contactList.size

    inner class ContactViewHolder(private val binding: ViewholderContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val contact = contactList[position]
                    val bundle = Bundle().apply {
                        putString("selected_contact_name", contact.name)
                        putString("selected_contact_iban", contact.iban)
                        putString("selected_contact_surname", contact.surname)
                        putString("selected_contact_picUrl", contact.picUrl)
                    }
                    Log.d("ContactAdapter", "Clicked on item: $position, Contact: $contact")
                    Log.d("ContactAdapter", "Bundle: $bundle")
                    itemView.findNavController()
                        .navigate(R.id.actionHomePageFragmentToSendMoneyFragment, bundle)
                }

            }
        }

        fun bind(contact: Contact) {
            binding.apply {
                Glide.with(itemView)
                    .load(contact.picUrl)
                    .into(pic)
                titleTxt.text = contact.name
            }
        }
    }

    fun updateData(newList: List<Contact>) {
        contactList = newList
        notifyDataSetChanged()
    }
}
