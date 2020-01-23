package com.foxcr.user.data.api
import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.user.data.protocal.LoginResp
import com.foxcr.user.data.protocal.RegisterResp
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username:String,@Field("password")password:String,@Field("repassword")repassword:String): Observable<BaseResp<RegisterResp>>

    /**
     * 登陆
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username:String,@Field("password")password:String):Observable<BaseResp<LoginResp>>
}