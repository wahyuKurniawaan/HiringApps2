package com.wahyu.hiringapps2.util

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wahyu.hiringapps2.R

abstract class BaseActivity: AppCompatActivity() {

    // inisialisasi code kotlin dengan layout xml
//    abstract fun findView()

    // untuk menyimpan perubahan object
    abstract fun initView()

    // menampung listener seperti onClick, dll
    abstract fun initListener()

    fun setShortToast(text: String, context: Context = this) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    fun setLongToast(text: String, context: Context = this) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    fun close() {
        val close = Intent(Intent.ACTION_MAIN)
        close.addCategory(Intent.CATEGORY_HOME)
        close.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(close)
    }

    fun closeWithDialogConfirm() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirm exit!")
            .setIcon(R.drawable.ic_exit)
            .setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                close()}
            .setNegativeButton("No") {  dialog, id -> }
        dialog.show()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    class KeyExtraIntent {

        companion object {

            const val EXTRA_FIRST_NAME = "EXTRA_FIRST_NAME"
            const val EXTRA_LAST_NAME = "EXTRA_LAST_NAME"
            const val EXTRA_EMAIL = "EXTRA_EMAIL"
            const val EXTRA_PASSWORD = "EXTRA_PASSWORD"
        }
    }
}