package com.foxcr.kotlineasyshop.injection.module

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides


@Module
class FragmentModule constructor(val fragment:Fragment){

    @Provides
    fun providersFragment():Fragment{
        return fragment
    }
}