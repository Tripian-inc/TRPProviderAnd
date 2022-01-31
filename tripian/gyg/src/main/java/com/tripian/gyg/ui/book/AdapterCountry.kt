package com.tripian.gyg.ui.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.CountryCode
import java.util.*

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
abstract class AdapterCountry constructor(val context: Context, val items: Array<out Locale>) :
    RecyclerView.Adapter<AdapterCountry.Country>() {

    abstract fun onSelectedCountry(country: CountryCode)

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Country {
        return Country(inflater.inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) return 0

        return items.size
    }

    override fun onBindViewHolder(holder: Country, position: Int) {
        with(holder) {
            tvText.text = items.get(position).displayName
            tvText.setOnClickListener {
                onSelectedCountry(CountryCode().apply {
                    displayName = items.get(position).displayName
                    code = items.get(position).country
                })
            }
        }
    }

    class Country constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvText: TextView = vi.findViewById(R.id.tvText)
    }
}