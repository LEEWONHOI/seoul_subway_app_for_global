package com.example.wonhoi_seoul_subway_info_app_for_global.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wonhoi_seoul_subway_info_app_for_global.R
import com.example.wonhoi_seoul_subway_info_app_for_global.databinding.ActivityMainBinding
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toGone
import com.example.wonhoi_seoul_subway_info_app_for_global.extension.toVisible
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals.StationArrivalsFragmentArgs

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navigationController)   // 액션바와 네비게이션을 연동한다.
    }

    private fun bindViews() {
        navigationController.addOnDestinationChangedListener { _, destination, argument ->
            if (destination.id == R.id.station_arrivals_dest) { // 화면 전환용 체크
                // 역 도착 정보 화면에 갈때마다 argument 로 전달받은 station.name 으로 toolbar 의 title 을 변경해준다.
                title = StationArrivalsFragmentArgs.fromBundle(argument!!).station.engName     // 타입을 알 수 있는 argument 로 받은 뒤 station 정보 가져오기
                binding.toolbar.toVisible()
            } else {
                binding.toolbar.toGone()
            }
        }
    }
}