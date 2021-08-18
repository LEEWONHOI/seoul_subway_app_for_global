package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_seoul_subway_info_app_for_global.R
import com.example.wonhoi_seoul_subway_info_app_for_global.databinding.FragmentStationArrivalsBinding
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toGone
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toVisible
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class StationArrivalsFragment :
    BaseFragment<StationArrivalsViewModel, FragmentStationArrivalsBinding>() {

    private val arguments: StationArrivalsFragmentArgs by navArgs()

    override val viewModel by inject<StationArrivalsViewModel> {
        parametersOf(arguments.station)
    }

    override fun getViewBinding(): FragmentStationArrivalsBinding =
        FragmentStationArrivalsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // onCreateOptionsMenu 호출을 위해서
    }

    var stationHashMap = HashMap<String, String>()

    override fun observeData() =
        viewModel.stationArrivalsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is StationArrivalsState.Uninitialized -> { }
                is StationArrivalsState.Loading -> {
                    binding.progressBar.toVisible()
                }
                is StationArrivalsState.Success -> {
                    binding.progressBar.toGone()
                    showStationArrivals(it.arriveInfo)
                    stationHashMap = it.stationNameAndEngNameHashMap
                    for ((key, value) in stationHashMap) {
                        Log.d("test1staionFragment","전체 : ${key} : ${value}")
                    }
                }
                is StationArrivalsState.Error -> {
                    binding.progressBar.toGone()
                    binding.recyclerView.toGone()
                    binding.errorDescriptionTextView.toVisible()
                    binding.errorDescriptionTextView.text = getString(it.messageId)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_station_arrivals, menu)
        menu.findItem(R.id.favoriteAction).apply {
            setIcon(
                if (arguments.station.isFavorited) {    // 프레그먼트의 isFavorite 값
                    R.drawable.ic_star
                } else {
                    R.drawable.ic_star_empty
                }
            )
            isChecked = arguments.station.isFavorited   // onOptionsItemSelected 에 사용할 용도
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.refreshAction -> {
                viewModel.fetchStationArrivals()
                true
            }
            R.id.favoriteAction -> {
                item.isChecked = !item.isChecked
                item.setIcon(
                    if (item.isChecked) {
                        R.drawable.ic_star
                    } else {
                        R.drawable.ic_star_empty
                    }
                )
                viewModel.toggleStationFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showStationArrivals(arrivalInformation: List<ArrivalInformation>) {
        binding.errorDescriptionTextView.toGone()
        (binding.recyclerView.adapter as? StationArrivalsAdapter)?.run {
            this.data = arrivalInformation
            notifyDataSetChanged()
        }
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = StationArrivalsAdapter(this@StationArrivalsFragment)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun bindViews() {}
}
