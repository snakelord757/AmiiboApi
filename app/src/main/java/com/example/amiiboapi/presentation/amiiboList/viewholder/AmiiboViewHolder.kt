package com.example.amiiboapi.presentation.amiiboList.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.squareup.picasso.Picasso

/**
 * ViewHolder ля отображения предмета
 *
 * @property clickListener слушатель нажатий
 *
 * @param itemView view отображаемого элемента
 */
class AmiiboViewHolder(
    private val clickListener: (String) -> Unit,
    itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val amiiboImage: ImageView = itemView.findViewById(R.id.amiiboImage)
    private val amiiboTailTextView: TextView = itemView.findViewById(R.id.amiiboName)

    /**
     * Метод, связывающий [AmiiboModelMinimal] с [itemView]
     *
     * @param amiiboModelMinimal модель предмета с минимальной информацией
     */
    fun bind(amiiboModelMinimal: AmiiboModelMinimal) {
        Picasso.get()
            .load(amiiboModelMinimal.image)
            .fit()
            .centerInside()
            .into(amiiboImage)
        amiiboTailTextView.text = amiiboModelMinimal.name
        itemView.setOnClickListener { clickListener.invoke(amiiboModelMinimal.tail) }
    }
}