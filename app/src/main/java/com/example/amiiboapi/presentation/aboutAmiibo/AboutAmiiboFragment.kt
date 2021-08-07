package com.example.amiiboapi.presentation.aboutAmiibo

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

class AboutAmiiboFragment : BaseFragment<AboutAmiiboViewModel>(R.layout.fragment_about_amiibo) {
    
    private lateinit var amiiboImageView: ImageView
    private lateinit var amiiboTailTextView: TextView
    private lateinit var amiiboSeriesTextView: TextView
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
        amiiboTypeTextView = view.findViewById(R.id.amiiboType)
        amiiboInfoContainer = view.findViewById(R.id.amiiboInfoContainer)
        viewModel.amiibo.observe(viewLifecycleOwner, ::showInfo)
        arguments?.let { args ->
            if(args.containsKey(AMIIBO_NAME_KEY))
                viewModel.loadInfoAbout(args.getString(AMIIBO_NAME_KEY)!!)
        }
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
        amiiboTypeTextView.text = amiiboModel.type
    }
    
    companion object {
        const val TAG = "AboutAmiiboFragment"
        private const val AMIIBO_NAME_KEY = "amiibo-name-key"

        fun newInstance(amiiboTail: String): AboutAmiiboFragment {
            val args = Bundle()
            args.putString(AMIIBO_NAME_KEY, amiiboTail)
            val aboutAmiiboFragment = AboutAmiiboFragment()
            aboutAmiiboFragment.arguments = args
            return aboutAmiiboFragment
        }
    }
}