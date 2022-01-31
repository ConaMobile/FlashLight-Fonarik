package com.conamobile.flashlightfonarik

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var flashSwitch = false
    private var flashSwitch2 = false
    lateinit var mAdView : AdView

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomsheetFragment = bottom_sheet()

        MobileAds.initialize(this) {}
        val adView = AdView(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        flash_button.setOnClickListener {
            flashSwitch = !flashSwitch

            if (flashSwitch) {

                try {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        val camera = Camera.open()
                        val params = camera.parameters
                        params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
                        camera.parameters = params
                        camera.startPreview()
                    } else {
                        val camManager =
                            this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                        camManager.setTorchMode(camManager.cameraIdList[0], true)
                    }
                    txt.setTextColor(Color.parseColor("#FF0031"))
                    txt.text = "ON"
                    img.setImageResource(R.drawable.off)
                    img.scaleType = ImageView.ScaleType.FIT_CENTER
                } catch (e: Exception) {
                    Toast.makeText(this, "Your Device is Not Supported", Toast.LENGTH_SHORT).show()
                }


            }
            else {

                try {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        val camera = Camera.open()
                        val params = camera.parameters
                        params.flashMode = Camera.Parameters.FLASH_MODE_OFF
                        camera.parameters = params
                        camera.startPreview()
                    } else {
                        val camManager =
                            this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                        camManager.setTorchMode(camManager.cameraIdList[0], false)
                    }
                    txt.setTextColor(Color.parseColor("#11FB00"))
                    txt.text = "OFF"
                    img.setImageResource(R.drawable.on)
                    img.scaleType = ImageView.ScaleType.FIT_CENTER
                } catch (e: Exception) {
                    Toast.makeText(this, "Your Device is Not Supported", Toast.LENGTH_SHORT).show()
                }

            }

        }

        selfie_btn.setOnClickListener {
            flashSwitch2 = !flashSwitch2

            if (flashSwitch2) {

                try {
                    val camManager =
                        this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                    camManager.setTorchMode(camManager.cameraIdList[1], true)

                    selfie_img.setImageResource(R.drawable.selfie_off)
                } catch (e: Exception) {
                    Toast.makeText(this, "Your Device is Not Supported", Toast.LENGTH_SHORT).show()
                }


            } else {

                try {
                    val camManager =
                        this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                    camManager.setTorchMode(camManager.cameraIdList[1], false)

                    selfie_img.setImageResource(R.drawable.on)
                } catch (e: Exception) {
                    Toast.makeText(this, "Your Device is Not Supported", Toast.LENGTH_SHORT).show()
                }

            }
        }

        about_btn.setOnClickListener {
            bottomsheetFragment.show(supportFragmentManager, "BottomSheet Dialog")
            if (bottomsheetFragment.showsDialog) {
//                about_btn.isClickable = false
            }
        }

    }
}