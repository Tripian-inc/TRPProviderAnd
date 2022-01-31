package com.tripian.gyg.ui

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.GetTourReviews
import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.domain.model.ExperienceReview
import com.tripian.gyg.util.extensions.launch
import com.tripian.gyg.util.extensions.navigateToFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class ACExperienceReviewsVM : BaseViewModel() {

    var onTourListener = MutableLiveData<List<ExperienceReview>>()

    private var experience: ExperienceDetail? = null

    fun getTourDetail(e: ExperienceDetail) {
        experience = e

        getFilteredReview(0) // newest
    }

    fun getFilteredReview(sortBy: Int) = launch {
        GetTourReviews(experience!!.id!!, sortBy).execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .collect {
                onTourListener.postValue(it)
            }
    }

    fun onClickedBack() {
        goBack()
    }

    fun onClickedFilter() {
        navigateToFragment(FRReviewFilter.newInstance())
    }
}
