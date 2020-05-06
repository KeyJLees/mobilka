package com.blogspot.atifsoftwares.imagepick_kotlin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BUTTON CLICK
        img_pick_btn.setOnClickListener {

                    //permission already granted
                    pickImageFromGallery();
                }

        rotate_img_right.setOnClickListener {

            rotateImageRight();
        }


        rotate_img_left.setOnClickListener {

            rotateImageLeft();
        }

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    var angle:Float=0F
    private fun rotateImageRight() {
        angle=angle+90F
        image_view.setRotation(angle);
    }

    private fun rotateImageLeft() {
        angle=angle-90F
        image_view.setRotation(angle);
    }


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result


    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image_view.setImageURI(data?.data)
        }
    }
}
