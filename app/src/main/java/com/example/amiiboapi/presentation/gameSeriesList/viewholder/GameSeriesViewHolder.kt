package com.example.amiiboapi.presentation.gameSeriesList.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.data.model.GameSeriesModel

class GameSeriesViewHolder(
    private val clickListener: (String) -> Unit, itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(gameSeriesModel: GameSeriesModel) {
        val gameSeriesTextView = itemView as TextView
        gameSeriesTextView.text = gameSeriesModel.name
        gameSeriesTextView.setOnClickListener { clickListener.invoke(gameSeriesModel.key) }
    }
}