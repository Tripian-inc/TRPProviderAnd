package com.tripian.gyg.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.base.BaseBottomDialogFragment
import com.tripian.gyg.databinding.FrCountrySelectBinding
import com.tripian.gyg.domain.model.CountryCode
import java.util.*

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRCountrySelect : BaseBottomDialogFragment<FrCountrySelectBinding>() {

    override val binding: (LayoutInflater) -> FrCountrySelectBinding = FrCountrySelectBinding::inflate

    companion object {
        fun newInstance(): FRCountrySelect {
            return FRCountrySelect()
        }
    }

    override fun setListeners() {
        vi.rvCountries.adapter = object : AdapterCountry(requireContext(), Locale.getAvailableLocales()) {
            override fun onSelectedCountry(country: CountryCode) {
                setFragmentResult("CountryCode", Bundle().apply { putSerializable("country", country) })
                dismiss()
            }
        }
    }

    override fun setReceivers() {
    }
}