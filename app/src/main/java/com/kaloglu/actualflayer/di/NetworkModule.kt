package com.kaloglu.actualflayer.di

import com.kaloglu.actualflayer.database.source.UserLocalDataSourceImpl
import com.kaloglu.actualflayer.domain.database.UserLocalDataSource
import com.kaloglu.actualflayer.network.UserApi
import com.kaloglu.actualflayer.network.UserDataSource
import com.kaloglu.actualflayer.network.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface NetworkModule {

    @get:Binds
    val UserDataSourceImpl.userDataSourceImpl: UserDataSource

    @get:Binds
    val UserLocalDataSourceImpl.userLocalDataSourceImpl: UserLocalDataSource
}