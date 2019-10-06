package com.mif50.extensions.ktx

import android.app.Activity
import android.app.Dialog
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mif50.extensions.R
import com.mif50.extensions.helpers.ui.LayoutRes

fun DialogFragment.getLayoutRes(): LayoutRes {
    val annotation = this::class.java.annotations.find { it is LayoutRes } as? LayoutRes
    if (annotation != null) {
        return annotation
    } else {
        throw KotlinNullPointerException("Please add the LayoutRes annotation at the to of the class")
    }
}


fun Activity.showAlertDialog(title: String,
                             message: String,
                             actionOK: String,
                             actionCancel: String,
                             listener: OnDialogClickListener) {

    val view = this.layoutInflater.inflate(R.layout.dialog_alert, null)

    val dialog = AlertDialog.Builder(this).setView(view)

    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDesc = view.findViewById<TextView>(R.id.tvDescription)
    val btnCancel = view.findViewById<Button>(R.id.btnCancel)
    val btnOk = view.findViewById<Button>(R.id.btnOk)

    tvTitle.text = title
    tvDesc.text = message
    btnCancel.text = actionCancel
    btnOk.text = actionOK
    val dlg = dialog.create()
    dlg.window?.setWindowAnimations(R.style.DialogAnimationPopup)

    dlg.setCancelable(false)
    dlg.setCanceledOnTouchOutside(false)

    btnCancel.setOnClickListener { listener.onButtonDialogClicked(dlg, TYPE_CANCEL) }
    btnOk.setOnClickListener { listener.onButtonDialogClicked(dlg, TYPE_OK) }

    dlg.show()
}



interface OnDialogClickListener {
    fun onButtonDialogClicked(dialog: Dialog, @TypeAction type: Int)
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(TYPE_OK, TYPE_CANCEL, TYPE_NEUTRAL)
annotation class TypeAction

// Declare the constants
const val TYPE_OK = 0
const val TYPE_CANCEL = 1
const val TYPE_NEUTRAL = 2