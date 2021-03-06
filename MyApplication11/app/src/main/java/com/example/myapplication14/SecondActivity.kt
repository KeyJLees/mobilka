package com.example.myapplication14

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filters_main.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


class SecondActivity : AppCompatActivity() {

    private var brightt: Double = 1.0
    private lateinit var ggbmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filters_main)

        ggbmap = roflan.bitmap
        image_view2.setImageBitmap(ggbmap);


        button3.setOnClickListener {
            negative()
        }

        button4.setOnClickListener {
            whiteblack()
        }

        button5.setOnClickListener {
            gray()
        }

        button6.setOnClickListener {
            sepia()
        }

        image_scaling.setOnClickListener {
            val msg: String = edit_text2.text.toString()
            if(msg.trim().isNotEmpty())
            mashtab(edit_text2.text.toString().toDouble())
            else
            {
                Toast.makeText(
                    this,
                    "Scaling factor not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                bright(seekBar.progress / 10.0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

    }

    fun prevSlide(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        val bmap: Bitmap = (image_view2.getDrawable() as BitmapDrawable).bitmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
        roflan.bitmap = mBitmap
        startActivity(intent)
    }


    fun nextSlide2(view: View)
    {
        val intent = Intent(this, ThirdActivity::class.java)
        val bmap: Bitmap = (image_view2.getDrawable() as BitmapDrawable).bitmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
        roflan.bitmap = mBitmap
        startActivity(intent)
    }


    private fun bright(brightness: Double) {
        brightt = brightness
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)
        for (y in 0 until nHeight)
            for (x in 0 until nWidth){
                val r = mBitmap.getPixel(x, y).red
                val g = mBitmap.getPixel(x, y).green
                val b = mBitmap.getPixel(x, y).blue
                var red: Int = (r * brightness).toInt()
                red = min(255, max(0,red))
                var green: Int = (g * brightness).toInt()
                green = min(255, max(0,green))
                var blue: Int = (b * brightness).toInt()
                blue = min(255, max(0,blue))
                bmp.setPixel(x,y, Color.rgb(red, green, blue))
            }
        image_view2.setImageBitmap(bmp);
    }

    private fun negative() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)
        for (y in 0 until nHeight)
            for (x in 0 until nWidth) {
                val r = mBitmap.getPixel(x, y).red
                val g = mBitmap.getPixel(x, y).green
                val b = mBitmap.getPixel(x, y).blue
                bmp.setPixel(x,y, Color.rgb(255 - r, 255 - g, 255 - b))
            }
        image_view2.setImageBitmap(bmp);

    }

    private fun whiteblack() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)

        val separator: Double = 255 / brightt / 2 * 3
        for (y in 0 until nHeight)
            for (x in 0 until nWidth) {
                val r = mBitmap.getPixel(x, y).red
                val g = mBitmap.getPixel(x, y).green
                val b = mBitmap.getPixel(x, y).blue
                val total: Double = (r + g + b).toDouble()
                if (total > separator){
                    bmp.setPixel(x,y, Color.rgb(255, 255, 255))
                }
                else {
                    bmp.setPixel(x,y, Color.rgb(0, 0, 0))
                }
            }
        image_view2.setImageBitmap(bmp);
    }

    private fun gray() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)

        for (y in 0 until nHeight)
            for (x in 0 until nWidth) {
                val r = mBitmap.getPixel(x, y).red
                val g = mBitmap.getPixel(x, y).green
                val b = mBitmap.getPixel(x, y).blue
                val grayy: Int = (r * 0.2126 + g * 0.7152 + b * 0.0722).toInt()
                bmp.setPixel(x,y, Color.rgb(grayy, grayy, grayy))
            }
        image_view2.setImageBitmap(bmp);
    }

    private fun sepia() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)

        for (y in 0 until nHeight)
            for (x in 0 until nWidth) {
                val r = mBitmap.getPixel(x, y).red
                val g = mBitmap.getPixel(x, y).green
                val b = mBitmap.getPixel(x, y).blue
                val red: Int = (r * 0.393 + g * 0.769 + b * 0.189).toInt()
                val green = (r * 0.349 + g * 0.686 + b * 0.168).toInt()
                val blue = (r * 0.272 + g * 0.534 + b * 0.131).toInt()
                bmp.setPixel(x,y, Color.rgb(red, green, blue))
            }
        image_view2.setImageBitmap(bmp);
    }

    private fun mashtab(koef: Double) {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = (mBitmap.width * koef).toInt()
        val nHeight: Int = (mBitmap.height * koef).toInt()
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)

        for (y in 0 until nHeight)
            for (x in 0 until nWidth) {
                val r = mBitmap.getPixel((x / koef).toInt(), (y/koef).toInt())
                bmp.setPixel(x, y, r)
            }
        ggbmap = bmap
        image_view2.setImageBitmap(bmp);
    }



}
