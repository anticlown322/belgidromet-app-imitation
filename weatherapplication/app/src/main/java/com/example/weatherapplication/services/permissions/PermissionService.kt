package com.example.weatherapplication.services.permissions

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionService private constructor(
    private val fragment: Fragment
) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    companion object {
        fun with(fragment: Fragment): PermissionService {
            return PermissionService(fragment)
        }
    }

    init {
        registerPermissionLauncher()
    }

    private fun registerPermissionLauncher() {
        permissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                (fragment as? PermissionCallback)?.onPermissionGranted()
            } else {
                (fragment as? PermissionCallback)?.onPermissionDenied()
            }
        }
    }

    fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String) {
        permissionLauncher.launch(permission)
    }
}