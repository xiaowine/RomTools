package cn.xiaowine.ui.dialog


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cn.xiaowine.romtools.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MdProgressDialog(context: Context) : MaterialAlertDialogBuilder(context) {
    private var layout: View

    init {
        val inflater: LayoutInflater = this.create().layoutInflater
        layout = inflater.inflate(R.layout.dialog_progress, null)
        setView(layout)
        setCancelable(false)
    }

    fun setMessages(message: String): MdProgressDialog {
        layout.findViewById<TextView>(R.id.message).text = message
        return this
    }

    fun setTitle(message: String): MdProgressDialog {
        super.setTitle(message)
        return this
    }

}