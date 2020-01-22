package com.foxcr.base.injection.module

import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Module
import dagger.Provides

@Module
class LifecycleProvideModule(private val lifecycleProvider: LifecycleProvider<*>){

    @Provides
    fun providesLifecycleProvider():LifecycleProvider<*>{
        return lifecycleProvider
    }
}