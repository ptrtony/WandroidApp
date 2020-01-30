package com.foxcr.base.utils

import android.content.Context
import android.content.SharedPreferences
import com.foxcr.base.common.BaseApplication
import com.foxcr.base.common.BaseConstant

object SPUtil {
    private val sp:SharedPreferences = BaseApplication.context.getSharedPreferences(BaseConstant.TABLE_PREFS,Context.MODE_PRIVATE)
    private var ed:SharedPreferences.Editor
    init {
        ed = sp.edit()
    }
    /**
     * Boolean数据
     */
    fun putBoolean(key:String,value:Boolean){
        ed.putBoolean(key,value)
        ed.commit()
    }

    /**
     * 默认false
     */
    fun getBoolean(key:String) :Boolean{
        return sp.getBoolean(key,false)
    }

    /**
     * String数据
     */
    fun putString(key:String,value:String){
        ed.putString(key,value)
        ed.commit()
    }

    fun getString(key:String,value:String) :String?{
        return sp.getString(key,value)
    }

    /**
     * float数据
     */
    fun putFloat(key:String,value:Float) {
        ed.putFloat(key,value)
        ed.commit()
    }

    fun  getFloat(key:String,value: Float ) :Float{
        return sp.getFloat(key,value)
    }

    /**
     * Int数据
     */
    fun putInt(key:String,value:Int){
        ed.putInt(key,value)
        ed.commit()
    }

    fun getInt(key:String,value : Int) :Int{
        return sp.getInt(key,value)
    }

    /**
     * Long数据
     */
    fun putLong(key:String,value:Long){
        ed.putLong(key,value)
        ed.commit()
    }

    fun getLong(key:String,value:Long) :Long{
        return sp.getLong(key,value)
    }

    /**
     * set 数据
     */
    fun putStringSet(key :String,set:Set<String>){
        val localSet = getStringSet(key)?.toMutableSet()
        localSet?.addAll(set)
        ed.putStringSet(key,localSet)
        ed.commit()
    }


    fun getStringSet(key:String):Set<String>?{
        val set = setOf<String>()
        return sp.getStringSet(key,set)
    }
    /**
     * 删除key数据
     */
    fun remove(key:String){
        ed.remove(key)
        ed.commit()
    }
}