package com.example.amiiboapi.presentation.gameSeriesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.presentation.gameSeriesList.viewholder.GameSeriesViewHolder

/**
 * Адаптер для отображения серий игр
 *
 * @property gameSeries список серий игр [GameSeriesModel]
 * @property clickListener слушатель нажатий
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesAdapter(
    private val gameSeries: List<GameSeriesModel>,
    private val clickListener: (String) -> Unit) : RecyclerView.Adapter<GameSeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameSeriesViewHolder {
        return GameSeriesViewHolder(
            clickListener,
            LayoutInflater.from(parent.context).inflate(R.layout.item_game_series, parent, false))
    }

    override fun onBindViewHolder(holder: GameSeriesViewHolder, position: Int) {
        holder.bind(gameSeries[position])
    }

    override fun getItemCount(): Int = gameSeries.size

}