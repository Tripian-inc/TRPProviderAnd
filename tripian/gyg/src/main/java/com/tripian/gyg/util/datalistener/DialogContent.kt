import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.base.BaseDialogFragment
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogContent(
    val titleRes: Int?,
    var titleText: String? = null,
    val contentRes: Int?,
    var contentText: String? = null,
    val positiveRes: Int?,
    val negativeRes: Int?,
    val cancellable: Boolean,
    val positive: (() -> Unit)? = null,
    val negative: (() -> Unit)? = null
) : Parcelable {

    constructor(builder: Builder) : this(
        builder.titleResId,
        builder.titleText,
        builder.contentResId,
        builder.contentText,
        builder.positiveRes,
        builder.negativeRes,
        builder.cancellable,
        builder.positive,
        builder.negative
    )

    class Builder() {
        var titleResId: Int? = null
        var titleText: String? = null
        var contentResId: Int? = null
        var contentText: String? = null
        var positiveRes: Int? = null
        var negativeRes: Int? = null
        var cancellable: Boolean = true
        var positive: (() -> Unit)? = null
        var negative: (() -> Unit)? = null
        fun build() = DialogContent(this)
    }
}

fun BaseViewModel.dialog(block: DialogContent.Builder.() -> Unit) {
    viewModelScope.launch {
        _dialog.send(DialogContent.Builder().apply(block).apply {
            if (positiveRes == null) {
                positiveRes = R.string.ok
            }
        }.build())
    }
}

inline fun BaseFragment<*>.dialog(block: DialogContent.Builder.() -> Unit) {
    if (activity is BaseActivity<*>) {
        (activity as BaseActivity<*>).showDialog(
            DialogContent.Builder().apply(block).apply {
                if (positiveRes == null) {
                    positiveRes = R.string.ok
                }
            }.build()
        )
    }
}

inline fun BaseActivity<*>.dialog(block: DialogContent.Builder.() -> Unit) {
    showDialog(
        DialogContent.Builder().apply(block).apply {
            if (positiveRes == null) {
                positiveRes = R.string.ok
            }
        }.build()
    )
}

inline fun BaseDialogFragment<*>.dialog(block: DialogContent.Builder.() -> Unit) {
    if (activity is BaseActivity<*>) {
        (activity as BaseActivity<*>).showDialog(
            DialogContent.Builder().apply(block).apply {
                if (positiveRes == null) {
                    positiveRes = R.string.ok
                }
            }.build()
        )
    }
}
