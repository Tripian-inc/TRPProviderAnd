package com.tripian.gyg.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.Experiences
import com.tripian.gyg.domain.model.ExperiencesItem

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
abstract class AdapterExperiences constructor(val context: Context, val items: List<Experiences>) :
    RecyclerView.Adapter<AdapterExperiences.ExperienceRoot>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    abstract fun onClickedItem(item: ExperiencesItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceRoot {
        return ExperienceRoot(inflater.inflate(R.layout.item_experiences, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperienceRoot, position: Int) {
        val item = items[position]

        with(holder) {
            tvTitle.text = item.title

            if (rvInnerList.adapter == null) {
                val adapter = object : AdapterExperiencesItem(context, item.items) {
                    override fun onClickedItem(item: ExperiencesItem) {
                        this@AdapterExperiences.onClickedItem(item)
                    }
                }

                rvInnerList.adapter = adapter
            } else {
                rvInnerList.adapter?.notifyDataSetChanged()
            }
        }
    }

    class ExperienceRoot constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvTitle: TextView = vi.findViewById(R.id.tvTitle)
        val rvInnerList: RecyclerView = vi.findViewById(R.id.rvInnerList)

        init {
            rvInnerList.layoutManager =
                LinearLayoutManager(vi.context, RecyclerView.HORIZONTAL, false)
        }
    }
}