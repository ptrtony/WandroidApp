package com.foxcr.base.injection

import java.lang.annotation.Documented
import java.lang.annotation.Retention

import javax.inject.Scope
import java.lang.annotation.RetentionPolicy.RUNTIME
@Scope
@Retention(RUNTIME)
@Documented
annotation class PerActivityScope