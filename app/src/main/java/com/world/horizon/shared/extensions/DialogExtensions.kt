package com.world.horizon.shared.extensions

import android.app.Activity
import android.app.AlertDialog

fun Activity.showDialogError(title: String, message: String){
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder
        .setMessage(title)
        .setTitle(message)
        .setPositiveButton("Cancel") { dialog, which ->
            // Do something.
        }

    val dialog: AlertDialog = builder.create()
    dialog.show()
}