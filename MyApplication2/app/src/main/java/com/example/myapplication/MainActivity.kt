package com.blogspot.atifsoftwares.imagepick_kotlin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val IMAGE_PICK_CODE = 1;
    private val CAMERA = 2;
    private val CAMERA_PERMISSION_CODE = 100;
    private val STORAGE_PERMISSION_CODE = 101;
    private val imageview: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //BUTTON CLICK
        img_pick_btn.setOnClickListener {
            checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
                    //permission already granted
                    pickImageFromGallery(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
        img_pick_btn2.setOnClickListener {
            checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            //permission already granted
            takePhoto(android.Manifest.permission.CAMERA);
        }

        rotate_img_left.setOnClickListener {

            roateImage(image_view);
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

    private fun roateImage(view: ImageView) {

        val bmap: Bitmap = (view.getDrawable() as BitmapDrawable).bitmap
        val width: Int = bmap.width
        val height: Int = bmap.height
        val mybmap: Bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val sin: Int = 1
        val cos: Int = 0
        val x0 = 0.5 * (width - 1)
        val y0 = 0.5 * (height - 1)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val a = x - x0
                val b = y - y0
                val xx = (+a * cos - b * sin + x0).toInt()
                val yy = (+a * sin + b * cos + y0).toInt()
                if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                    mybmap.setPixel(x, y, bmap.getPixel(xx, yy))
                }
            }
        }
        image_view.setImageBitmap(mybmap);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image_view.setImageURI(data?.data)
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA){
            val thumbnail = data!!.extras!!["data"] as Bitmap
            image_view.setImageBitmap(thumbnail);
        }
    }


}
