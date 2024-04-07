package com.world.horizon.shared.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.widget.Toast

inline fun<reified T: Parcelable> Intent.getParcelableIntent(key: String): T? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    } else {
        getParcelableExtra(key, T::class.java)
    }
}

fun Activity.toastShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}