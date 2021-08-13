package com.example.amiiboapi.presentation.amiibo_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.presentation.amiibo_list.viewholder.AmiiboViewHolder

/**
 * Адаптер для отображения предметов по выбранной игровой серии
 *
 * @property amiiboList список предметов [AmiiboModelMinimal]
 * @property clickListener слушатель нажатий
 *
 * @author Murad Luguev on 08-08-2021
 */
class AmiiboAdapter(
    private val amiiboList: List<AmiiboModelMinimal>,
    private val clickListener: (String) -> Unit,) : RecyclerView.Adapter<AmiiboViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmiiboViewHolder {
        return AmiiboViewHolder(
            clickListener,
            LayoutInflater.from(parent.context).inflate(R.layout.item_amiibo, parent, false))
    }

    override fun onBindViewHolder(holder: AmiiboViewHolder, position: Int) {
        holder.bind(amiiboList[position])
    }

    override fun getItemCount(): Int = amiiboList.size
}