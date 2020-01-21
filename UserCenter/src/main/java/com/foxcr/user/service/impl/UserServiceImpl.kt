package com.foxcr.user.service.impl

import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.base.rx.BaseException
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.data.repository.UserRepository
import com.foxcr.user.service.UserService
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class UserServiceImpl:UserService {
    override fun register(
        username: String,
        password: String,
        repassword: String
    ): Observable<RegisterResp> {
        val userRepository = UserRepository()
        return userRepository.register(username, password, repassword)
            .subscribeOn(Schedulers.io())
            .flatMap(Function<BaseResp<RegisterResp>, Observable<RegisterResp>> {
                if (it.errorCode!=0){
                    return@Function Observable.error(BaseException(it.errorCode,it.errorMsg))
                }
                return@Function Observable.just(it.data)
            })
    }


}