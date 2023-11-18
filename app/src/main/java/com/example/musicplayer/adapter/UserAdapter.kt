package com.example.musicplayer.adapter

import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.musicplayer.databinding.ItemRvBinding
import com.example.musicplayer.models.User
import com.example.musicplayer.utils.itemTouchHelperA
import java.util.Collections

class UserAdapter (var list: ArrayList<User>, var rvAction:RvAction):RecyclerView.Adapter<UserAdapter.Vh>(), itemTouchHelperA {

    inner class Vh(val itemRvBinding: ItemRvBinding):ViewHolder(itemRvBinding.root) {

        fun onBind(user: User, position: Int) {
            itemRvBinding.name.text = user.name
            itemRvBinding.root.setOnClickListener {
                rvAction.itemClick(position, user)
            }
            itemRvBinding.root.setOnLongClickListener{
                rvAction.itemLongClick(position, user)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun onItemMove(fromItem: Int, toItem: Int) {
        if (fromItem<toItem){
            for (i in fromItem until toItem){
                Collections.swap(list , i, i+1)
            }
        }else{
            for (i in fromItem downTo toItem){
                Collections.swap(list, i, i)
            }
        }
        notifyItemMoved(fromItem, toItem)
    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    interface RvAction{
        fun itemClick(position:Int, user: User)
        fun itemLongClick(position: Int, user: User)
    }
}