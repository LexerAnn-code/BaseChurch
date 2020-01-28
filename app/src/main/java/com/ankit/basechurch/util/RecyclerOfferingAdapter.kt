package com.ankit.basechurch.util

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ankit.basechurch.activity.EditActivity
import com.ankit.basechurch.R
import com.ankit.basechurch.activity.TitheEditActivity
import kotlinx.android.synthetic.main.addlayout.view.*

class RecyclerOfferingViewHolder (val v: View): RecyclerView.ViewHolder(v)
class RecyclerOfferingAdapter(private val host:Activity):ListAdapter<Offering, RecyclerOfferingViewHolder>(
    DIFF_UTIL
){
    companion object {

        val DIFF_UTIL: DiffUtil.ItemCallback<Offering> =

            object : DiffUtil.ItemCallback<Offering>() {
                override fun areItemsTheSame(oldItem: Offering, newItem: Offering): Boolean =
                    newItem.OfferingAmount == oldItem.OfferingAmount


                override fun areContentsTheSame(oldItem: Offering, newItem: Offering): Boolean =
                    newItem.Date == oldItem.Date
            }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerOfferingViewHolder {
            return RecyclerOfferingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.addlayout,
                    parent,
                    false
                )
            )
        }

    override fun getItem(position: Int): Offering {
        return super.getItem(position)

    }

        override fun onBindViewHolder(holder: RecyclerOfferingViewHolder, position: Int) {
          val tt=getItem(position)
            holder.v.TithesAmount.text=tt.OfferingAmount
            holder.v.TithesDate.text=tt.Date

        }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {

        super.registerAdapterDataObserver(observer)

    }
    }




