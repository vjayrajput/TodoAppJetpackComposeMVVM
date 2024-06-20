package com.app.todo.business.di

import com.app.todo.business.data.di.BusinessDataModule
import com.app.todo.business.domain.di.BusinessDomainModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [BusinessDataModule::class, BusinessDomainModule::class])
@InstallIn(SingletonComponent::class)
object BusinessModule {
    //DO NOTHING
}