package com.nexus.restaurant.di;

import com.nexus.restaurant.data.local.NexusDatabase;
import com.nexus.restaurant.data.local.OrderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DatabaseModule_ProvideOrderDaoFactory implements Factory<OrderDao> {
  private final Provider<NexusDatabase> databaseProvider;

  public DatabaseModule_ProvideOrderDaoFactory(Provider<NexusDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public OrderDao get() {
    return provideOrderDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideOrderDaoFactory create(
      Provider<NexusDatabase> databaseProvider) {
    return new DatabaseModule_ProvideOrderDaoFactory(databaseProvider);
  }

  public static OrderDao provideOrderDao(NexusDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideOrderDao(database));
  }
}
