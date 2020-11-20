package com.example.surveillance.flow.licensePlate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveillance.data.CityCode
import com.example.surveillance.data.repository.LicensePlateRepository
import kotlinx.coroutines.launch

class LicensePlateViewModel(private val licensePlateRepository: LicensePlateRepository) :
    ViewModel() {

    private var _cityCodes = MutableLiveData<List<CityCode>>()
    val cityCodes: LiveData<List<CityCode>> = _cityCodes

    init {
        viewModelScope.launch {
            licensePlateRepository.getLicensePlate()
            _cityCodes.value = licensePlateRepository.getAllCityCodes()
        }
    }

}