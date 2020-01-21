package com.foxcr.user.service

import com.foxcr.user.data.protocal.RegisterResp
import io.reactivex.Observable


interface UserService {
    fun register(username:String,password:String,repassword:String): Observable<RegisterResp>
}