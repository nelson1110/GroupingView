package com.libs.nelson.groupingview

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TestAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    val data = listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","f","g","h","i","j","k","l","m","n","f","g","h","i","j","k","l","m","n","f","g","h","i","j","k","l","m","n","f","g","h","i","j","k","l","m","n")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val textView = TextView(parent.context)
        textView.textSize = 30f
        return TestViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        (holder.itemView as TextView).text = data[position]
    }


    class TestViewHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    }
}