package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zimplifica.siteappandroid.Models.Card
import com.zimplifica.siteappandroid.R

class CardAdapter(var mCtx: Context, var resource:Int, var items: List<Card>) : ArrayAdapter<Card>(mCtx,resource,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder: ViewHolder?
        var rootView = convertView

        if(rootView == null){
            val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
            rootView = layoutInflater.inflate(resource , null )
            holder = ViewHolder()
            holder.ivIcon  = rootView.findViewById(R.id.imageView2)
            holder.tvName = rootView.findViewById(R.id.textView5)
            rootView.tag = holder
        }
        else{
            holder = rootView.tag as ViewHolder
        }
        var mItems : Card = items[position]

        if(mItems.issuer=="visa"){
            holder.ivIcon!!.setImageDrawable(mCtx.resources.getDrawable(R.drawable.visa_logo))
        }
        if(mItems.issuer=="mastercard"){
            holder.ivIcon!!.setImageDrawable(mCtx.resources.getDrawable(R.drawable.mastercard_logo))
        }
        holder.tvName!!.text = "•••• " + mItems.last4

        return rootView!!
    }

    internal class ViewHolder {
        var ivIcon: ImageView? = null
        var tvName: TextView? = null
    }
}