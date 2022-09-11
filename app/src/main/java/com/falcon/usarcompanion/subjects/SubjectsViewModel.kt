package com.falcon.usarcompanion.subjects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falcon.usarcompanion.network.Api
import com.falcon.usarcompanion.network.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubjectsViewModel : ViewModel() {
    private val _isDataFetchSuccessful = MutableLiveData<Boolean>(true)
    val isDataFetchSuccessful: LiveData<Boolean>
        get() = _isDataFetchSuccessful

    private val _subjects = MutableLiveData<List<Subject>?>(null)
    val subjects: LiveData<List<Subject>?>
        get() = _subjects

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getData()
    }

    private fun getData() {
        coroutineScope.launch {
            try {
                val data = Api.yearDataApiService.getFirstYearData().await().subjects
                _subjects.value = data
                _isDataFetchSuccessful.value = true
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                _subjects.value = null
                _isDataFetchSuccessful.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}