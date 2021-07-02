package net.rafaeltoledo.filasp.ui.listing

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.data.parseData
import net.rafaeltoledo.filasp.data.readAsset
import net.rafaeltoledo.filasp.databinding.FragmentListingBinding
import javax.inject.Inject

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private val viewModel: ListingViewModel by viewModels()

    @Inject
    lateinit var moshi: Moshi

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
        val rawData = requireContext().readAsset()
        val data = moshi.parseData(rawData)

        placeList.adapter = ListingAdapter(places/*.filter { data.map { it.key }.contains(it.name).not() }*/)
        placeList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}