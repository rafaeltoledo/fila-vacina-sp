package net.rafaeltoledo.filasp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace

class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val place: VaccinationPlace = requireArguments().getParcelable("place")!!

        Snackbar.make(view, place.name, Snackbar.LENGTH_INDEFINITE).show()
    }
}
