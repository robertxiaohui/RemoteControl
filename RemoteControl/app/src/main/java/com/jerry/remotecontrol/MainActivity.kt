package com.jerry.remotecontrol

import android.annotation.TargetApi
import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mCIR: ConsumerIrManager? = null

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCIR = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager//获取红外服务
    }

    fun initView() {

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun onClick(view: View) {
        //Toast.makeText(this,"Oclick",Toast.LENGTH_SHORT).show();
        when (view.id) {
            R.id.mBtnMenu -> sendOrder(KeyMap.KEYCODE_MENU)
            R.id.mBtnPower -> sendOrder(KeyMap.KEYCODE_POWER)
            R.id.mBtnSource -> sendOrder(KeyMap.KEYCODE_INPUT)
            R.id.mBtnLeft -> sendOrder(KeyMap.KEYCODE_LEFT)
            R.id.mBtnUp -> sendOrder(KeyMap.KEYCODE_UP)
            R.id.mBtnRight -> sendOrder(KeyMap.KEYCODE_RIGHT)
            R.id.mBtnDown -> sendOrder(KeyMap.KEYCODE_DOWN)
            R.id.mBtnEnter -> sendOrder(KeyMap.KEYCODE_OK)
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun sendOrder(order: Int) {
        var tmp = Integer.toHexString(order)
        Log.d("TAG","order = "+order+", tmp = "+tmp)
        mCIR?.transmit(38000, RemoteUtils.creatOrder(tmp))
    }
}
