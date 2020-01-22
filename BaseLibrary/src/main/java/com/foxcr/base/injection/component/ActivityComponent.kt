package com.foxcr.base.injection.component

import android.app.Activity
import com.foxcr.base.injection.ActivityScope
import com.foxcr.base.injection.module.ActivityModule
import com.foxcr.base.injection.module.LifecycleProvideModule
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class, LifecycleProvideModule::class],dependencies = [AppComponent::class])
interface ActivityComponent {
    fun injectionActivity():Activity
    fun injectionLifecycleProvider(): LifecycleProvider<*>
}