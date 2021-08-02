package com.example.kotlinwebsocet.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinwebsocet.R

class ReceivedImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.findViewById(R.id.imageView)
    var nameTxt: TextView = itemView.findViewById(R.id.nameTxt)
}