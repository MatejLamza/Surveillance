package com.example.surveillance.flow.licensePlate

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.surveillance.R
import com.example.surveillance.data.LicensePlate
import com.example.surveillance.utils.MockData
import kotlinx.android.synthetic.main.fragment_license_plate.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

private const val TAG = "SURVEILANCE_MAIN"
private const val CHANNEL_NAME = "NotifName"

class LicensePlateFragment : Fragment() {

    private val cityCodeDropDownAdapter: LicensePlateCityCodeArrayAdapter by lazy {
        LicensePlateCityCodeArrayAdapter(cityCode)
    }
    private val licensePlateAdapter: LicensePlateAdapter by lazy {
        LicensePlateAdapter()
    }
    private val licensePlateViewModel: LicensePlateViewModel by viewModel()

    private lateinit var code: String
    private val regex = Regex("^[A-Z]{2}-[0-9]{3,4}-[A-Z]{1,2}\$")
    private var plate: String? = null

    private lateinit var notificationManager: NotificationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_license_plate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        bind()
        val beeperControl = ApiPingger()
        beeperControl.pingAPIForAnHour()
    }

    private fun bind() {
        licensePlateViewModel.currentPlate.observe(viewLifecycleOwner) {
            Log.d("bbb", "Notifikacija: $it")
        }
    }

    private fun setupUI() {
        cityCode.setAdapter(cityCodeDropDownAdapter)
        licensePlates.adapter = licensePlateAdapter

        cityCodeDropDownAdapter.cityCodes = MockData.cityCodes

        cityCodeDropDownAdapter.onCityCodeClickListener = { cityCode ->
            code = cityCode
        }

        addLicensePlate.setOnClickListener {
            Log.d("bbb", "Click")
            plate = "$code-${licensePlate.text}"
            if (regex.containsMatchIn(plate!!)) {
                licensePlateAdapter.addNewLicensePlate(
                    LicensePlate(
                        plate!!
                    )
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Invalid license plate format!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    inner class ApiPingger {
        private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        fun pingAPIForAnHour() {
            val pinger = Runnable {
                Log.d("bbb", "PING")
                plate?.let { licensePlateViewModel.pingAPI(it.filterNot { plate -> plate == '-' }) }
            }
            val beeperHandle: ScheduledFuture<*> =
                scheduler.scheduleAtFixedRate(pinger, 10, 10, TimeUnit.SECONDS)
            scheduler.schedule(Runnable { beeperHandle.cancel(true) }, 60 * 60, TimeUnit.SECONDS)
        }
    }
}