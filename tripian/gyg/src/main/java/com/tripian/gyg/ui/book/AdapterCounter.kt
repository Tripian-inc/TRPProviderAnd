package com.tripian.gyg.ui.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.BookCounter

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
abstract class AdapterCounter constructor(val context: Context, val items: List<BookCounter>?) :
    RecyclerView.Adapter<AdapterCounter.TourCounter>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    abstract fun onChangedCounter(counter: BookCounter)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourCounter {
        return TourCounter(inflater.inflate(R.layout.item_counter, parent, false))
    }

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) return 0

        return items.size
    }

    override fun onBindViewHolder(holder: TourCounter, position: Int) {
        val item = items!![position]

        with(holder) {
            tvCategory.text = item.category
            tvDescription.text = item.description
            tvCount.text = "${item.count}"
            imMinus.setOnClickListener {
                if (item.count >= item.minParticipant) {
                    item.count -= 1

                    tvCount.text = "${item.count}"
                }

                onChangedCounter(item)
            }

            imPlus.setOnClickListener {
                if (item.count < item.maxParticipant) {
                    item.count += 1

                    tvCount.text = "${item.count}"
                }

                onChangedCounter(item)
            }
        }
    }

    class TourCounter constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvCategory: TextView = vi.findViewById(R.id.tvCategory)
        val tvDescription: TextView = vi.findViewById(R.id.tvDescription)
        val tvCount: TextView = vi.findViewById(R.id.tvCount)
        val imMinus: ImageView = vi.findViewById(R.id.imMinus)
        val imPlus: ImageView = vi.findViewById(R.id.imPlus)
    }
}