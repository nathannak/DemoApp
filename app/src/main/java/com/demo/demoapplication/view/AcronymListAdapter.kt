package com.demo.demoapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.demoapplication.R
import com.demo.demoapplication.databinding.ItemAcronymBinding
import com.demo.demoapplication.model.Lf

/*

Recycler view for search fragment, uses DataBinding

Written by W on 12/13/20
*/

class AcronymListAdapter(var acronymList: ArrayList<Lf>) : RecyclerView.Adapter<AcronymListAdapter.DogViewHolder>() {

    fun updateAcronymList(newacronymList: ArrayList<Lf>) {
        acronymList.clear()
        acronymList.addAll(newacronymList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAcronymBinding>(inflater, R.layout.item_acronym, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = acronymList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.longForm = acronymList[position]
    }

    class DogViewHolder(var view: ItemAcronymBinding) : RecyclerView.ViewHolder(view.root)
}