package com.example.myapplication14

import android.Manifest
import android.R.attr.thumbnail
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*


class roflan(){

    companion object{lateinit var bitmap: Bitmap}

}

class MainActivity : AppCompatActivity() {
    private val PICTURE_RESULT: Int = 1001
    var imageUri: Uri? =null
    private val IMAGE_PICK_CODE = 1;
    private val CAMERA = 2;
    private val CAMERA_PERMISSION_CODE = 100;
    private val STORAGE_PERMISSION_CODE = 101;
    private lateinit var gbmap: Bitmap
    var flag=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bitmap = intent.getParcelableExtra<Bitmap>("bitmap")

        //BUTTON CLICK
        img_pick_btn.setOnClickListener {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
            //permission already granted
            flag=true
            pickImageFromGallery(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        img_pick_btn2.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            //permission already granted
            flag=true
            takePhoto(Manifest.permission.CAMERA);
        }

        rotate_img_left.setOnClickListener {
            val msg: String = edit_text.text.toString()
            if((msg.trim().isNotEmpty())&&(flag==true))
            rottateImage(image_view,360-edit_text.text.toString().toInt())
            else if (flag==false)
            {
                Toast.makeText(
                    this,
                    "Image not uploaded",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                Toast.makeText(
                    this,
                    "Rotation angle not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }



     fun nextSlide(view: View)
    {
            if (flag) {
                val intent = Intent(this, SecondActivity::class.java)
                val bmap: Bitmap = (image_view.getDrawable() as BitmapDrawable).bitmap
                val aspectRatio: Float = bmap.height.toFloat() / bmap.width
                val displayMetrics: DisplayMetrics = resources.displayMetrics
                val mImageWidth = displayMetrics.widthPixels
                val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
                val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
                roflan.bitmap = mBitmap
                startActivity(intent)
            }
        else
            {
                Toast.makeText(
                    this,
                    "Image not uploaded",
                    Toast.LENGTH_SHORT
                ).show()
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
        var values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
        imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, PICTURE_RESULT)
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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICTURE_RESULT -> if (requestCode == PICTURE_RESULT) if (resultCode == Activity.RESULT_OK) {
                try {
                    var thumbnail = MediaStore.Images.Media.getBitmap(
                        contentResolver, imageUri
                    )
                    image_view.setImageBitmap(thumbnail)
                   var imageurl = getRealPathFromURI(imageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(contentUri, proj, null, null, null)
        val column_index: Int = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


    fun rottateImage(view: ImageView, degrees: Int) {
            val bmap: Bitmap = (view.getDrawable() as BitmapDrawable).bitmap
            val aspectRatio: Float = bmap.height.toFloat() / bmap.width
            val displayMetrics: DisplayMetrics = resources.displayMetrics
            val mImageWidth = displayMetrics.widthPixels
            val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
            val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
            val rad = (degrees * 3.1415926535f) / 180f
            val cosf = cos(rad)
            val sinf = sin(rad)

            val nWidth: Int = mBitmap.width
            val nHeight: Int = mBitmap.height

            val x1 = (-nHeight * sinf).toInt()
            val y1 = (nHeight * cosf).toInt()
            val x2 = (nWidth * cosf - nHeight * sinf).toInt()
            val y2 = (nHeight * cosf + nWidth * sinf).toInt()
            val x3 = (nWidth * cosf).toInt()
            val y3 = (nWidth * sinf).toInt()

            val minX = min(0, min(x1, min(x2, x3)))
            val minY = min(0, min(y1, min(y2, y3)))
            val maxX = max(0, max(x1, max(x2, x3)))
            val maxY = max(0, max(y1, max(y2, y3)))

            val w = maxX - minX
            val h = maxY - minY
            val bmp: Bitmap = Bitmap.createBitmap(w, h, mBitmap.config)


            for (y in 0 until h)
                for (x in 0 until w) {

                    val sourceX = ((x + minX) * cosf + (y + minY) * sinf).toInt()
                    val sourceY = ((y + minY) * cosf - (x + minX) * sinf).toInt()
                    if (sourceX in 0 until nWidth && sourceY in 0 until nHeight)
                        bmp.setPixel(x, y, mBitmap.getPixel(sourceX, sourceY))
                    else
                        bmp.setPixel(x, y, 0)
                }
            image_view.setImageBitmap(bmp);
            gbmap = (image_view.getDrawable() as BitmapDrawable).bitmap

    }




}
