package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zimplifica.siteappandroid.Models.Card
import com.zimplifica.siteappandroid.R

class RecyclerCard(val context: Context, val items: List<Card> ) : RecyclerView.Adapter<RecyclerCard.CardViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.dialog_card_row,p0, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: CardViewHolder, p1: Int) {
        val card = items[p1]
        p0.cardNumber.text = "•••• " +card.last4
        if(card.issuer=="visa"){
            p0.cardType.text = "Visa"
            p0.cardImage.setImageDrawable(context.resources.getDrawable(R.drawable.visa_logo))
        }
        if(card.issuer=="mastercard"){
            p0.cardType.text = "MasterCard"
            p0.cardImage.setImageDrawable(context.resources.getDrawable(R.drawable.mastercard_logo))
        }
    }

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cardNumber : TextView = itemView.findViewById(R.id.card_number_dialog)
        var cardType : TextView = itemView.findViewById(R.id.cardTypetxtrecycler)
        var cardImage : ImageView = itemView.findViewById(R.id.imageView3)
    }
}