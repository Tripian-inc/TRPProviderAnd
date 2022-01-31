package com.tripian.gyg.ui.book

import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.util.extensions.navigateToFragment

class ACBookVM : BaseViewModel() {

    fun onClickedBack() {
        goBack()
    }

    fun showDatePage() {
        navigateToFragment(FRTourDate.newInstance(), addToBackStack = false)
    }
}
