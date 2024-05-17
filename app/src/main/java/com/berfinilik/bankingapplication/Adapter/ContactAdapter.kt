package com.berfinilik.bankingapplication.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.bankingapplication.Domain.Contact
import com.berfinilik.bankingapplication.R
import com.berfinilik.bankingapplication.databinding.ViewholderContactBinding
import com.bumptech.glide.Glide

class ContactAdapter(private var contactList: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding:ViewholderContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val contact = contactList[position]
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

            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("selected_contact_name", contact.name)
                    putString("selected_contact_iban", contact.iban)
                    putString("selected_contact_surname", contact.surname)

                }
                itemView.findNavController().navigate(R.id.actionHomePageFragmentToSendMoneyFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ViewholderContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = contactList.size


    fun updateData(newList: List<Contact>) {
        contactList = newList
        notifyDataSetChanged()
    }

}