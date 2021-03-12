package com.animir.bottomsheet2.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.animir.bottomsheet2.R
import com.animir.bottomsheet2.vo.BottomSheetItem

class BottomSheetAdapter(context : Context, dataList : ArrayList<BottomSheetItem>) : RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>(){
    private val mDataList : ArrayList<BottomSheetItem> = dataList
    private val mContext : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_scrollview_item, parent,false)
        return BottomSheetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class BottomSheetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val caption = itemView.findViewById<TextView>(R.id.caption)

        fun bind(item : BottomSheetItem){
            title.text = item.title
            caption.text = item.caption
        }
    }
}