package com.foxcr.kotlineasyshop.injection.module

import com.foxcr.kotlineasyshop.service.HomeService
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import dagger.Module
import dagger.Provides


@Module
class HomeModule{

    @Provides
    fun homeProviders(service: HomeServiceImpl):HomeService{
        return service
    }
}