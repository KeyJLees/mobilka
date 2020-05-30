package com.example.uiux

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlinx.android.synthetic.main.filters_layout.*
import kotlin.math.roundToInt

class filtersActivity : AppCompatActivity() {

    private var ggbmap = roflan.bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filters_layout)
        button1.setOnClickListener{
            sepia()
        }

        button2.setOnClickListener{
            whiteblack()
        }

        button3.setOnClickListener{
            negative()
        }

        button4.setOnClickListener{
            gray()
        }


    }

    private fun negative() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val mImageWidth = 900
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
        roflan.bitmap = bmp
    }

    private fun whiteblack() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val mImageWidth = 900
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)

        val nWidth: Int = mBitmap.width
        val nHeight: Int = mBitmap.height
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)

        val separator: Double = 255 / roflan.brightt / 2 * 3
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
        roflan.bitmap = bmp
    }

    private fun gray() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val mImageWidth = 900
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
        roflan.bitmap = bmp

    }

    private fun sepia() {
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val mImageWidth = 900
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
        roflan.bitmap = bmp

    }

}
