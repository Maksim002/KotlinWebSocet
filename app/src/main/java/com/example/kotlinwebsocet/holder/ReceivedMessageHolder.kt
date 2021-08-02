package com.example.kotlinwebsocet.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinwebsocet.R

class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var messageTxt: TextView = itemView.findViewById(R.id.receivedTxt)
}