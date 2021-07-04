package net.rafaeltoledo.filasp.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.maps.Style
import dagger.hilt.android.AndroidEntryPoint
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.databinding.FragmentMapBinding

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMapBinding.bind(view)

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync {
            it.setStyle(Style.MAPBOX_STREETS) {

            }
        }
    }
}