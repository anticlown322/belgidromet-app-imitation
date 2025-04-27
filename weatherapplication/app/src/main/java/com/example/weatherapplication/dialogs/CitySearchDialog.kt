package com.example.weatherapplication.dialogs

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

object CitySearchDialog {

    fun show(
        context: Context,
        title: String = "City name:",
        hint: String = "Enter city name",
        positiveText: String = "OK",
        negativeText: String = "Cancel",
        listener: DialogListener
    ) {
        val input = EditText(context).apply {
            this.hint = hint
        }

        AlertDialog.Builder(context).apply {
            setTitle(title)
            setView(input)
            setPositiveButton(positiveText) { _, _ ->
                listener.onPositiveButtonClicked(input.text.toString())
            }
            setNegativeButton(negativeText) { _, _ ->
                listener.onNegativeButtonClicked()
            }
            create()
        }.show()
    }
}