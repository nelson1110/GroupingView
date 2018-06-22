package com.libs.nelson.groupingviewsample

import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.libs.nelson.groupingview.GroupDecoration
import com.libs.nelson.groupingview.SliderView
import com.libs.nelson.groupingview.TestAdapter
import kotlinx.android.synthetic.main.grouping_view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grouping_view)


        list.layoutManager = LinearLayoutManager(this)
        list.adapter = TestAdapter()

        list.addItemDecoration(object : GroupDecoration(){
            override fun onBindGroupView(groupView: View, position: Int) {
                groupView.findViewById<TextView>(R.id.text).text = position.toString()
            }

            override fun getGroupViewLayout(position: Int): Int {
                return R.layout.group_header
            }

            override fun checkItemIsFirstOfGroup(position: Int): Boolean {
                return position%2 == 0
            }


        })



        slider.setAdapter(object : SliderView.SliderAdapter(){


            override fun getItemDataList(): List<Any> {
               return listOf("a","b","c","d","e","f","g","h","i")
            }

            override fun onBindItem(item: View, data: Any) {
                item.findViewById<TextView>(R.id.item).text = data.toString()
            }

        })

        slider.setItemListener(object : SliderView.ItemListener{

            override fun onTouch(item: View, data: Any?) {
                Toast.makeText(this@MainActivity,data.toString(),Toast.LENGTH_SHORT).show()
            }

        })

    }


}
