package com.falcon.usarcompanion.subjects

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.falcon.usarcompanion.model.Branch
import com.falcon.usarcompanion.model.Year
import com.falcon.usarcompanion.network.Api
import com.falcon.usarcompanion.network.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

data class SubjectsViewModelData(
    val currentBranch: Branch,
    val currentYear: Year
)

class SubjectsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _isDataFetchSuccessful = MutableLiveData<Boolean>(true)
    val isDataFetchSuccessful: LiveData<Boolean>
        get() = _isDataFetchSuccessful

    private val _subjects = MutableLiveData<List<Subject>?>(null)
    val subjects: LiveData<List<Subject>?>
        get() = _subjects

    val hideLoadAnimation = MutableLiveData<Boolean>(false)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private lateinit var currentData: SubjectsViewModelData

    private fun fetchData() {

        //Toast.makeText(getApplication(), currentData.currentYear.displayName, Toast.LENGTH_LONG).show()
        //Toast.makeText(getApplication(), currentData.currentBranch.displayName, Toast.LENGTH_LONG).show()
        coroutineScope.launch {
            try {
                val data = when(currentData.currentYear) {
                    Year.FIRST -> Api.yearDataApiService.getFirstYearData().await().subjects
                    Year.SECOND -> getBranch(Api.yearDataApiService.getSecondYearData().await().branches, currentData.currentBranch.displayName).subjects
                    Year.THIRD -> getBranch(Api.yearDataApiService.getThirdYearData().await().branches, currentData.currentBranch.displayName).subjects
                    Year.FOURTH -> getBranch(Api.yearDataApiService.getFourthYearData().await().branches, currentData.currentBranch.displayName).subjects
                }
                hideLoadAnimation.value = true
                _subjects.value = data
                _isDataFetchSuccessful.value = true
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                _subjects.value = null
                _isDataFetchSuccessful.value = false
            }
        }
    }


    private fun getBranch(branches: List<com.falcon.usarcompanion.network.Branch>, branchName: String): com.falcon.usarcompanion.network.Branch {
        return branches.find { it.branchName == branchName }!!
    }


    fun setCurrentData(data: SubjectsViewModelData) {
        this.currentData = data
        fetchData()
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}


//Toast.makeText(contexts, "mausi" + currentYear.toString(), Toast.LENGTH_LONG).show()