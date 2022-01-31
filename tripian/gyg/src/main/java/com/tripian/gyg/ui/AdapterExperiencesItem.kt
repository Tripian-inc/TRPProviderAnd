package com.tripian.gyg.ui

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.ExperiencesItem
import com.tripian.gyg.util.extensions.dp2Px

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
abstract class AdapterExperiencesItem constructor(
    val context: Context,
    val items: ArrayList<ExperiencesItem>
) : RecyclerView.Adapter<AdapterExperiencesItem.ExperiencesInner>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var ROUND_CORNER = dp2Px(8f).toInt()

    abstract fun onClickedItem(item: ExperiencesItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperiencesInner {
        return ExperiencesInner(inflater.inflate(R.layout.item_experiences_inner, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperiencesInner, position: Int) {
        val item = items[position]

        with(holder) {
            tvTitle.text = item.title

            if (item.rating != null) {
                rateBar.isVisible = true
                tvRate.isVisible = true

                rateBar.rating = item.rating!!
                tvRate.text = "(${item.rateCount})"
            } else {
                rateBar.isVisible = false
                tvRate.isVisible = false
            }

            tvBestSeller.isVisible = item.bestSeller

            if (item.price != null) {
                tvPrice.isVisible = true
                tvPrice.text = "${item.price}$"
            } else {
                tvPrice.isVisible = false
            }

            if (!TextUtils.isEmpty(item.image)) {
                Glide.with(context).load(item.image)
                    .apply(
                        RequestOptions().transform(
                            CenterCrop(),
                            RoundedCorners(ROUND_CORNER)
                        )
                    )
                    .into(imImage)
            }

            llRoot.setOnClickListener { onClickedItem(item) }
        }
    }

    class ExperiencesInner constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val llRoot: LinearLayout = vi.findViewById(R.id.llRoot)
        val imImage: ImageView = vi.findViewById(R.id.imImage)
        val tvTitle: TextView = vi.findViewById(R.id.tvTitle)
        val tvRate: TextView = vi.findViewById(R.id.tvRate)
        val rateBar: RatingBar = vi.findViewById(R.id.rateBar)
        val tvPrice: TextView = vi.findViewById(R.id.tvPrice)
        val tvBestSeller: TextView = vi.findViewById(R.id.tvBestSeller)
    }
}