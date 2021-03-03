package com.example.surveillance.flow.licensePlate

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.observe
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.surveillance.R
import com.example.surveillance.data.LicensePlate
import com.example.surveillance.data.remote.response.PlateAPIItem
import com.example.surveillance.utils.MockData
import com.example.surveillance.utils.MyBroadcast
import com.example.surveillance.utils.MyService
import kotlinx.android.synthetic.main.fragment_license_plate.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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

    private var plates: MutableList<String> = mutableListOf()

    private lateinit var notificationManager: NotificationManager
    private var myBroadcastReceiver: MyBroadcast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_license_plate, container, false)
    }

    override fun onResume() {
        myBroadcastReceiver = MyBroadcast()
        val intentFilter = IntentFilter("PlateIntent")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(myBroadcastReceiver!!, intentFilter)
        super.onResume()
    }

    override fun onPause() {
        if (myBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(
                myBroadcastReceiver!!
            )
        }
        myBroadcastReceiver = null
        super.onPause()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        bind()
//        val beeperControl = ApiPingger()
//        beeperControl.pingAPIForAnHour()
    }

    private fun bind() {
        licensePlateViewModel.currentPlate.distinctUntilChanged().observe(viewLifecycleOwner) {
            Log.d("bbb", "Notifikacija: $it")
            setupNotification(plate = it)
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
            plate = "$code-${licensePlate.text}"
            if (regex.containsMatchIn(plate!!)) {
                licensePlateAdapter.addNewLicensePlate(LicensePlate(plate!!))
                plates = licensePlateAdapter.licensePlates.map {
                    it.licensePlateNumber
                } as MutableList<String>
            } else {
                Toast.makeText(
                    requireContext(),
                    "Invalid license plate format!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        startBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyService::class.java)
            intent.putExtra("KEY", plates.joinToString(","))
            requireActivity().startService(intent)
        }
    }

    private fun setupNotification(plate: PlateAPIItem) {
        val notificationBuilder = NotificationCompat.Builder(requireContext(), "17")
        val intent = Intent(requireContext(), TestFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0)

        val bigStyleText = NotificationCompat.BigTextStyle().bigText(
            "Plate ${plate.plate} you requested was seen" +
                    "${Date().time}"
        )
            .setBigContentTitle("Plate ${plate.plate} was detected!")
            .setSummaryText("Plate has been found")

        notificationBuilder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Plate ${plate.plate} was detected!")
            .setContentText("Plate found!")
            .setStyle(bigStyleText)
            .priority = Notification.PRIORITY_MAX

        notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "17"
            val notificationChannel =
                NotificationChannel(channelID, "ChannelNotify", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder.setChannelId(channelID)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }
}