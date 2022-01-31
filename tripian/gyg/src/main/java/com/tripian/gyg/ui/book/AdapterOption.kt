package com.tripian.gyg.ui.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripian.gyg.R
import com.tripian.gyg.domain.model.BookOption

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
abstract class AdapterOption constructor(val context: Context, val items: List<BookOption>?) :
    RecyclerView.Adapter<AdapterOption.TourBook>() {

    abstract fun onSelectedOption(option: BookOption)

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourBook {
        return TourBook(inflater.inflate(R.layout.item_book_option, parent, false))
    }

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) return 0

        return items.size
    }

    override fun onBindViewHolder(holder: TourBook, position: Int) {
        val item = items!![position]

        with(holder) {
            tvTitle.text = item.title
            tvDay.text = item.day
            tvTime.text = item.time
            tvPrice.text = item.price.toString()
            tvPriceDescription.text = "For ${item.count} person"

            btnSelect.setOnClickListener { onSelectedOption(item) }
        }
    }

    class TourBook constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        val tvTitle: TextView = vi.findViewById(R.id.tvTitle)
        val tvDay: TextView = vi.findViewById(R.id.tvDay)
        val tvTime: TextView = vi.findViewById(R.id.tvTime)
        val tvPrice: TextView = vi.findViewById(R.id.tvPrice)
        val tvPriceDescription: TextView = vi.findViewById(R.id.tvPriceDescription)
        val btnSelect: Button = vi.findViewById(R.id.btnSelect)
    }
}