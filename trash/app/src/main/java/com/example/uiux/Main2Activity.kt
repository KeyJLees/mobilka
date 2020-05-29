package com.example.uiux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupWindow
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.editor_main.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_main)

        btn_filters.setOnClickListener {

         val filters=BottomSheetDialog(this)
        val view=layoutInflater.inflate(R.layout.filters_layout,null)
            filters.setContentView(view)
            filters.show()
        }
    }
}
