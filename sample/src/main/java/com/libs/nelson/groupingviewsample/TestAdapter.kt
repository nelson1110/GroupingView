package com.libs.nelson.groupingview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

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


    class TestViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}