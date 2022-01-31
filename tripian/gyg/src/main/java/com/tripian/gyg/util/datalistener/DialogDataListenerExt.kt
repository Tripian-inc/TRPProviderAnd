package com.tripian.gyg.util.datalistener

import androidx.lifecycle.*
import com.tripian.gyg.util.extensions.showFragment
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.base.BaseBottomDialogFragment
import com.tripian.gyg.base.BaseDialogFragment
import com.tripian.gyg.base.BaseViewModel
import kotlinx.coroutines.flow.collect

inline fun <reified VM : BaseViewModel> Lazy<VM>.viewListener(owner: BaseBottomDialogFragment<*>) {
    with(owner) {
        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.dialog.collect {
                    it?.let {
                        if (activity != null) {
                            (activity as BaseActivity<*>).showDialog(it)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showToast.collect {
                    it?.let {
                        if (activity != null) {
                            (activity as BaseActivity<*>).showToast(it)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showLoading.collect {
                    if (activity != null) {
                        (activity as BaseActivity<*>).showLoading()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.hideLoading.collect {
                    if (activity != null) {
                        (activity as BaseActivity<*>).hideLoading()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.goBack.collect { it?.let { activity?.onBackPressed() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.finish.collect { it?.let { activity?.finish() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showFragment.collect { it?.let { activity?.showFragment(it) } }
            }
        }
    }
}

inline fun <reified VM : BaseViewModel> Lazy<VM>.viewListener(owner: BaseDialogFragment<*>) {
    with(owner) {
        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.dialog.collect {
                    it?.let {
                        if (activity != null) {
                            (activity as BaseActivity<*>).showDialog(it)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showToast.collect {
                    it?.let {
                        if (activity != null) {
                            (activity as BaseActivity<*>).showToast(it)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showLoading.collect {
                    if (activity != null) {
                        (activity as BaseActivity<*>).showLoading()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.hideLoading.collect {
                    if (activity != null) {
                        (activity as BaseActivity<*>).hideLoading()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.goBack.collect { it?.let { activity?.onBackPressed() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.finish.collect { it?.let { activity?.finish() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showFragment.collect { it?.let { activity?.showFragment(it) } }
            }
        }
    }
}
