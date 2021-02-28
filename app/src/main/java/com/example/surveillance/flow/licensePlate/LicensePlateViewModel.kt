package com.example.surveillance.flow.licensePlate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveillance.data.CityCode
import com.example.surveillance.data.Plate
import com.example.surveillance.data.repository.LicensePlateRepository
import kotlinx.coroutines.launch

class LicensePlateViewModel(private val licensePlateRepository: LicensePlateRepository) :
    ViewModel() {

    private var _cityCodes = MutableLiveData<List<CityCode>>()
    val cityCodes: LiveData<List<CityCode>> = _cityCodes

    private val _currentPlate = MutableLiveData<Plate>()
    val currentPlate: LiveData<Plate> = _currentPlate


    init {
        viewModelScope.launch {
            try {
//                val temp = licensePlateRepository.getLicensePlate("RI1234AB")
//                Log.d("bbb", "Plate: $temp")
                _cityCodes.value = licensePlateRepository.getAllCityCodes()
            } catch (e: Exception) {
                Log.d("bbb", "error: ${e.message}")
            }
        }
    }

    fun pingAPI(plate: String) {
        viewModelScope.launch {
            try {
                _currentPlate.value = licensePlateRepository.getLicensePlate(plate)
            } catch (e: Exception) {
                Log.d("bbb", "Exception: ${e.message}")
            }
        }
    }

}