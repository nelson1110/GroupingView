package com.libs.nelson.groupingview

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager


abstract class GroupDecoration : RecyclerView.ItemDecoration() {


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val count = parent.adapter?.itemCount!!
        if (count == 0) {
            return
        }

        for (i in 0..count) {
            val view = parent.layoutManager!!.getChildAt(i) ?: continue
            val position = parent.getChildAdapterPosition(view)
            if (checkItemIsFirstOfGroup(position)) {
                val groupView = inflateGroupView(parent, getGroupViewLayout(position))
                c.translate(0f,view.top.toFloat()-groupView.measuredHeight)
                onBindGroupView(groupView,position)
                groupView.draw(c)
                c.translate(0f,-view.top+groupView.measuredHeight.toFloat())
            }

        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if(!shouldAnimation()){
            return
        }
        val layoutManager = parent.layoutManager
        if(layoutManager is LinearLayoutManager){
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val firstVisibleView = layoutManager.findViewByPosition(firstVisiblePosition)

            val groupView = inflateGroupView(parent,getGroupViewLayout(firstVisiblePosition))
            onBindGroupView(groupView,firstVisiblePosition)
            if(firstVisiblePosition < parent.adapter!!.itemCount-1 && checkItemIsFirstOfGroup(firstVisiblePosition+1)
            && firstVisibleView!!.bottom <=groupView.measuredHeight){
                c.translate(0f,firstVisibleView.bottom-groupView.measuredHeight.toFloat())
                groupView.draw(c)
                c.translate(0f,-firstVisibleView.bottom+groupView.measuredHeight.toFloat())
            }else{
                groupView.draw(c)
            }
        }


    }

    private fun inflateGroupView(parent: RecyclerView, groupViewLayout: Int): View {
        val view = LayoutInflater.from(parent.context).inflate(groupViewLayout, parent, false)
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, parent.measuredWidth, view.measuredHeight)
        return view
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        position.let {
            if (checkItemIsFirstOfGroup(position)) {
                outRect.top = inflateGroupView(parent, getGroupViewLayout(position)).measuredHeight
            }

        }

    }

    abstract fun checkItemIsFirstOfGroup(position: Int): Boolean

    abstract fun getGroupViewLayout(position: Int): Int

    abstract fun onBindGroupView(groupView: View,position: Int)

    open fun shouldAnimation(): Boolean{
        return true
    }


}