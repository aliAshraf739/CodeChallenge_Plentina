package com.example.abbasisecurities.Util

import android.os.Looper
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.example.abbasisecurities.ui.LoginActivity
import java.lang.Exception

object Alert {
    @JvmStatic
    fun show(context: Context?, title: String?, msg: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                handler.removeCallbacks(this)
                val builder = AlertDialog.Builder(context)
                builder.setNeutralButton(
                    "OK"
                ) { dialog, which -> dialog.dismiss() }
                builder.setTitle(title)
                builder.setMessage(msg)
                try {
                    builder.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun showToLogin(context: Context, title: String?, msg: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                handler.removeCallbacks(this)
                val builder = AlertDialog.Builder(context)
                builder.setNeutralButton(
                    "OK"
                ) { dialog, which ->
                    (context as Activity).finish()
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
                builder.setTitle(title)
                builder.setMessage(msg)
                try {
                    builder.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun showErrorAlert(context: Context?) {
        show(context, "ERROR", "Error occurred please try again.")
    }
}