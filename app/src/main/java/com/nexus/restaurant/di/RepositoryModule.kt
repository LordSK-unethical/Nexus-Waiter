package com.nexus.restaurant.di

import com.nexus.restaurant.data.repository.OrderRepositoryImpl
import com.nexus.restaurant.data.repository.SocketRepositoryImpl
import com.nexus.restaurant.data.repository.TableRepositoryImpl
import com.nexus.restaurant.domain.repository.OrderRepository
import com.nexus.restaurant.domain.repository.SocketRepository
import com.nexus.restaurant.domain.repository.TableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindTableRepository(
        tableRepositoryImpl: TableRepositoryImpl
    ): TableRepository

    @Binds
    @Singleton
    abstract fun bindSocketRepository(
        socketRepositoryImpl: SocketRepositoryImpl
    ): SocketRepository
}
