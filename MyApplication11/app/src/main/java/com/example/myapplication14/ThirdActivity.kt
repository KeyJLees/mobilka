package com.example.myapplication14

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.filters_main.*
import kotlin.math.roundToInt

class ThirdActivity : AppCompatActivity() {
    private lateinit var ggbmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        ggbmap = roflan.bitmap
        image_view3.setImageBitmap(ggbmap);
    }

    fun prevSlide2(view: View)
    {
        val intent = Intent(this, SecondActivity::class.java)
        val bmap: Bitmap = (image_view2.getDrawable() as BitmapDrawable).bitmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
        roflan.bitmap = mBitmap
        startActivity(intent)
    }
}
