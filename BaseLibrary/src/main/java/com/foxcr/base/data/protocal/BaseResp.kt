package com.foxcr.base.data.protocal
data class BaseResp<T>(var errorCode:Int,var errorMsg:String?,var data:T)