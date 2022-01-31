package com.tripian.gyg.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.ExperienceReview

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
class AdapterExperienceReviewItem constructor(
    val context: Context,
    val items: List<ExperienceReview>
) : RecyclerView.Adapter<AdapterExperienceReviewItem.ExperienceReviewHolder>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceReviewHolder {
        return ExperienceReviewHolder(inflater.inflate(R.layout.item_experience_review, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperienceReviewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            item.comment?.let {
                tvComment.text = it
                tvComment.isVisible = true
            } ?: kotlin.run { tvComment.isVisible = false }

            tvAuthor.text = item.reviewer
            tvReviewDate.text = item.date

            if (item.rating != null) {
                rateBar.isVisible = true
                tvRate.isVisible = true

                rateBar.rating = item.rating!!
                tvRate.text = "${item.rating}"
            } else {
                rateBar.isVisible = false
                tvRate.isVisible = false
            }
        }
    }

    class ExperienceReviewHolder constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvComment: TextView = vi.findViewById(R.id.tvComment)
        val tvAuthor: TextView = vi.findViewById(R.id.tvAuthor)
        val rateBar: RatingBar = vi.findViewById(R.id.rateBar)
        val tvRate: TextView = vi.findViewById(R.id.tvRate)
        val tvReviewDate: TextView = vi.findViewById(R.id.tvReviewDate)
    }
}