package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zimplifica.siteappandroid.R

class SplitFareAdapter(val ctx : Context, val items : MutableList<ContactModel>):RecyclerView.Adapter<SplitFareAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewHolder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.split_item_row, p0, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: CardViewHolder, p1: Int) {
        val contact = items[p1]
        p0.name.text = contact.name
    }

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.textView6)
    }

    fun addContact( contact : ContactModel){
        items.add(contact)
        this.notifyDataSetChanged()
    }
    fun deleteContact(contact: ContactModel){
        items.remove(contact)
        this.notifyDataSetChanged()
    }
}