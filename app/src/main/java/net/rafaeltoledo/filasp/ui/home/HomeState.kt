package net.rafaeltoledo.filasp.ui.home

import androidx.annotation.StringRes
import net.rafaeltoledo.filasp.api.VaccinationPlace

data class HomeState(
    val places: List<VaccinationPlace> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes
    val error: Int = 0,
)