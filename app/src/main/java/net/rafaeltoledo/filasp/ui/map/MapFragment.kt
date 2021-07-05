package net.rafaeltoledo.filasp.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.data.latLng
import net.rafaeltoledo.filasp.data.parseData
import net.rafaeltoledo.filasp.data.readAsset
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var moshi: Moshi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            val rawData = requireContext().readAsset()
            val data = moshi.parseData(rawData)

            data.forEach { place ->
                it.addMarker(
                    MarkerOptions()
                        .position(place.latLng())
                        .title(place.key)
                )
            }

            it.moveCamera(
                CameraUpdateFactory
                    .newLatLngZoom(
                        LatLng(-23.5509304, -46.6414154), 14.5f
                    )
            )
        }
    }
}