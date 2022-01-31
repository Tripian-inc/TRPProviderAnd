package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.databinding.AcExperienceDetailBinding
import com.tripian.gyg.ui.book.ACBook
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.datalistener.startActivity
import com.tripian.gyg.util.extensions.observe
import com.tripian.gyg.util.extensions.roundTo

/**
 * Created by semihozkoroglu on 3.10.2020.
 */
class ACExperienceDetail : BaseActivity<AcExperienceDetailBinding>() {

    override val binding: (LayoutInflater) -> AcExperienceDetailBinding = AcExperienceDetailBinding::inflate

    private val viewModel: ACExperienceDetailVM by injectVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(vi.root)

        viewModel.getTours(intent.extras!!.getLong("tourId"))
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
    }

    override fun setReceivers() {
        observe(viewModel.onTourDetailListener) { tour ->
            vi.tvTitle.text = tour?.title
            vi.rateBar.rating = tour?.rating ?: 0f
            vi.tvRate.text = tour?.rating?.roundTo(1).toString()

            if (tour?.duration.isNullOrEmpty()) {
                vi.llDuration.isVisible = false
            } else {
                vi.tvDuration.text = tour?.duration
            }

            if (tour?.liveGuide.isNullOrEmpty()) {
                vi.llLiveGuide.isVisible = false
            } else {
                vi.tvLiveGuide.text = tour?.liveGuide
            }

            if (tour?.cancellation.isNullOrEmpty()) {
                vi.llCancellation.isVisible = false
            } else {
                vi.tvCancellation.text = tour?.cancellation
            }

            vi.tvAbstract.text = tour?.abstract

            if (tour?.highlights.isNullOrEmpty()) {
                vi.rvHighlights.isVisible = false
            } else {
                vi.rvHighlights.adapter = AdapterExperienceHighlight(this, tour?.highlights!!)
            }

            if (tour?.include.isNullOrEmpty()) {
                vi.llIncluded.isVisible = false
            } else {
                vi.tvInclude.text = tour?.include
            }

            if (tour?.exclude.isNullOrEmpty()) {
                vi.llExcluded.isVisible = false
            } else {
                vi.tvExclude.text = tour?.exclude
            }

            vi.tvReadMoreDetail.setOnClickListener {
                startActivity(ACExperienceMore::class, bundle = Bundle().apply {
                    putSerializable("experience", viewModel.experience)
                })
            }
            vi.tvReadAllReviews.setOnClickListener {
                startActivity(ACExperienceReviews::class, bundle = Bundle().apply {
                    putSerializable("experience", viewModel.experience)
                })
            }

            vi.tvPrice.text = "$${tour?.price}"

            tour?.images?.let { vi.vpPager.adapter = AdapterImages(this, it) }

            tour?.reviews?.let {
                vi.tvReviewCount.text = "${it.size} Reviews"

                val reviews = if (it.size > 3) {
                    it.subList(0, 3)
                } else {
                    it
                }

                vi.rvReviews.adapter = AdapterExperienceReviewItem(this, reviews)
            } ?: kotlin.run {
                vi.llReviews.isVisible = false
                vi.tvReviewCount.isVisible = false
            }

//            tour?.url?.let { url -> openCustomTabExt(url) } ?: run { vi.btnApply.isVisible = false }
            vi.btnApply.setOnClickListener {
                tour?.id?.let { tourId ->
                    startActivity(ACBook::class, Bundle().apply {
                        putLong("tourId", tourId)
                        putString("title", tour.title ?: "")
                    })
                }
            }
        }
    }
}