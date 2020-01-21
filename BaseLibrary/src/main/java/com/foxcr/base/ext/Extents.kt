package com.foxcr.base.ext

import android.content.Context
import android.widget.Toast

fun String.toast(context:Context,duration:Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(context.applicationContext,this,duration)

}