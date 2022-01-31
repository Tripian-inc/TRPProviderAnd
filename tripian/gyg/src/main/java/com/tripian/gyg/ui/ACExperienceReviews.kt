package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.databinding.AcExperienceReviewsBinding
import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.extensions.observe

/**
 * Created by semihozkoroglu on 3.10.2020.
 */
class ACExperienceReviews : BaseActivity<AcExperienceReviewsBinding>() {

    override val binding: (LayoutInflater) -> AcExperienceReviewsBinding = AcExperienceReviewsBinding::inflate

    private val viewModel: ACExperienceReviewsVM by injectVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTourDetail(intent.extras!!.getSerializable("experience") as ExperienceDetail)
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
        vi.imFilter.setOnClickListener { viewModel.onClickedFilter() }

        setFragmentResultListener("ReviewFilter") { requestKey, result ->
            viewModel.getFilteredReview(result.getInt("sortType"))
        }
    }

    override fun setReceivers() {
        observe(viewModel.onTourListener) {
            vi.rvList.adapter = AdapterExperienceReviewItem(this, it!!)
        }
    }
}