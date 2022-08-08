package cn.xiaowine.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cn.xiaowine.romtools.R
import cn.xiaowine.ui.dialog.MdProgressDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jaredrummler.ktsh.Shell
import java.util.*

object Utils {
    const val TAG = "RomTools"

    fun dp2px(context: Context, dpValue: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics).toInt()

    fun backUp(activity: Activity, type: String) {
        val dialog = MdProgressDialog(activity).setTitle(activity.getString(R.string.backup_ing).format(type.firstUp())).setMessage(activity.getString(R.string.no_exit)).show()
        Thread {
            val command = "mkdir /storage/emulated/0/Documents/ && dd if=/dev/block/by-name/$type of=/storage/emulated/0/Documents/$type.img"
            Log.i(TAG, command)
            val result = Shell("sh").run(command)
            val response = result.output()
            Log.i(TAG, response)
            if (result.exitCode == 0) {
                val responseList = response.split(", ")
                ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.backup_done).format(responseList[responseList.lastIndex - 1], responseList[responseList.lastIndex], type))
//                ActivityUtils.showToastOnLooper(activity, "${type.firstUp()}已备份到\n/storage/emulated/0/Documents/$type.img")
            } else {
                ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.backup_error).format(response))
            }
            dialog.dismiss()
        }.start()
    }

    private fun flash(activity: Activity, type: String, filePath: String) {
        MaterialAlertDialogBuilder(activity).setTitle(activity.getString(R.string.flash_ask)).setMessage(activity.getString(R.string.flash_tips).format(type, filePath)).setPositiveButton(activity.getString(R.string.flash_ok)) { _, _ ->
            val dialog = MdProgressDialog(activity).setTitle(activity.getString(R.string.flash_ing).format(type.firstUp())).setMessage(activity.getString(R.string.no_exit)).show()
            Thread {
                val command = "dd if=$filePath of=/dev/block/by-name/$type"
                Log.i(TAG, command)
                val result = Shell("sh").run(command)
                val response = result.output()
                Log.i(TAG, response)
                if (result.exitCode == 0) {
                    val responseList = response.split(", ")
                    ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.flash_done).format(responseList[responseList.lastIndex - 1], responseList[responseList.lastIndex]))
                    ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.restart_effect))
                } else {
                    ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.flash_error).format(response))
                }
                dialog.dismiss()
            }.start()
        }.setNegativeButton(activity.getString(R.string.flash_no)) { _, _ -> }.show()
    }

    fun registerResul(frameLayout: Fragment, activity: Activity, type: String): ActivityResultLauncher<Array<String>> {
        return frameLayout.registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it == null) return@registerForActivityResult
            val uriPath = it.path.toString()
            val pathList = uriPath.split(".")
            val suffix = pathList[pathList.lastIndex]
            if (suffix == "img") {
                val filePath = FileUtils(activity).getFilePathByUri(it)
                flash(activity, type, filePath)
            } else {
                ActivityUtils.showToastOnLooper(activity, activity.getString(R.string.unknown))
            }

        }
    }

    private fun String.firstUp(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}