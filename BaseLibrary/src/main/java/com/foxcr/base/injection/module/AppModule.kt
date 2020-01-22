package com.foxcr.base.injection.module

import android.content.Context
import com.foxcr.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val context: BaseApplication){

    @Provides
    @Singleton
    fun providesAppContext():Context{
        return context
    }
}