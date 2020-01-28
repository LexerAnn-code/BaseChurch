package com.ankit.basechurch.util

import android.app.Activity
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ankit.basechurch.R
import kotlinx.android.synthetic.main.mdetail.view.*

class RecyclerMemberDetailHolder(val v: View):RecyclerView.ViewHolder(v)
class RecyclerMemberDetailAdapter (val host: Activity):ListAdapter<Tithes,RecyclerMemberDetailHolder>(
    DIFF_UTIL){
    companion object {

        val DIFF_UTIL: DiffUtil.ItemCallback<Tithes> =
            object : DiffUtil.ItemCallback<Tithes>() {
                override fun areItemsTheSame(oldItem: Tithes, newItem: Tithes): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(oldItem: Tithes, newItem: Tithes): Boolean =
                    newItem == oldItem
            }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMemberDetailHolder {
          return  RecyclerMemberDetailHolder(
              LayoutInflater.from(parent.context).inflate(R.layout.mdetail,parent,false)
          )
        }

        override fun onBindViewHolder(holder: RecyclerMemberDetailHolder, position: Int) {
            val gt=getItem(position)
            holder.v.mFName.text=gt.TithePayee
            holder.v.mTAmount.text=gt.TitheAmount
            holder.v.mTDate.text=gt.TitheDate

        }
    }
