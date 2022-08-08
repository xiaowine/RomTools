package cn.xiaowine.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import app.xiaowine.xtoast.XToast
import cn.xiaowine.romtools.R

object ActivityUtils {
    private val handler by lazy { Handler(Looper.getMainLooper()) }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun showToastOnLooper(context: Context, message: String) {
        try {

            handler.post { //                XToast.makeToast(context, message, toastIcon =context.resources.getDrawable(R.mipmap.ic_launcher_round)).show()
                XToast.makeText(context, message, toastIcon = context.resources.getDrawable(R.mipmap.ic_launcher, null)).show()
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    fun openUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}