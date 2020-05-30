package com.example.uiux
import android.Manifest
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
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.editor_main.*
import kotlin.math.roundToInt


class roflan(){

    companion object{lateinit var bitmap: Bitmap; var brightt: Double = 1.0}

}

class MainActivity : AppCompatActivity() {

    private val PICTURE_RESULT: Int = 1001
    var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1;
    private val CAMERA = 2;
    private val CAMERA_PERMISSION_CODE = 100;
    private val STORAGE_PERMISSION_CODE = 101;
    private lateinit var gbmap: Bitmap
    private var f = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        main_card.setOnClickListener {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
            pickImageFromGallery(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }

        camera_main.setOnClickListener {

            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            //permission already granted
            takePhoto(Manifest.permission.CAMERA);

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
            var values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
            imageUri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
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
            f = true
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA){
            try {
                var thumbnail = MediaStore.Images.Media.getBitmap(
                    contentResolver, imageUri
                )
                gbmap = thumbnail
            } catch (e: Exception) {
                e.printStackTrace()
            }
            f = true
        }
        if (f == true) {
            roflan.bitmap = gbmap
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
        }
    }

}



