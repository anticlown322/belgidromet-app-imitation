package com.example.weatherapplication.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

object UpdateResultDialog {
    fun show(
        context: Context,
        isUpdated: Boolean,
        lastUpdateTime: String?,
        newUpdateTime: String?,
        listener: DialogListener
    ) {
        val title = if (isUpdated) "Forecast is updated" else "Forecast is up to date"
        val message = buildString {
            if (isUpdated) {
                append("New forecast data was received\n")
                append("Previous update time: $lastUpdateTime\n")
                append("Last update time: $newUpdateTime")
            } else {
                append("Last update time: $lastUpdateTime")
            }
        }

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                listener.onPositiveButtonClicked(isUpdated)
            }
            .setCancelable(false)
            .show()
    }
}