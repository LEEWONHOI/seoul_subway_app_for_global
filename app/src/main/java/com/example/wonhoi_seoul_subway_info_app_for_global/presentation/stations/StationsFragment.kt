package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stations

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_seoul_subway_info_app_for_global.databinding.FragmentStationsBinding
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toGone
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toVisible
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.BaseFragment
import org.koin.android.ext.android.inject

class StationsFragment : BaseFragment<StationsViewModel, FragmentStationsBinding>() {

    override val viewModel by inject<StationsViewModel>()

    override fun getViewBinding(): FragmentStationsBinding = FragmentStationsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()

    }

    private fun showStations(stations: List<Station>) {
        (binding.recyclerView.adapter as? StationsAdapter)?.run {
            this.data = stations

            notifyDataSetChanged()
        }
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = StationsAdapter()
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun bindViews() {
        binding.searchEditText.addTextChangedListener {  editable ->
            viewModel.filterStations(editable.toString())
        }
        // nav 를 이용해서 프레그먼트를 교체해주는 방법 ( nav 에서 선언한 toStationArrivalsAction 가 작동하는거임)
        (binding.recyclerView.adapter as? StationsAdapter)?.apply {
            onItemClickListener = { clickedStation ->
                val action = StationsFragmentDirections.toStationArrivalsAction(clickedStation)
                findNavController().navigate(action)
            }
            onFavoriteClickListener = {clickedFavorite ->
                viewModel.toggleStationFavorite(clickedFavorite)
            }
        }
    }

    override fun observeData() = viewModel.stationLiveData.observe(viewLifecycleOwner){
        when(it) {
            is StationsState.Uninitialized -> {

            }
            is StationsState.Loading -> {
                binding.progressBar.toVisible()
            }
            is StationsState.Success -> {
                binding.progressBar.toGone()
                showStations(it.stations)
//                val list = viewModel.getEngName()
//                Toast.makeText(requireContext(), "$list", Toast.LENGTH_LONG).show()
//                Log.d("test111", "$list")
//                val tes = viewModel.getStationEngName()
//                Toast.makeText(requireContext(), "$", Toast.LENGTH_LONG).show()

            }
            is StationsState.Error -> {
                binding.progressBar.toGone()
                Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}