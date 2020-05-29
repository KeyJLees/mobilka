package com.example.myapplication14

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.myapplication14.roflan.Companion.bitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filters_main.*
import kotlin.math.*


class SecondActivity : AppCompatActivity() {

    private var brightt: Double = 1.0
    private lateinit var ggbmap: Bitmap
    private var sumred: Int = 0
    private var sumgreen: Int = 0
    private var sumblue: Int = 0
    private var sumс: Int = 0
    var Pix: Array<Pixels> = arrayOf()

    class Pixels {
        var X = 0f
        var Y = 0f
    }

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
            mashtab(edit_text2.text.toString().toDouble())
        }

        mask.setOnClickListener {
            unsharp(ggbmap, 5, 5.toFloat(), 2)
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

        image_view2.setOnTouchListener(View.OnTouchListener { v, event ->
            val action = event.action
            var motionTouchEventX = event.x
            var motionTouchEventY = event.y
            motionTouchEventY *= bitmap!!.height.toFloat() / image_view.height.toFloat()
            motionTouchEventX *= bitmap!!.width.toFloat() / image_view.width.toFloat()
            sumred = 0
            sumgreen = 0
            sumblue = 0
            sumс = 0

            when (action) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_MOVE -> {
                    Pix[sumс].X = motionTouchEventX
                    Pix[sumс].Y = motionTouchEventY
                    sumс++
                    var r = ggbmap.getPixel(motionTouchEventX.toInt(), motionTouchEventY.toInt()).red
                    var g = ggbmap.getPixel(motionTouchEventX.toInt(), motionTouchEventY.toInt()).green
                    var b = ggbmap.getPixel(motionTouchEventX.toInt(), motionTouchEventY.toInt()).blue
                    sumred += r
                    sumgreen += g
                    sumblue += b
                }
                MotionEvent.ACTION_UP -> {
                    retush()
                }
            }
            true
        })

    }

    fun prevSlide(view: View)
    {
        val randomIntent= Intent(this, MainActivity::class.java)
        startActivity(randomIntent)
    }

    fun nextSlide(view: View)
    {
        val randomIntent= Intent(this,ThirdActivity::class.java)
        startActivity(randomIntent)
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
        ggbmap = bmap
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

    private fun retush() {
        val coof = 0.2
        val bmap: Bitmap = ggbmap
        val aspectRatio: Float = bmap.height.toFloat() / bmap.width
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val mImageWidth = displayMetrics.widthPixels
        val mImageHeight = (mImageWidth * aspectRatio).roundToInt()
        val mBitmap = Bitmap.createScaledBitmap(bmap, mImageWidth, mImageHeight, false)
        val nWidth: Int = (mBitmap.width)
        val nHeight: Int = (mBitmap.height)
        val bmp: Bitmap = Bitmap.createBitmap(nWidth, nHeight, mBitmap.config)
        val PixelRed = sumred / sumс
        val PixelGreen = sumgreen / sumс
        val PixelBlue = sumblue / sumс
        for (cc in 0 until sumс) {
            var r = mBitmap.getPixel((Pix[cc].X).toInt(), (Pix[cc].Y).toInt()).red
            var g = mBitmap.getPixel((Pix[cc].X).toInt(), (Pix[cc].Y).toInt()).green
            var b = mBitmap.getPixel((Pix[cc].X).toInt(), (Pix[cc].Y).toInt()).blue
            r += ((PixelRed - r) * coof).toInt()
            g += ((PixelGreen - g) * coof).toInt()
            b += ((PixelBlue - b) * coof).toInt()
            ggbmap.setPixel((Pix[cc].X).toInt(), (Pix[cc].Y).toInt(), Color.rgb(r, g, b))
            }
        image_view2.setImageBitmap(ggbmap);
    }

    private fun unsharp(ivPhoto: Bitmap, radius: Int, amount:Float, threshold: Int){

        val blurredPhoto = boxBlur(ivPhoto!!, radius)
        val originalPixels =  Array(ivPhoto!!.width, {IntArray(ivPhoto!!.height)})
        val blurredPixels = Array(blurredPhoto!!.width, {IntArray(blurredPhoto!!.height)})

        for (j in 0 until ivPhoto!!.height) {
            for (i in 0 until ivPhoto!!.width) {
                originalPixels[i][j] = ivPhoto!!.getPixel(i, j)
                blurredPixels[i][j] = blurredPhoto!!.getPixel(i, j)
            }
        }

        unsharpMask(ivPhoto,originalPixels, blurredPixels, amount, threshold)
    }

    private fun boxBlur(bitmap: Bitmap, range: Int): Bitmap? {
        assert(range and 1 == 0) { "Range must be odd." }

        val width = bitmap.width
        val height = bitmap.height

        val blurred = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(blurred)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        boxBlurHorizontal(pixels, width, height, range / 2)
        boxBlurVertical(pixels, width, height, range / 2)

        canvas.drawBitmap(pixels, 0, width, 0.0f, 0.0f, width, height, true, null)
        return blurred
    }


    private fun boxBlurHorizontal(pixels: IntArray, w: Int, h: Int, halfRange: Int) {
        var index = 0
        val newColors = IntArray(w)
        for (y in 0 until h) {
            var hits = 0
            var r: Long = 0
            var g: Long = 0
            var b: Long = 0
            for (x in -halfRange until w) {
                val oldPixel = x - halfRange - 1
                if (oldPixel >= 0) {
                    val color = pixels[index + oldPixel]
                    if (color != 0) {
                        r -= Color.red(color)
                        g -= Color.green(color)
                        b -= Color.blue(color)
                    }
                    hits--
                }
                val newPixel = x + halfRange
                if (newPixel < w) {
                    val color = pixels[index + newPixel]
                    if (color != 0) {
                        r += Color.red(color)
                        g += Color.green(color)
                        b += Color.blue(color)
                    }
                    hits++
                }
                if (x >= 0) {
                    newColors[x] = Color.rgb((r / hits).toInt(),
                        (g / hits).toInt(), (b / hits).toInt()
                    )
                }
            }
            for (x in 0 until w) {
                pixels[index + x] = newColors[x]
            }
            index += w
        }
    }


    private fun boxBlurVertical(pixels: IntArray, w: Int, h: Int, halfRange: Int) {
        val newColors = IntArray(h)
        val oldPixelOffset = -(halfRange + 1) * w
        val newPixelOffset = halfRange * w
        for (x in 0 until w) {
            var hits = 0
            var r: Long = 0
            var g: Long = 0
            var b: Long = 0
            var index = -halfRange * w + x
            for (y in -halfRange until h) {
                val oldPixel = y - halfRange - 1
                if (oldPixel >= 0) {
                    val color = pixels[index + oldPixelOffset]
                    if (color != 0) {
                        r -= Color.red(color)
                        g -= Color.green(color)
                        b -= Color.blue(color)
                    }
                    hits--
                }
                val newPixel = y + halfRange
                if (newPixel < h) {
                    val color = pixels[index + newPixelOffset]
                    if (color != 0) {
                        r += Color.red(color)
                        g += Color.green(color)
                        b += Color.blue(color)
                    }
                    hits++
                }
                if (y >= 0) {
                    newColors[y] = Color.rgb((r / hits).toInt(),
                        (g / hits).toInt(), (b / hits).toInt()
                    )
                }
                index += w
            }
            for (y in 0 until h) {
                pixels[y * w + x] = newColors[y]
            }
        }
    }


    private fun unsharpMask(
        ivPhoto: Bitmap,
        origPixels: Array<IntArray>,
        blurredPixels: Array<IntArray>,
        amount: Float,
        threshold: Int) {

        val newBitmap = Bitmap.createBitmap(ivPhoto!!.width, ivPhoto!!.height, Bitmap.Config.ARGB_8888)

        var orgRed = 0
        var orgGreen = 0
        var orgBlue = 0
        var blurredRed = 0
        var blurredGreen = 0
        var blurredBlue = 0
        var usmPixel = 0
        val alpha = -0x1000000
        for (j in 0 until newBitmap.height) {
            for (i in 0 until newBitmap.width) {
                val origPixel = origPixels[i][j]
                val blurredPixel = blurredPixels[i][j]

                orgRed = origPixel shr 16 and 0xff
                orgGreen = origPixel shr 8 and 0xff
                orgBlue = origPixel and 0xff
                blurredRed = blurredPixel shr 16 and 0xff
                blurredGreen = blurredPixel shr 8 and 0xff
                blurredBlue = blurredPixel and 0xff

                if (abs(orgRed - blurredRed) >= threshold) {
                    orgRed = (amount * (orgRed - blurredRed) + orgRed).toInt()
                    orgRed = if (orgRed > 255) 255 else if (orgRed < 0) 0 else orgRed
                }
                if (abs(orgGreen - blurredGreen) >= threshold) {
                    orgGreen = (amount * (orgGreen - blurredGreen) + orgGreen).toInt()
                    orgGreen = if (orgGreen > 255) 255 else if (orgGreen < 0) 0 else orgGreen
                }
                if (abs(orgBlue - blurredBlue) >= threshold) {
                    orgBlue = (amount * (orgBlue - blurredBlue) + orgBlue).toInt()
                    orgBlue = if (orgBlue > 255) 255 else if (orgBlue < 0) 0 else orgBlue
                }
                usmPixel = alpha or (orgRed shl 16) or (orgGreen shl 8) or orgBlue
                newBitmap.setPixel(i, j, usmPixel)
            }
        }
        image_view2.setImageBitmap(newBitmap)

    }

}
