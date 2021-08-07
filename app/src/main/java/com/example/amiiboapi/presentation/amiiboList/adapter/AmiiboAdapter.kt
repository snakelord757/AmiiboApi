package com.example.amiiboapi.presentation.amiiboList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.presentation.amiiboList.viewholder.AmiiboViewHolder

class AmiiboAdapter(
    private val amiibosList: List<AmiiboModelMinimal>,
    private val clickListener: (String) -> Unit,) : RecyclerView.Adapter<AmiiboViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmiiboViewHolder {
        return AmiiboViewHolder(
            clickListener,
            LayoutInflater.from(parent.context).inflate(R.layout.item_amiibo, parent, false))
    }

    override fun onBindViewHolder(holder: AmiiboViewHolder, position: Int) {
        holder.bind(amiibosList[position])
    }

    override fun getItemCount(): Int = amiibosList.size
}