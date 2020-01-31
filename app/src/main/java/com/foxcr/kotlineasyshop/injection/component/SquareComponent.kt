package com.foxcr.kotlineasyshop.injection.component

import com.foxcr.base.injection.PerActivityScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.ui.fragment.HomeFragment
import com.foxcr.kotlineasyshop.ui.fragment.MainFragment
import com.foxcr.kotlineasyshop.ui.fragment.SquareFragment
import dagger.Component

@PerActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [HomeModule::class])
interface SquareComponent {
    fun inject(squareFragment: SquareFragment)
}