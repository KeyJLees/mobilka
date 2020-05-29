package com.example.uiux

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.editor_main.*

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_main)




        btn_rotate.setOnClickListener {
            val window= PopupWindow(this)
            val view=layoutInflater.inflate(R.layout.layout_popup,null)
            window.contentView=view
            window.showAsDropDown(btn_rotate) }


        btn_scaling.setOnClickListener {
            val window= PopupWindow(this)
            val view=layoutInflater.inflate(R.layout.scale_popup,null)
            window.contentView=view
            window.showAsDropDown(btn_rotate) }



        btn_filters.setOnClickListener {

            val filters=BottomSheetDialog(this)
            val view=layoutInflater.inflate(R.layout.filters_layout,null)
            filters.setContentView(view)
            filters.show()
        }


    }


}
