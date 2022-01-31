package com.tripian.gyg.util.extensions

import androidx.fragment.app.Fragment
import com.tripian.gyg.util.fragment.FragmentFactory
import com.tripian.gyg.util.fragment.TransitionType
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.util.fragment.AnimationType

/**
 * fragmentManager BaseActivity'den ve BaseFragment'dan her bir view'in ViewModel'ine set edilmektedir.
 *
 * @see BaseFragment.onCreateView
 * @see BaseActivity.onCreate
 *
 * Activity supportFragmentManager kullanirken, Fragment'lar childFragmentManager kullanmaktadir.
 * inner fragment kullanim durumunda eğer backstack kullanilmayacak ise fragmentManagerEnable true gonderilmesi gerekmektedir.
 * backPress override manuel pop edilmelidir
 *
 * @see BaseViewModel.fragmentManager
 */
fun BaseViewModel.navigateToFragment(
    fragment: Fragment,
    addToBackStack: Boolean = true,
    clearBackStack: Boolean = false,
    viewId: Int = R.id.container,
    transitionType: TransitionType = TransitionType.REPLACE,
    animation: AnimationType = AnimationType.NO_ANIM
) {

    val factory = FragmentFactory.Builder(fragment)
        .addToBackStack(addToBackStack)
        .setClearBackStack(clearBackStack)
        .setViewId(viewId)
        .setTransitionType(transitionType)
        .setAnimation(animation)
        .build()

    showFragment(factory)
}