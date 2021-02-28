package com.example.surveillance.flow.licensePlate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveillance.data.remote.response.PlateAPIItem
import com.example.surveillance.data.repository.LicensePlateRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LicensePlateViewModel(private val licensePlateRepository: LicensePlateRepository) :
    ViewModel() {


    private val _currentPlate = MutableLiveData<PlateAPIItem>()
    val currentPlate: LiveData<PlateAPIItem> = _currentPlate

    fun pingAPIwithGivenPlates(plates: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                Log.d("bbb", "Pinging api with parameter: $plates")
                val plates = licensePlateRepository.fetchPlates(plates)
                Log.d("bbb", "Size: ${plates.size}")
                delay(3000)
                plates.forEach {
                    if (it.detected) {
                        Log.d("bbb", "Detected")
                        _currentPlate.value = it
                    }
                    delay(10000)
                }
            }.onFailure {
                Log.e("bbb", "Error: ${it.message}", it)
            }
        }
    }
}