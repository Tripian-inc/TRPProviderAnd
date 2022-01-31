package com.tripian.gyg.ui

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.model.ExperienceDetail

class ACExperienceMoreVM : BaseViewModel() {

    var onTourListener = MutableLiveData<ExperienceDetail>()

    fun getTourDetail(experience: ExperienceDetail) {
        onTourListener.postValue(experience)
    }

    fun onClickedBack() {
        goBack()
    }
}
