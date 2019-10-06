package com.mif50.extensions.ktx

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val PICK_IMAGE_REQUEST: Int = 100
const val PICK_CAMERA_IMAGE_REQUEST: Int = 101
const val REQUEST_VIDEO_CAPTURE: Int = 102
const val PICK_CAMERA_REQUEST: Int = 104
const val FILE_PICK_REQUEST: Int = 105
const val MEDIA_PICK_REQUEST: Int = 106
var cameraFilePath: String? = null

fun Activity.pickImageFromGallery(askForPermission: Boolean) {
    if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    } else {
        if (askForPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
        }
    }
}

fun Fragment.pickImageFromGallery(askForPermission: Boolean) {
    if (this.activity == null) return
    if (activity!!.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    } else {
        if (askForPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    //This is the directory in which the file will be created. This is the default location of Camera photos

    val storageDir = this.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir      /* directory */
    )
    // Save a file: path for using again
    cameraFilePath = image.absolutePath
    return image
}

@SuppressLint("SimpleDateFormat", "InlinedApi")
fun Context.createImageFileAndrodQ(): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"

    val resolver = this.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        put(MediaStore.MediaColumns.RELATIVE_PATH,  Environment.DIRECTORY_PICTURES)

    }
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    return uri

}

fun Activity.pickPhotoFromCamera(askForPermission: Boolean) {
    if (hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this, "$packageName.provider", createImageFile())
        } else {
            Uri.fromFile(createImageFile())
        }

        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(chooserIntent, PICK_CAMERA_IMAGE_REQUEST)

    } else {
        if (askForPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PICK_CAMERA_IMAGE_REQUEST
            )
        }
    }
}

fun Fragment.pickPhotoFromCamera(askForPermission: Boolean) {
    if (this.activity == null) return

    if (activity!!.hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
             FileProvider.getUriForFile(context!!, "${activity?.packageName}.provider", this.context!!.createImageFile())
        else Uri.fromFile(this.context!!.createImageFile())

        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(chooserIntent, PICK_CAMERA_IMAGE_REQUEST)

    } else {
        if (askForPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_CAMERA_IMAGE_REQUEST)
        }
    }
}

fun Activity.recordVideo(askForPermission: Boolean) {
    if (hasPermission(Manifest.permission.CAMERA)) {
        val intent = Intent("android.media.action.VIDEO_CAPTURE")
        intent.putExtra("android.intent.extra.durationLimit", 120)
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
    } else {
        if (askForPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_VIDEO_CAPTURE)
        }
    }
}

fun Fragment.recordVideo(withAskPermission: Boolean) {
    if (this.activity == null) return
    if (activity!!.hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        val intent = Intent("android.media.action.VIDEO_CAPTURE")
        intent.putExtra("android.intent.extra.durationLimit", 120)
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
    } else {
        if (withAskPermission && isEqualOrMoreThanMarshmallow()) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_VIDEO_CAPTURE)
        }
    }
}

fun Context.playMessageSound() {
    val alarmSound: Uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/raw/message")
    val r = RingtoneManager.getRingtone(applicationContext, alarmSound)
    r.play()
}

//fun AppCompatActivity.openCamera(withAskPermission: Boolean) {
//    if (hasPermission(Manifest.permission.CAMERA)) {
//        val listString = arrayListOf(getString(R.string.text_pick_image), getString(R.string.text_record_video))
//        SelectFileDialogFragment.newInstance(listString).show(supportFragmentManager, SelectFileDialogFragment.TAG)
//    } else {
//        if (withAskPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(
//                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                PICK_CAMERA_REQUEST)
//        }
//    }
//}


//fun Activity.pickMedia(withAskPermission: Boolean) {
//    if (hasPermission(FilePickerConst.PERMISSIONS_FILE_PICKER)) {
//        FilePickerBuilder.instance.setActivityTheme(R.style.AppTheme)
//            .enableVideoPicker(true)
//            .enableImagePicker(true)
//            .setMaxCount(1)
//            .pickPhoto(this)
//    } else {
//        if (withAskPermission && isEqualOrMoreThanMarshmallow()) {
//            requestPermissions(arrayOf(FilePickerConst.PERMISSIONS_FILE_PICKER, Manifest.permission.CAMERA),
//                MEDIA_PICK_REQUEST)
//        }
//    }
//}




