package com.world.horizon.shared.extensions

import android.app.Activity
import android.app.AlertDialog

fun Activity.showDialogError(title: String, message: String) {
    if (!isFinishing) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, which ->
                // Do something.
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
