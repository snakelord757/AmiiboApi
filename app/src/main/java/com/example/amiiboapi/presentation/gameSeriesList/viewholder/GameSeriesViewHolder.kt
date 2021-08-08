package com.example.amiiboapi.presentation.gameSeriesList.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.data.model.GameSeriesModel

/**
 * ViewHolder для отоюражения конкретной серии игр
 *
 * @property clickListener слушатель нажатий
 *
 * @param itemView view отображаемого элемента
 *
 * @author Murad Luguev on 08-08-2021
 */
class GameSeriesViewHolder(
    private val clickListener: (String) -> Unit, itemView: View
) : RecyclerView.ViewHolder(itemView) {

    /**
     * Метод для связывания данныз из [GameSeriesModel] с [itemView]
     *
     * @param gameSeriesModel модель игровой серии [GameSeriesModel]
     */
    fun bind(gameSeriesModel: GameSeriesModel) {
        val gameSeriesTextView = itemView as TextView
        gameSeriesTextView.text = gameSeriesModel.name
        gameSeriesTextView.setOnClickListener { clickListener.invoke(gameSeriesModel.key) }
    }
}