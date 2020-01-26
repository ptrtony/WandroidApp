package com.foxcr.kotlineasyshop.injection.component

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.foxcr.base.injection.FragmentScope
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.kotlineasyshop.injection.module.FragmentModule
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Component


@FragmentScope
@Component(dependencies = [ActivityComponent::class],modules = [FragmentModule::class])
interface FragmentComponent {
    fun activity():Activity
    fun context():Context
    fun fragment():Fragment
    fun lifecycleProvider(): LifecycleProvider<*>
}