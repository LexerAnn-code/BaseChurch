package com.ankit.basechurch.util

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ankit.basechurch.R
import com.ankit.basechurch.activity.EditActivity
import com.ankit.basechurch.activity.TitheEditActivity
import kotlinx.android.synthetic.main.addtitheslayout.view.*

class RecyclerTithesViewHolder (val v: View): RecyclerView.ViewHolder(v)
class RecyclerTithesAdapter (val host:Activity):ListAdapter<User ,RecyclerTithesViewHolder>(DIFF_UTIL){
    companion object{
        val DIFF_UTIL:DiffUtil.ItemCallback<User> =
            object :DiffUtil.ItemCallback<User>(){
                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                    newItem.uid==oldItem.uid



                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                    newItem==oldItem
            }


    }
    override fun onBindViewHolder(holder: RecyclerTithesViewHolder, position: Int) {
        val gg=getItem(position)
        holder.v.TPayeesFirstName.text=gg.userFirstName
        holder.v.TPayeesLastName.text=gg.userLastName
        holder.v.setOnClickListener {
            host.startActivity(
                Intent(host, TitheEditActivity::class.java).apply {
                    putExtra(TitheEditActivity.EXTRA_POST, gg) })
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTithesViewHolder {
        return RecyclerTithesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.addtitheslayout,parent,false)
        )
    }


    }



