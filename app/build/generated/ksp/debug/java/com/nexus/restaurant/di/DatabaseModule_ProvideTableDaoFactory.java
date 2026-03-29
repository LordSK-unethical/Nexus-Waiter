package com.nexus.restaurant.di;

import com.nexus.restaurant.data.local.NexusDatabase;
import com.nexus.restaurant.data.local.TableDao;
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
public final class DatabaseModule_ProvideTableDaoFactory implements Factory<TableDao> {
  private final Provider<NexusDatabase> databaseProvider;

  public DatabaseModule_ProvideTableDaoFactory(Provider<NexusDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TableDao get() {
    return provideTableDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTableDaoFactory create(
      Provider<NexusDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTableDaoFactory(databaseProvider);
  }

  public static TableDao provideTableDao(NexusDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTableDao(database));
  }
}
