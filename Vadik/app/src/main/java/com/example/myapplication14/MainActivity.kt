package com.example.myapplication14

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import kotlin.math.*

class roflan(){

    companion object{lateinit var bitmap: Bitmap}

}

class MainActivity : AppCompatActivity() {

    private val IMAGE_PICK_CODE = 1;
    private val CAMERA = 2;
    private val CAMERA_PERMISSION_CODE = 100;
    private val STORAGE_PERMISSION_CODE = 101;
    private lateinit var gbmap: Bitmap
    private var f = false
    private var t = false
    private var l = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bitmap = intent.getParcelableExtra<Bitmap>("bitmap")

        //BUTTON CLICK
        main_card.setOnClickListener {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
            //permission already granted
            pickImageFromGallery(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        camera_main.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            //permission already granted
            takePhoto(Manifest.permission.CAMERA);
        }

        rotate_img_left.setOnClickListener {
            if(l == true && edit_text.text.isNotEmpty()) {
                rottateImage(image_view, 360 - edit_text.text.toString().toInt());
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun nextSlide(view: View)
    {
        if (l == true) {
            val intent = Intent(this, SecondActivity::class.java)
            val bmap: Bitmap = (image_view.getDrawable() as BitmapDrawable).bitmap
            val aspectRatio: Float = bmap.height.toFloat() / bmap.width
            val mImageWidth = 700
            val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
            val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
            roflan.bitmap = mBitmap
            startActivity(intent)
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {

        // Проверка, если разрешение не предоставлено
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this@MainActivity, arrayOf(permission),
                    requestCode
                )
        } else {
            Toast
                .makeText(
                    this@MainActivity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Проверка, предоставил ли пользователь разрешение или нет.
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                // Отображение тостового сообщения
                Toast.makeText(
                    this@MainActivity,
                    "Camera Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Camera Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun takePhoto(permission: String){
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA)
        }
    }


    private fun pickImageFromGallery(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val gallaryintent = Intent(Intent.ACTION_PICK)
            gallaryintent.type = "image/*"
            startActivityForResult(gallaryintent, IMAGE_PICK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image_view.setImageURI(data?.data)
            gbmap = (image_view.getDrawable() as BitmapDrawable).bitmap
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA){
            val thumbnail = data!!.extras!!["data"] as Bitmap
            image_view.setImageBitmap(thumbnail);
            gbmap = (image_view.getDrawable() as BitmapDrawable).bitmap
        }
        l = true
    }






}
