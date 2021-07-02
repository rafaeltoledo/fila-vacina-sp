package net.rafaeltoledo.filasp.ui.listing

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.databinding.FragmentListingBinding

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private val viewModel: ListingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentListingBinding.bind(view)

        viewModel.result.observe(viewLifecycleOwner) {
            binding.handleLoading(it.isLoading)
            binding.handleError(it.error)
            binding.handleData(it.places)
        }

        viewModel.fetchData()
    }

    private fun FragmentListingBinding.handleLoading(isLoading: Boolean) {

    }

    private fun FragmentListingBinding.handleError(@StringRes errorMessage: Int) {

    }

    private fun FragmentListingBinding.handleData(places: List<VaccinationPlace>) {
        placeList.adapter = ListingAdapter(places)
        placeList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}