package com.foxcr.user.service.impl
import com.foxcr.user.data.protocal.LoginResp
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.data.repository.UserRepository
import com.foxcr.user.service.UserService
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor():UserService {

    @Inject
    lateinit var userRepository:UserRepository
    override fun register(
        username: String,
        password: String,
        repassword: String
    ): Observable<RegisterResp> {
        return userRepository.register(username, password, repassword)
    }

    override fun login(username: String, password: String): Observable<LoginResp> {
        return userRepository.login(username,password)
    }


}