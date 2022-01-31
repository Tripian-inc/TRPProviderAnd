package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.databinding.AcExperienceMoreBinding
import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.extensions.observe

/**
 * Created by semihozkoroglu on 3.10.2020.
 */
class ACExperienceMore : BaseActivity<AcExperienceMoreBinding>() {

    override val binding: (LayoutInflater) -> AcExperienceMoreBinding = AcExperienceMoreBinding::inflate

    private val viewModel: ACExperienceMoreVM by injectVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTourDetail(intent.extras!!.getSerializable("experience") as ExperienceDetail)
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
    }

    override fun setReceivers() {
        observe(viewModel.onTourListener) {
            vi.tvDescription.text = it?.description
        }
    }
}