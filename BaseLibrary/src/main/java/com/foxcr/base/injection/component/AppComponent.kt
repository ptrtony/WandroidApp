package com.foxcr.base.injection.component

import android.content.Context
import com.foxcr.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context():Context
}