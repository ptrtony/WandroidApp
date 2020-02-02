package com.foxcr.kotlineasyshop.injection.component

import com.foxcr.base.injection.PerActivityScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.ui.fragment.NavigationFragment
import dagger.Component

@PerActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [HomeModule::class])
interface NavigationComponent {
    fun inject(navigationFragment: NavigationFragment)
}