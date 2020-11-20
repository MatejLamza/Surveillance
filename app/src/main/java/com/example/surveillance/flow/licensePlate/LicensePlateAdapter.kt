package com.example.surveillance.flow.licensePlate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surveillance.data.LicensePlate
import com.example.surveillance.R
import kotlinx.android.synthetic.main.item_license_plate.view.*

class LicensePlateAdapter : RecyclerView.Adapter<LicensePlateAdapter.LicensePlateViewHolder>() {

    var licensePlates: MutableSet<LicensePlate> = mutableSetOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addNewLicensePlate(plate: LicensePlate) {
        licensePlates.add(plate)
        notifyItemChanged(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicensePlateViewHolder =
        LicensePlateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_license_plate, parent, false)
        )

    override fun getItemCount(): Int = licensePlates.size

    override fun onBindViewHolder(holder: LicensePlateViewHolder, position: Int) {
        holder.licensePlate = licensePlates.elementAt(position)
    }

    inner class LicensePlateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var licensePlate: LicensePlate? = null
            set(value) {
                field = value
                if (value != null) {
                    itemView.licensePlateItem.text = value.licensePlateNumber
                }
            }
    }
}