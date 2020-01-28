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
import com.ankit.basechurch.activity.DetailMember
import com.ankit.basechurch.activity.TitheEditActivity
import kotlinx.android.synthetic.main.membertitheslayout.view.*


class RecyclerMemberHolder(val v: View):RecyclerView.ViewHolder(v)
class RecyclerMemberAdapter(val host:Activity):ListAdapter<User,RecyclerMemberHolder>(DIFF_UTIL){
    companion object {

        val DIFF_UTIL: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                    newItem.uid == oldItem.uid


                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                    newItem == oldItem
            }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMemberHolder {
            return RecyclerMemberHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.membertitheslayout,parent,false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerMemberHolder, position: Int) {
           val Mm=getItem(position)
          holder.v.AddFirstName.text=Mm.userFirstName
            holder.v.AddSecondName.text=Mm.userLastName
           holder.v.MemberLogo.load(Mm.icon)
            holder.v.setOnClickListener {
                host.startActivity(
                    Intent(host, DetailMember::class.java).apply {
                        putExtra(DetailMember.EXTRA_POST, Mm) })
            }
        }


        }

