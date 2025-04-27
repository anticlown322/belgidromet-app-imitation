package com.example.weatherapplication.dialogs

interface DialogListener {
    fun onPositiveButtonClicked(data: Any? = null)
    fun onNegativeButtonClicked() = Unit
}