package com.codelytical.rekognition_face

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelytical.face_rekognition.MNCIdentifier
import com.codelytical.rekognition_face.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding

	private var latestTmpUri: Uri? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		MNCIdentifier.setLowMemoryThreshold(0)

		with(binding) {

			btnLivenessDetection.setOnClickListener {
				resultLauncherLiveness.launch(MNCIdentifier.getLivenessIntent(this@MainActivity))
			}

		}
	}

	private val resultLauncherLiveness =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode == Activity.RESULT_OK) {
				val data = result.data
				val livenessResult = MNCIdentifier.getLivenessResult(data)
				livenessResult?.let {
					if (it.isSuccess) {
						binding.tvAttempt.apply {
							visibility = View.VISIBLE
							text = "Attempt: ${it.attempt}"
						}
						val livenessResultAdapter = LivenessResultAdapter(it)
						binding.rvLiveness.apply {
							layoutManager = LinearLayoutManager(
								this@MainActivity,
								LinearLayoutManager.HORIZONTAL,
								false
							)
							adapter = livenessResultAdapter
						}
					}
				}
			}
		}
}