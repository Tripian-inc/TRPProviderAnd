package com.tripian.gyg.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.databinding.FrTourDateBinding
import com.tripian.gyg.domain.model.BookCounter
import com.tripian.gyg.domain.model.BookOption
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.extensions.getDay
import com.tripian.gyg.util.extensions.getMonth
import com.tripian.gyg.util.extensions.getYear
import com.tripian.gyg.util.extensions.observe
import com.tripian.gyg.widget.datepicker.SpinnerDatePickerDialogBuilder
import java.util.*


/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRTourDate : BaseFragment<FrTourDateBinding>() {

    override val binding: (LayoutInflater) -> FrTourDateBinding = FrTourDateBinding::inflate

    private val viewModel: FRTourDateVM by injectVM()

    private val todayTime = Calendar.getInstance().timeInMillis

    companion object {
        fun newInstance(): FRTourDate {
            return FRTourDate()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BookingPageData.clearBooking()

        viewModel.init()
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as ACBook).setPageTitle(R.string.page_title_date)
    }

    override fun setListeners() {
        vi.etDate.setOnClickListener {
            SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback { _, year, monthOfYear, dayOfMonth ->
                    viewModel.onDateSelected(year, monthOfYear, dayOfMonth)
                }
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .customTitle(getString(R.string.check_availability))
                .defaultDate(viewModel.getYear(), viewModel.getMonth(), viewModel.getDay())
                .maxDate(todayTime.getYear().toInt() + 30, 0, 1)
                .minDate(todayTime.getYear().toInt(), todayTime.getMonth().toInt() - 1, todayTime.getDay().toInt())
                .build()
                .show();
        }
    }

    override fun setReceivers() {
        observe(viewModel.onSetDateListener) {
            vi.etDate.setText(it)
        }

        observe(viewModel.onSetCounterListener) {
            vi.rvCounter.adapter = object : AdapterCounter(requireContext(), it) {
                override fun onChangedCounter(counter: BookCounter) {
                    viewModel.onChangedCounter()
                }
            }
        }

        observe(viewModel.onSetOptionsListener) {
            if (vi.rvOptions.adapter == null || it!!.second) {
                vi.rvOptions.adapter = object : AdapterOption(requireContext(), it!!.first) {
                    override fun onSelectedOption(option: BookOption) {
                        viewModel.onSelectedOption(option)
                    }
                }
            } else {
                vi.rvOptions.adapter?.notifyDataSetChanged()
            }
        }
    }
}