package com.foxcr.user.injection.module

import com.foxcr.user.service.UserService
import com.foxcr.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides


@Module
class UserRegisterModule {

    @Provides
    fun providerUserRegisterService(userRegisterService:UserServiceImpl):UserService{
        return userRegisterService
    }
}