package com.example.weatherapplication.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

object LocationSettingsDialog {

    fun show(
        context: Context,
        title: String = "Enable location?",
        message: String = "Location disabled! Do you want to enable location?",
        positiveText: String = "OK",
        negativeText: String = "Cancel",
        listener: DialogListener
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveText) { _, _ ->
                listener.onPositiveButtonClicked()
            }
            setNegativeButton(negativeText) { _, _ ->
                listener.onNegativeButtonClicked()
            }
            create()
        }.show()
    }
}