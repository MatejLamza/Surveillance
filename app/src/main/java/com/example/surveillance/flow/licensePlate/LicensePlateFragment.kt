package com.example.surveillance.flow.licensePlate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.surveillance.R
import com.example.surveillance.data.LicensePlate
import com.example.surveillance.utils.MockData
import kotlinx.android.synthetic.main.fragment_license_plate.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "SURVEILANCE_MAIN"

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
    }

    private fun bind() {
    }

    private fun setupUI() {
        cityCode.setAdapter(cityCodeDropDownAdapter)
        licensePlates.adapter = licensePlateAdapter

        cityCodeDropDownAdapter.cityCodes = MockData.cityCodes
        licensePlateAdapter.licensePlates = MockData.licensePlates

        cityCodeDropDownAdapter.onCityCodeClickListener = { cityCode ->
            code = cityCode
        }

        addLicensePlate.setOnClickListener {
            val plate = "$code-${licensePlate.text}"
            if (regex.containsMatchIn(plate))
                licensePlateAdapter.addNewLicensePlate(
                    LicensePlate(
                        plate
                    )
                )
            else
                Toast.makeText(
                    requireContext(),
                    "Invalid license plate format!",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}