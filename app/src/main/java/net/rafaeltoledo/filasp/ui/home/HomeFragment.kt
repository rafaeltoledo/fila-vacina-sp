package net.rafaeltoledo.filasp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.data.latLng
import net.rafaeltoledo.filasp.data.mapIcon
import net.rafaeltoledo.filasp.data.parseData
import net.rafaeltoledo.filasp.data.readAsset
import net.rafaeltoledo.filasp.databinding.FragmentHomeBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@AndroidEntryPoint
@RuntimePermissions
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var moshi: Moshi

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeBinding.bind(view)

        viewModel.result.observe(viewLifecycleOwner) {
            binding.handleLoading(it.isLoading)
            binding.handleError(it.error)
            binding.handleData(it.places)
        }

        viewModel.fetchData()
    }

    private fun FragmentHomeBinding.handleLoading(isLoading: Boolean) {
        loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun FragmentHomeBinding.handleError(@StringRes errorMessage: Int) {
        if (errorMessage == 0) return

        Snackbar.make(root, errorMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.button_retry) {
                viewModel.fetchData()
            }.show()
    }

    private fun FragmentHomeBinding.handleData(places: List<VaccinationPlace>) {
        setupMapWithPermissionCheck(places = places.toMutableList())
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun setupMap(places: List<VaccinationPlace>) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
            map.uiSettings.isZoomControlsEnabled = true

            addPlaces(map, places)
            map.setOnInfoWindowClickListener { marker ->
                navigateToDetails(places.find { it.name == marker.title })
                true
            }
        }
    }

    private fun navigateToDetails(place: VaccinationPlace?) {
        place?.let {
            Snackbar.make(requireView(), it.updatedAt, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun addPlaces(map: GoogleMap, places: List<VaccinationPlace>) {
        val rawData = requireContext().readAsset()
        val data = moshi.parseData(rawData)

        places.forEach { place ->
            map.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(data.find { it.key == place.name }!!.latLng())
                    .icon(place.status.mapIcon())
            )
        }
    }
}