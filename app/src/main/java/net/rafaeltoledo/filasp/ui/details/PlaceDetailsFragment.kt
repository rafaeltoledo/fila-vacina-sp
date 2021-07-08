package net.rafaeltoledo.filasp.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.databinding.FragmentPlaceDetailsBinding
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val place: VaccinationPlace = requireArguments().getParcelable("place")!!

        val binding = FragmentPlaceDetailsBinding.bind(view)

        binding.toolbar.title = place.name

        binding.data.text = place.updatedAt.sinceString(requireContext())
    }
}

private fun LocalDateTime.sinceString(context: Context): CharSequence {
    val duration = Duration.between(this, LocalDateTime.now())

    if (duration.toMinutes() < 60) return "${if (duration.toMinutes() == 0L) 1 else duration.toMinutes()}min"

    if (duration.toHours() < 24) return "${duration.toHours()}h"

    if (duration.toDays() < 30) return "${duration.toDays()}d"

    if (duration.toDays() < 365) {
        return format(DateTimeFormatter.ofPattern(context.getString(R.string.format_day_month)))
    }

    return format(DateTimeFormatter.ofPattern(context.getString(R.string.format_day_month_year)))
}
