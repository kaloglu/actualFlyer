package com.kaloglu.actualflayer.di

import com.kaloglu.actualflayer.database.KeyValueStorage
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object KeyValueStorageModule {

    @Singleton
    @Provides
    internal fun provideMmkv(): MMKV = MMKV.mmkvWithID("core")

    @Singleton
    @Provides
    internal fun provideKeyValueStorage(mmkv: MMKV, json: Json) = KeyValueStorage(mmkv, json)

}