package com.example.myapplication14

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.filters_main.*
import java.util.*


class ThirdActivity : AppCompatActivity(), View.OnTouchListener {


    var graphView: GraphView? = null

    class V {
        var X = 0f
        var Y = 0f
    }

    private var mas: Vector<V> = Vector(0, 1)
    private var X = 0f
    private var Y = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        graphView = findViewById(R.id.graph)

        graphView!!.viewport.isXAxisBoundsManual = true
        graphView!!.viewport.setMinX(0.0)
        graphView!!.viewport.setMaxX(10.0)

        graphView!!.viewport.isYAxisBoundsManual = true
        graphView!!.viewport.setMinY(0.0)
        graphView!!.viewport.setMaxY(10.0)

        button.setOnClickListener {

            val mas_3 = arrayOfNulls<V>(mas.capacity())

            for (i in 0 until mas.capacity()) mas_3[i] = mas[i]

            for (i in mas_3.indices) {
                for (g in 0 until mas_3.size - 1) {
                    if (mas_3[g]!!.X > mas_3[g + 1]!!.X) {
                        val tmp = mas_3[g]
                        mas_3[g] = mas_3[g + 1]
                        mas_3[g + 1] = tmp
                    }
                }
            }
            for (i in 0 until mas_3.size - 1) {
                val N1 = mas_3[i]
                val N2 = mas_3[i + 1]
                val a = (N2!!.Y - N1!!.Y) / (N2!!.X - N1!!.X)
                val b = N1!!.Y - a * N1!!.X
                var g = (N1!!.X + 0.01).toFloat()
                while (g < (N2!!.X - 0.01).toFloat()) {
                    val k: DataPoint = DataPoint(g.toDouble(), (f(a, b, g)).toDouble())
                    val series2 =
                        PointsGraphSeries(
                            arrayOf<DataPoint>(k)
                        )
                    graphView!!.addSeries(series2)
                    series2.shape = PointsGraphSeries.Shape.POINT
                    series2.color = Color.BLACK
                    series2.size = 4f
                    g = (g + 0.01).toFloat()
                }
            }

        }

        button2.setOnClickListener {
            var minY = 0f

            var maxY = 10f

            val mas_2 = mas

            for (i in mas_2.indices) {
                for (g in 0 until mas_2.size - 1) {
                    if (mas_2[g]!!.X > mas_2[g + 1]!!.X) {
                        val tmp = mas_2[g]
                        mas_2[g] = mas_2[g + 1]
                        mas_2[g + 1] = tmp
                    }
                }
            }
            var i = mas_2[0]!!.X + 0.005f
            while (i < mas_2[mas.capacity() - 1]!!.X - 0.005f) {
                val y = lagrange(mas_2, mas.capacity(), i.toDouble())
                val k = DataPoint(i.toDouble(), y.toDouble())
                if (y > maxY) {
                    maxY = y
                }
                if (y < minY) {
                    minY = y
                }
                val series3 =
                    PointsGraphSeries(
                        arrayOf<DataPoint>(
                            k
                        )
                    )
                graphView!!.addSeries(series3)
                series3.shape = PointsGraphSeries.Shape.POINT
                series3.color = Color.RED
                series3.size = 4f
                i = i + 0.005f
            }



            graphView!!.viewport.setMinY(minY.toDouble())

            graphView!!.viewport.setMaxY(maxY.toDouble())


        }

        button7.setOnClickListener {
            graphView!!.removeAllSeries();
            mas.clear();
            graphView!!.getViewport().setMinX(0.0);
            graphView!!.getViewport().setMaxX(10.0);
            graphView!!.getViewport().setMinY(0.0);
            graphView!!.getViewport().setMaxY(10.0);
        }

        graph.setOnTouchListener(this);

    }


    private fun f(a: Float, b: Float, x: Float): Float {
        return (a * x + b)
    }

    private fun lagrange(mas: Vector<V>, n: Int, _x: Double): Float {
        var result = 0.0f
        for (i in 0 until n) {
            var P = 1.0
            for (j in 0 until n)
                if (i != j) {
                    P *= (_x - mas[j].X) / (mas[i].X - mas[j].X)
                }
            result = result + (P * mas[i].Y).toFloat()

        }
        return result
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        X = event!!.x
        Y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val id = ThirdActivity.V()
                id.X = (X / 100 - 0.25).toFloat()
                id.Y = (10 - Y / 100 - 0.25).toFloat()
                mas.add(id)
                val a = DataPoint(X / 100 - 0.25, 10 - Y / 100 - 0.25)
                val series = PointsGraphSeries(arrayOf(a))
                graphView!!.addSeries(series)
                series.shape = PointsGraphSeries.Shape.POINT
                series.setColor(Color.BLACK)
            }
        }
        return true
    }

}
