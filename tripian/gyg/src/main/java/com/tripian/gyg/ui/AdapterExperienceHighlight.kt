package com.tripian.gyg.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
class AdapterExperienceHighlight constructor(val context: Context, val items: List<String>) :
    RecyclerView.Adapter<AdapterExperienceHighlight.ExperienceHighlight>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceHighlight {
        return ExperienceHighlight(inflater.inflate(R.layout.item_experience_detail_highlights, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperienceHighlight, position: Int) {
        with(holder) {
            tvText.text = items[position]
        }
    }

    class ExperienceHighlight constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvText: TextView = vi.findViewById(R.id.tvText)
    }
}