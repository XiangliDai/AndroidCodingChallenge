package com.xdai.challenge.di

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention
import javax.inject.Scope

@Scope
@Retention(value = RetentionPolicy.RUNTIME)
annotation class PerActivity