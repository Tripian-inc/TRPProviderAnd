package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.core.view.isVisible
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.databinding.AcExperiencesBinding
import com.tripian.gyg.domain.model.ExperiencesItem
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.datalistener.startActivity
import com.tripian.gyg.util.extensions.observe

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class ACExperiences : BaseActivity<AcExperiencesBinding>() {

    override val binding: (LayoutInflater) -> AcExperiencesBinding = AcExperiencesBinding::inflate

    private val viewModel: ACExperiencesVM by injectVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getList()
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
    }

    override fun setReceivers() {
        observe(viewModel.onTourListener) {
            if (it.isNullOrEmpty()) {
                vi.rvList.isVisible = false
            } else {
                vi.rvList.isVisible = true

                vi.rvList.adapter = object : AdapterExperiences(this, it) {
                    override fun onClickedItem(item: ExperiencesItem) {
                        startActivity(ACExperienceDetail::class, bundle = Bundle().apply {
                            putLong("tourId", item.id!!)
                        })
                    }
                }
            }
        }
    }
}