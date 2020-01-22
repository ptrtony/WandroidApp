package com.foxcr.user.injection.component

import com.foxcr.base.injection.PerActivityScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.ui.activity.RegisterActivity
import dagger.Component


@PerActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [UserRegisterModule::class])
interface UserRegisterComponent {
    fun inject(registerActivity: RegisterActivity)
}