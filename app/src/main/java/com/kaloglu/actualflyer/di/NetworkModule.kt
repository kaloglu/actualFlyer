package com.kaloglu.actualflyer.di

import com.kaloglu.actualflyer.domain.database.UserLocalDataSourceImpl
import com.kaloglu.actualflyer.domain.database.UserLocalDataSource
import com.kaloglu.actualflyer.network.UserDataSource
import com.kaloglu.actualflyer.network.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface NetworkModule {

    @get:Binds
    val UserDataSourceImpl.userDataSourceImpl: UserDataSource

    @get:Binds
    val UserLocalDataSourceImpl.userLocalDataSourceImpl: UserLocalDataSource
}