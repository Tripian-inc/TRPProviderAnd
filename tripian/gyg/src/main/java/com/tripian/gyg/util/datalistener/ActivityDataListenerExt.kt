package com.tripian.gyg.util.datalistener

import androidx.lifecycle.lifecycleScope
import com.tripian.gyg.util.extensions.showFragment
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.base.BaseViewModel
import kotlinx.coroutines.flow.collect

inline fun <reified VM : BaseViewModel> Lazy<VM>.viewListener(owner: BaseActivity<*>) {
    with(owner) {
        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.dialog.collect { it?.let { showDialog(it) } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showToast.collect { it?.let { showToast(it) } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showLoading.collect { showLoading() }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.hideLoading.collect { hideLoading() }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.goBack.collect { it?.let { onBackPressed() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.finish.collect { it?.let { finish() } }
            }
        }

        lifecycleScope.launchWhenCreated {
            this@viewListener.value.let { vm ->
                vm.showFragment.collect { it?.let { showFragment(it) } }
            }
        }
    }
}
