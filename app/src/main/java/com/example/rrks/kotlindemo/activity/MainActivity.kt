package com.example.rrks.kotlindemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.rrks.kotlindemo.*
import com.example.rrks.kotlindemo.utils.Demo
import com.example.rrks.kotlindemo.utils.JavaDemo
import com.example.rrks.kotlindemo.utils.showToast
import com.example.rrks.kotlindemo.utils.spaceToCamelCase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
//        val tv_hello_world: TextView = findViewById(R.id.tv_hello_world)
//        var text_view: TextView = findViewById(R.id.text_view)
//        var et_first: EditText = findViewById(R.id.et_first)
//        var et_second: EditText = findViewById(R.id.et_second)

//        var jss:Int = 2
        text_view.text = "?"
        var text = text_view.text
        findViewById<Button>(R.id.button).setOnClickListener {
            show()
        }

//        val ctv: ColorfulTextView = findViewById(R.id.ctv)
//        ctv.text = "点击"

    }

    private fun show() {
        if (TextUtils.isEmpty(et_first.text) || TextUtils.isEmpty(et_second.text)) {
            Toast.makeText(this, "请填写数字", Toast.LENGTH_SHORT).show()
            return
        }
        var firstNum: Int = et_first.text.toString().toInt()
        var secondNum = et_second.text.toString().toInt()
        var demo = Demo()
        demo.name = ""
        var demoName = demo.getString()
        var result = spaceToCamelCase()

        when {
            firstNum > secondNum -> text_view.text = ">"
            firstNum > secondNum -> text_view.text = "<"
            firstNum == secondNum -> text_view.text = "="
        }
        showToast(result)

//        var items = listOf("123",345,4343)
        var javaDemo = JavaDemo()
        javaDemo.num
    }

    private fun getString(string: String): String {
        return "ggggggggggggggggggggg" + string
    }


}
