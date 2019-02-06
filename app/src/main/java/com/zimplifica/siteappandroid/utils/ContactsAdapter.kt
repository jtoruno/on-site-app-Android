package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.zimplifica.siteappandroid.R

class ContactsAdapter(val context: Context, val items : MutableList<ContactModel>, val callBack : (ContactModel, Boolean) -> Unit):
    Filterable, RecyclerView.Adapter<ContactsAdapter.CardViewHolder>(){

    private var dataComplete = items
    private var data : MutableList<ContactModel> = items

    override fun getFilter(): Filter {
        return filter
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.contact_row, p0, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: CardViewHolder, p1: Int) {
        val contact = data[p1]
        if(contact.selected){
            p0.checkImage.visibility = View.VISIBLE
        }
        else{
            p0.checkImage.visibility = View.GONE
        }
        p0.name.text = contact.name
        p0.number.text = contact.number
        p0.layout.setOnClickListener {
            if(p0.checkImage.visibility == View.VISIBLE){
                //p0.checkImage.visibility = View.GONE
                data[p1].selected = false
                notifyItemChanged(p1)
                callBack(contact, true)
            }
            else{
                data[p1].selected = true
                //p0.checkImage.visibility = View.VISIBLE
                notifyItemChanged(p1)
                callBack(contact, false)
            }

        }
    }

    private val filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if(constraint == null || constraint.isEmpty()){
                data = dataComplete
            }
            else{
                val filterList = mutableListOf<ContactModel>()
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in dataComplete){
                    if (item.name.toLowerCase().contains(filterPattern)){
                        filterList.add(item)
                    }
                }
                data = filterList
            }

            val result2s = FilterResults()
            result2s.values = data
            return result2s
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            data = results?.values as MutableList<ContactModel>
            notifyDataSetChanged()
        }

    }


    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.textView2)
        var checkImage : ImageView = itemView.findViewById(R.id.imageView)
        var number : TextView = itemView.findViewById(R.id.textView3)
        val layout : ConstraintLayout = itemView.findViewById(R.id.contact_layout)
    }
}