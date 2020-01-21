package com.foxcr.user.data.repository

import com.foxcr.base.data.net.RetrofitFactory
import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.user.data.api.UserApi
import com.foxcr.user.data.protocal.RegisterReq
import com.foxcr.user.data.protocal.RegisterResp
import io.reactivex.Observable

class UserRepository {
    fun register(username:String,password:String,repassword:String): Observable<BaseResp<RegisterResp>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(username,password, repassword))
    }
}