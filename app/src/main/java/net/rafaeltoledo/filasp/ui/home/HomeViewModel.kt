package net.rafaeltoledo.filasp.ui.home

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.FilaSpService
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.data.latLng
import net.rafaeltoledo.filasp.data.parseData
import net.rafaeltoledo.filasp.data.readAsset
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val assetManager: AssetManager,
    private val moshi: Moshi,
    private val service: FilaSpService,
) : ViewModel() {

    private val _result = MutableLiveData<HomeState>()
    val result: LiveData<HomeState>
        get() = _result

    fun fetchData() {
        viewModelScope.launch {
            val state = HomeState()
            _result.postValue(state.copy(isLoading = true))
            try {
                val data = service.requestData()
                _result.postValue(state.copy(places = prepareData(data), isLoading = false))
            } catch (e: IOException) {
                Log.e(this::class.java.simpleName, "Failed to fetch data", e)
                _result.postValue(state.copy(error = R.string.error_network, isLoading = false))
            }
        }
    }

    private fun prepareData(places: List<VaccinationPlace>): List<VaccinationPlace> {
        val emptyPlace = LatLng(.0, .0)

        val rawData = assetManager.readAsset()
        val data = moshi.parseData(rawData)
        return places.map { place ->
            place.copy(position = data.find { it.key == place.name }?.latLng() ?: emptyPlace)
        }.filter {
            it.position != emptyPlace
        }
    }
}
