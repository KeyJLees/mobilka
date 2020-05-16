package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val IMAGE_PICK_CODE = 1;
    private val CAMERA = 2;
    private val CAMERA_PERMISSION_CODE = 100;
    private val STORAGE_PERMISSION_CODE = 101;
    private val imageview: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        //BUTTON CLICK
        img_pick_btn.setOnClickListener {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
            //permission already granted
            pickImageFromGallery(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        img_pick_btn2.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            //permission already granted
            takePhoto(Manifest.permission.CAMERA);
        }

        rotate_img_left.setOnClickListener {

            rottateImage(image_view, 270);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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

    private fun rottateImage(view: ImageView, degrees: Int) {

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
