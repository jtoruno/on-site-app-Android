package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.zimplifica.siteappandroid.Models.Bill
import com.zimplifica.siteappandroid.R
import java.util.*

class ActivityAdapter(context: Context) : BaseAdapter() {
    private val TYPE_ITEM = 0
    private val TYPE_HEADER = 1
    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mData = mutableListOf<Bill>()
    private var sectionHeader = TreeSet<Int>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view  = convertView
        var holder: ViewHolder?
        val rowType = getItemViewType(position)
        if(view == null){
            holder = ViewHolder()
            when(rowType){
                TYPE_ITEM -> {
                    view = mInflater.inflate(R.layout.activity_row, null)
                    holder.companyText = view.findViewById(R.id.textView8)
                    holder.companyImg = view.findViewById(R.id.imageView7)
                    holder.descriptionText = view.findViewById(R.id.textView15)
                    holder.categoryText = view.findViewById(R.id.textView16)
                    holder.hourText = view.findViewById(R.id.textView17)
                    holder.amountText = view.findViewById(R.id.textView18)
                }
                TYPE_HEADER -> {
                    view = mInflater.inflate(R.layout.header_item_activity, null)
                    view.isEnabled = false
                    holder.hourText = view.findViewById(R.id.date_activity_header)
                }
            }
            view?.tag = holder
        }
        else{holder = view.tag as ViewHolder}

        if(rowType==TYPE_ITEM){
            val bill = mData[position]
            val generator = ColorGenerator.MATERIAL
            val initials = bill.enterprise
                .split(' ')
                .mapNotNull { it.firstOrNull()?.toString() }
                .reduce { acc, s -> acc + s }
            val drawable = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .endConfig()
                .buildRound(initials,generator.getColor(initials))
            holder.companyImg?.setImageDrawable(drawable)
            holder.companyText?.text = bill.enterprise
            holder.descriptionText?.text = bill.description
            holder.hourText?.text = bill.hour
            holder.amountText?.text = bill.amount
            if(bill.type){
                holder.categoryText?.text = "Solicitud de Pago"
            }
            else{
                holder.categoryText?.text = "Pago"
            }
        }
        else if(rowType == TYPE_HEADER){
            holder.hourText?.text = mData[position].date
        }
        return view!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (sectionHeader.contains(position)) TYPE_HEADER else TYPE_ITEM
    }

    override fun getItem(position: Int): Bill {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    fun addItem(item : Bill){
        mData.add(item)
        notifyDataSetChanged()
    }
    fun addHeader(item : Bill){
        mData.add(item)
        sectionHeader.add(mData.size - 1)
        notifyDataSetChanged()
    }

    class ViewHolder {
        var companyText : TextView? = null
        var companyImg : ImageView? = null
        var descriptionText : TextView? = null
        var categoryText : TextView? = null
        var hourText : TextView? = null
        var amountText : TextView? = null
    }
}