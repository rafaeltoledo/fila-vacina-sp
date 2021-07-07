package net.rafaeltoledo.filasp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.data.mapIcon
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
            }
        }
    }

    private fun navigateToDetails(place: VaccinationPlace?) {
        place?.let {
            findNavController().navigate(
                R.id.to_place_details,
                bundleOf(
                    "place" to it
                )
            )
        }
    }

    private fun addPlaces(map: GoogleMap, places: List<VaccinationPlace>) {
        if (places.isEmpty()) return

        val bounds = LatLngBounds.Builder()
        places.forEach { place ->
            bounds.include(place.position)
            map.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.position)
                    .icon(place.status.mapIcon())
            )
        }

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 0))
    }
}
