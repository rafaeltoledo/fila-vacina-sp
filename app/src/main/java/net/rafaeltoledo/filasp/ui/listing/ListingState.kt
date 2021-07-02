package net.rafaeltoledo.filasp.ui.listing

import androidx.annotation.StringRes
import net.rafaeltoledo.filasp.api.VaccinationPlace

data class ListingState(
    val places: List<VaccinationPlace> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes
    val error: Int = 0,
)