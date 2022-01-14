package com.builbee.app.util

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.builbee.app.R

class Loading {

    companion object {
        //        private lateinit var spotsDialog: AlertDialog
        private lateinit var spotsDialog: ProgressDialog

        fun show(context: Context?) {
            try {
                spotsDialog = ProgressDialog(context, R.style.MyAlertDialogStyle)
                spotsDialog.setMessage("Please Wait...")
                spotsDialog.setCancelable(false)
                spotsDialog.show()
            } catch (e: Exception) {
                Log.d("Loading", "show")
            }
        }

        fun cancel() {
            try {
                if (spotsDialog.isShowing) {
                    spotsDialog.dismiss()
                    spotsDialog.cancel()
                }
            } catch (e: Exception) {
                Log.d("Loading", "cancel")
            }
        }
    }

}