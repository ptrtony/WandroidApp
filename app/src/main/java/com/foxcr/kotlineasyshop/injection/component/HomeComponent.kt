package com.foxcr.kotlineasyshop.injection.component

import com.foxcr.base.injection.PerActivityScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.ui.fragment.HomeFragment
import dagger.Component

@PerActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}