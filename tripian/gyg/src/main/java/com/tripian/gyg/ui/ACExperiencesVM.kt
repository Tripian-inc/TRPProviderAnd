package com.tripian.gyg.ui

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.GetTours
import com.tripian.gyg.domain.model.Experiences
import com.tripian.gyg.util.extensions.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class ACExperiencesVM : BaseViewModel() {

    var onTourListener = MutableLiveData<List<Experiences>>()

    fun getList() = launch {
        GetTours().execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .collect {
                onTourListener.postValue(it)
            }
    }

    fun onClickedBack() {
        goBack()
    }
}
