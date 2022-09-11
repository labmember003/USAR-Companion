package com.falcon.usarcompanion.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Notes"
    }
    val text: LiveData<String> = _text
}