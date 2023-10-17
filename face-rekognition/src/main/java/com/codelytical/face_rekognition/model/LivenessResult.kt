package com.codelytical.face_rekognition.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import com.codelytical.face_rekognition.analyzer.DetectionMode
import com.codelytical.face_rekognition.core.utils.BitmapUtils
import kotlinx.parcelize.Parcelize


@Parcelize
data class LivenessResult(
	val isSuccess: Boolean,
	val errorMessage: String?,
	val totalTimeMilis: Long? = null,
	val detectionResult: List<DetectionResult>? = null,
	var attempt: Int = 0): Parcelable{

    @Parcelize
    data class DetectionResult(
		val detectionMode: DetectionMode,
		val image: Uri?,
		val imagePath: String?,
		val timeMilis: Long?): Parcelable

    fun getBitmap(context: Context, detectionMode: DetectionMode, onError: (String) -> Unit): Bitmap? {
        return detectionResult?.find {
            it.detectionMode == detectionMode
        }?.let {
            it.image?.let { uri ->
                BitmapUtils.getBitmapFromContentUri(context.contentResolver, uri, onError = onError)
            }
        }
    }
}