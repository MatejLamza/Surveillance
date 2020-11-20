package com.example.surveillance.flow.licensePlate


import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.surveillance.R

class LicensePlateCityCodeArrayAdapter(textView: AutoCompleteTextView) :
    ArrayAdapter<String>(textView.context, R.layout.item_city_code_drop_down) {

    var onCityCodeClickListener: ((cityCode: String) -> Unit)? = null
    var cityCodes: List<String> = emptyList()
        set(value) {
            field = value
            this.clear()
            this.addAll(value.map {
                it
            })
            notifyDataSetChanged()
        }

    init {
        textView.setOnItemClickListener { _, _, position, _ ->
            onCityCodeClickListener?.invoke(cityCodes[position])
        }
    }
}