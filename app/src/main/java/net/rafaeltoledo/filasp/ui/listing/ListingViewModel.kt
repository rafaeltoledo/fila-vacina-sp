package net.rafaeltoledo.filasp.ui.listing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.FilaSpService
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val service: FilaSpService)  : ViewModel() {

    private val _result = MutableLiveData<ListingState>()
    val result: LiveData<ListingState>
        get() = _result

    fun fetchData() {
        viewModelScope.launch {
            val state = ListingState()
            _result.postValue(state.copy(isLoading = true))
            try {
                val data = service.requestData()
                _result.postValue(state.copy(places = data, isLoading = false))
            } catch (e: IOException) {
                Log.e(this::class.java.simpleName, "Failed to fetch data", e)
                _result.postValue(state.copy(error = R.string.error_network, isLoading = false))
            }
        }
    }
}