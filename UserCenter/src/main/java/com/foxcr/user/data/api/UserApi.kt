package com.foxcr.user.data.api
import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.user.data.protocal.RegisterReq
import com.foxcr.user.data.protocal.RegisterResp
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user/register")
    fun register(@Body registerReq: RegisterReq): Observable<BaseResp<RegisterResp>>

}