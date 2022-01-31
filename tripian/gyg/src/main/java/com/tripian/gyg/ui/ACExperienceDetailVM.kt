package com.tripian.gyg.ui

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.GetTourDetail
import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.util.extensions.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class ACExperienceDetailVM : BaseViewModel() {

    var experience: ExperienceDetail? = null
    var onTourDetailListener = MutableLiveData<ExperienceDetail>()

    fun getTours(tourId: Long) = launch {
        GetTourDetail(tourId)
            .execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .collect {
                experience = it
                onTourDetailListener.postValue(it)
            }
    }

    fun onClickedBack() {
        goBack()
    }
}
