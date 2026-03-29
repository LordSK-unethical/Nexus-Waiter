package com.nexus.restaurant.di

import android.content.Context
import androidx.room.Room
import com.nexus.restaurant.data.local.MenuItemDao
import com.nexus.restaurant.data.local.NexusDatabase
import com.nexus.restaurant.data.local.OrderDao
import com.nexus.restaurant.data.local.TableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NexusDatabase {
        return Room.databaseBuilder(
            context,
            NexusDatabase::class.java,
            "nexus_restaurant.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(database: NexusDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    @Singleton
    fun provideTableDao(database: NexusDatabase): TableDao {
        return database.tableDao()
    }

    @Provides
    @Singleton
    fun provideMenuItemDao(database: NexusDatabase): MenuItemDao {
        return database.menuItemDao()
    }
}
