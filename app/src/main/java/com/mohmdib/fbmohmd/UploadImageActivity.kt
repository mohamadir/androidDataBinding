package com.mohmdib.fbmohmd

//import com.example.logutil.Sleka

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class UploadImageActivity : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "UploadImageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image2)
        findViewById<Button>(R.id.uploadFileBtn).setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        var adRequest = AdRequest.Builder().build()
        var adRequest2 = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest2, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@UploadImageActivity)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "WRONG")
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(this@UploadImageActivity)
            }
        })


        val adView = findViewById<AdView>(R.id.adView)


        adView.loadAd(adRequest)
        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                adView.adSize = AdSize.BANNER

                // Code to be executed when an ad finishes loading.
                Log.i("","")

            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.i("","")
            }

            override fun onAdOpened() {
                Log.i("","")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                Log.i("","")

                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                Log.i("","")

                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

//       var sleka =  Sleka.Builder().cardName("muhammed")
//            .cardNumber("123-xxxx")
//            .callBack {
//                Toast.makeText(this@UploadImageActivity, it.toString(), Toast.LENGTH_SHORT)
//            }.start()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            var imageView = findViewById<ImageView>(R.id.imagePreview)
            imageUri = data?.data
            uploadImageToFirebase(imageUri!!)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()

                        Log.i("MOHMD-TET", imageUrl)
                    }
                }

                ?.addOnFailureListener { e ->
                    Log.i("MOHMD-TET", e.message.toString())
                }
        }
    }
}