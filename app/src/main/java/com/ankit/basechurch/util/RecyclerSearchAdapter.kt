package com.ankit.basechurch.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankit.basechurch.R
import kotlinx.android.synthetic.main.searchlayout.view.*

class RecyclerSearchViewHolder(val v:View):RecyclerView.ViewHolder(v)
class RecyclerSearchAdapter (val host:Activity):ListAdapter<Tithes, RecyclerSearchViewHolder>(
    DIFF_UTIL
){
    companion object{

        val DIFF_UTIL:DiffUtil.ItemCallback<Tithes> =
            object :DiffUtil.ItemCallback<Tithes>(){
                override fun areItemsTheSame(oldItem: Tithes, newItem: Tithes): Boolean =
                    newItem.id==oldItem.id


                override fun areContentsTheSame(oldItem: Tithes, newItem: Tithes): Boolean =
                  newItem==oldItem
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerSearchViewHolder {
        return RecyclerSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.searchlayout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerSearchViewHolder, position: Int) {
        val sh=getItem(position)
        holder.v.SearchTithesDateItemT.text=sh.TitheDate
        holder.v.SearchTithesAmountItemT.text=sh.TitheAmount
        holder.v.SearchTPayeesName.text=sh.TithePayee
    }
}

