package com.tripian.gyg.util

interface OnBackPressListener {

    /**
     * Fragment'larda backpress'e register olmak icin isBackEnable true olarak override edilmelidir
     */
    fun isBackEnable(): Boolean

    /**
     * Eger isBackEnable ise bu method tetiklenir. onBackPressed false override edilirse event blocklanmis olur.
     * true olmasi durumunda backpress icin super.onBackPressed() cagrilmis olur
     */
    fun onBackPressed(): Boolean
}
