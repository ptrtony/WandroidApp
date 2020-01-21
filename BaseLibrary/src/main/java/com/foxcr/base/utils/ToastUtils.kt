package com.foxcr.base.utils

import android.content.Context
import android.widget.Toast
import java.util.*

class ToastUtils {
    companion object{
        private lateinit var mContext:Context
        private lateinit var mToast:Toast
        fun init(context: Context){
            mContext = context.applicationContext
            mToast = Toast.makeText(mContext,"",Toast.LENGTH_SHORT)
        }

        private fun showToast(info:String,duration:Int){
            mToast.setText(info)
            mToast.duration = duration
            mToast.show()
        }

        fun showToast(info:String){
            showToast(info,Toast.LENGTH_SHORT)
            mToast.show()
        }

        private fun showToast(resId:Int,duration:Int){
            showToast(mContext.getText(resId).toString(),duration)
        }

        fun showToast(resId:Int){
            showToast(resId,Toast.LENGTH_SHORT)
        }

        fun cancelToast(toast:Toast,cut:Long){
            val timer = Timer()
            timer.schedule(object:TimerTask(){
                override fun run() {
                    toast.cancel()
                    timer.cancel()
                }

            },cut)
        }

    }

}