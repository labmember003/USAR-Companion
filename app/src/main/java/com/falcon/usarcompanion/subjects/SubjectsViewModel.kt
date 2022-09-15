package com.falcon.usarcompanion.subjects

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.falcon.usarcompanion.network.Api
import com.falcon.usarcompanion.network.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubjectsViewModel(
    application: Application
) : AndroidViewModel(application) {


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

        Toast.makeText(getApplication(), currentYearForViewModel.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(getApplication(), currentBranchForViewModel.toString(), Toast.LENGTH_LONG).show()
        var hashmap = hashMapOf("AIDS" to 0, "AIML" to 1, "IIOT" to 2, "AR" to 3)
        coroutineScope.launch {
            try {
                if (currentYearForViewModel == 1) {
                    val data = Api.yearDataApiService.getFirstYearData().await().subjects
                    _subjects.value = data
                    _isDataFetchSuccessful.value = true
                } else if (currentYearForViewModel == 2) {
                    val data = Api.yearDataApiService.getSecondYearData().await().branches[hashmap[currentBranchForViewModel]!!].subjects
                    //val data = Api.yearDataApiService.getSecondYearData().await().branches[1].subjects
                    _subjects.value = data
                    _isDataFetchSuccessful.value = true
                } else if (currentYearForViewModel == 3) {
                    val data = Api.yearDataApiService.getThirdYearData().await().branches[hashmap[currentBranchForViewModel]!!].subjects
                    _subjects.value = data
                    _isDataFetchSuccessful.value = true
                } else if (currentYearForViewModel == 4) {
                    val data = Api.yearDataApiService.getFourthYearData().await().branches[hashmap[currentBranchForViewModel]!!].subjects
                    _subjects.value = data
                    _isDataFetchSuccessful.value = true
                }
                /*
                val data = Api.yearDataApiService.getFirstYearData().await().subjects
                _subjects.value = data
                _isDataFetchSuccessful.value = true
                 */
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


//Toast.makeText(contexts, "mausi" + currentYear.toString(), Toast.LENGTH_LONG).show()