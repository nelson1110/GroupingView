package com.libs.nelson.groupingview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class SliderView : LinearLayout {

    private var mAdapter: SliderAdapter? = null
    private var mItemListener: ItemListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        ev?.let {
            for (i in 0..childCount) {
                val child = getChildAt(i)
                val rec = Rect()
                child?.getGlobalVisibleRect(rec)
                if(rec.contains(it.rawX.toInt(),it.rawY.toInt())){
                    if(it.action == MotionEvent.ACTION_UP){
                        mItemListener?.onUp(child,mAdapter?.getItemDataList()?.get(i))
                    }else{
                        mItemListener?.onTouch(child,mAdapter?.getItemDataList()?.get(i))
                    }
                }
            }
        }


        return super.dispatchTouchEvent(ev)
    }

    private fun makeClickEvent(item: View, data: Any){

        item.setOnClickListener {
            mItemListener?.onClick(item,data)
        }

        item.setOnLongClickListener {
            mItemListener?.onLongClick(item,data)
            false
        }
    }

    fun setAdapter(adapter: SliderAdapter){
        mAdapter = adapter
        removeAllViews()
        mAdapter?.getItemDataList()?.forEach {
            try {
                val item = LayoutInflater.from(context).inflate(mAdapter?.getItemLayout()!!,null)
                makeClickEvent(item,it)
                mAdapter?.onBindItem(item,it)
                addView(item)
            }catch (e : Exception){
                throw Exception("layout not available exception")
            }
        }
    }

    fun setItemListener(listener: ItemListener){
        mItemListener = listener
    }


    abstract class SliderAdapter {
        fun getItemLayout(): Int{
            return R.layout.item_slider_default
        }

        abstract fun getItemDataList(): List<Any>

        abstract fun onBindItem(item: View , data: Any)

    }

    interface ItemListener {

        fun onTouch(item: View, data: Any?){}

        fun onUp(item: View, data: Any?){}

        fun onClick(item: View, data: Any?){}

        fun onLongClick(item: View, data: Any?){}

    }

}