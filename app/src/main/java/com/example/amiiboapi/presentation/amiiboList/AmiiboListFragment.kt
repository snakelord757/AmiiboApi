package com.example.amiiboapi.presentation.amiiboList

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amiiboapi.R
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.presentation.aboutAmiibo.AboutAmiiboFragment
import com.example.amiiboapi.presentation.amiiboList.adapter.AmiiboAdapter
import com.example.amiiboapi.presentation.common.BaseFragment
import com.example.amiiboapi.presentation.extensions.navigation

class AmiiboListFragment : BaseFragment<AmiiboListViewModel>(R.layout.fragment_with_list) {

    private lateinit var amiibosListRecyclerView: RecyclerView

    override fun provideViewModel(): AmiiboListViewModel {
        return ViewModelProvider(this).get(AmiiboListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amiibosListRecyclerView = view.findViewById(R.id.listOfElements)
        viewModel.amiiboListMinimal.observe(viewLifecycleOwner, ::showList)
        val gridLayoutManager = GridLayoutManager(requireContext(), ITEMS_SPAN_COUNT)
        amiibosListRecyclerView.layoutManager = gridLayoutManager
        arguments?.let { args->
            if(args.containsKey(GAME_SERIES_KEY))
                viewModel.getAmiiboByGameSeries(args.getString(GAME_SERIES_KEY)!!)
        }
    }

    private fun showList(amiibosList: List<AmiiboModelMinimal>) {
        val amiibosAdapter = AmiiboAdapter(amiibosList, ::showAmiiboInfo)
        amiibosListRecyclerView.adapter = amiibosAdapter
        amiibosListRecyclerView.visibility = View.VISIBLE
    }

    private fun showAmiiboInfo(amiiboTail: String) {
        navigation.move(
            AboutAmiiboFragment.newInstance(amiiboTail),
            AboutAmiiboFragment.TAG
        )
    }

    companion object {
        const val TAG = "AmiiboListFragment"
        private const val GAME_SERIES_KEY = "game_series_key"
        private const val ITEMS_SPAN_COUNT = 2

        fun newInstance(gameSeriesKey: String): AmiiboListFragment {
            val args = Bundle()
            args.putString(GAME_SERIES_KEY, gameSeriesKey)
            val amiiboListFragment = AmiiboListFragment()
            amiiboListFragment.arguments = args
            return amiiboListFragment
        }
    }
}