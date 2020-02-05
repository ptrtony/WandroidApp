package com.foxcr.kotlineasyshop.injection.component

import com.foxcr.base.injection.PerActivityScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.ui.activity.CoinRankActivity
import dagger.Component


@PerActivityScope
@Component(modules = [HomeModule::class],dependencies = [ActivityComponent::class])
interface CoinBankComponent {
    fun inject(coinRankActivity: CoinRankActivity)
}