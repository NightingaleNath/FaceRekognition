package com.codelytical.rekognition_face

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelytical.face_rekognition.model.LivenessResult
import com.codelytical.rekognition_face.databinding.ItemLiveDetectionResultBinding

class LivenessResultAdapter(val livenessResult: LivenessResult): RecyclerView.Adapter<LivenessResultAdapter.DetectionResultViewHolder>(){
    inner class DetectionResultViewHolder(val binding: ItemLiveDetectionResultBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionResultViewHolder {
        return DetectionResultViewHolder(
            ItemLiveDetectionResultBinding
                .inflate(LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

	override fun onBindViewHolder(holder: DetectionResultViewHolder, position: Int) {
		livenessResult.detectionResult?.get(position)?.let { item ->
			if (item.detectionMode.name == "HOLD_STILL") {
				with(holder.binding) {
					tvTitle.text = item.detectionMode.name
					tvTime.text = item.timeMilis?.toString()
					item.image?.let {
						livenessResult.getBitmap(holder.itemView.context, item.detectionMode, onError = { message ->
							Log.e("SelfieWithKtpActivity.TAG", message)
						})
					}?.let {
						ivResult.setImageBitmap(it)
					}
				}
			}
		}
	}

	override fun getItemCount(): Int {
        return livenessResult.detectionResult?.size?: 0
    }
}