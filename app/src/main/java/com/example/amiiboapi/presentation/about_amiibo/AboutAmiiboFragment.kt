package com.example.amiiboapi.presentation.about_amiibo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.presentation.common.BaseFragment
import com.squareup.picasso.Picasso

/**
 * Фрагмент, отображающий информацию о выбранном предмете
 *
 * @author Murad Luguev on 08-08-2021
 */
class AboutAmiiboFragment : BaseFragment<AboutAmiiboViewModel>(R.layout.fragment_about_amiibo) {

    private lateinit var amiiboImageView: ImageView
    private lateinit var amiiboTailTextView: TextView
    private lateinit var amiiboSeriesTextView: TextView
    private lateinit var amiiboGameSeriesTextView: TextView
    private lateinit var amiiboTypeTextView: TextView
    private lateinit var amiiboInfoContainer: ViewGroup

    override fun provideViewModel(): AboutAmiiboViewModel {
        return ViewModelProvider(this)[AboutAmiiboViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amiiboImageView = view.findViewById(R.id.amiiboImage)
        amiiboTailTextView = view.findViewById(R.id.amiiboName)
        amiiboSeriesTextView = view.findViewById(R.id.amiiboSeries)
        amiiboGameSeriesTextView = view.findViewById(R.id.amiiboGameSeries)
        amiiboTypeTextView = view.findViewById(R.id.amiiboType)
        amiiboInfoContainer = view.findViewById(R.id.amiiboInfoContainer)
        viewModel.amiibo.observe(viewLifecycleOwner, ::showInfo)
        loadInfoAboutAmiibo()
    }

    private fun showInfo(amiiboModel: AmiiboModel) {
        amiiboInfoContainer.visibility = View.VISIBLE
        Picasso.get()
            .load(amiiboModel.image)
            .fit()
            .centerInside()
            .into(amiiboImageView)
        amiiboTailTextView.text = amiiboModel.name
        amiiboSeriesTextView.text = amiiboModel.amiiboSeries
        amiiboGameSeriesTextView.text = amiiboModel.gameSeries
        amiiboTypeTextView.text = amiiboModel.type
    }

    override fun doOnRefresh() {
        amiiboInfoContainer.visibility = View.GONE
        loadInfoAboutAmiibo(true)
    }

    private fun loadInfoAboutAmiibo(forceReload: Boolean = false) {
        val args = requireArguments()
        if (args.containsKey(AMIIBO_TAIL_KEY))
            viewModel.getInfoAbout(args.getString(AMIIBO_TAIL_KEY)!!, forceReload)
    }

    companion object {
        const val TAG = "AboutAmiiboFragment"
        private const val AMIIBO_TAIL_KEY = "amiibo-name-key"

        /**
         * Фабричный метод для создания экземпляра [AboutAmiiboFragment] с заданным [AMIIBO_TAIL_KEY]
         *
         * @param amiiboTail "хвост" выбранного предмета
         * @return экземпляр класса [AboutAmiiboFragment]
         */
        fun newInstance(amiiboTail: String): AboutAmiiboFragment {
            val args = Bundle()
            args.putString(AMIIBO_TAIL_KEY, amiiboTail)
            val aboutAmiiboFragment = AboutAmiiboFragment()
            aboutAmiiboFragment.arguments = args
            return aboutAmiiboFragment
        }
    }
}