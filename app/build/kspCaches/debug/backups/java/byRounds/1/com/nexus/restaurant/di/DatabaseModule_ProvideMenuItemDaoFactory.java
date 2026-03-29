package com.nexus.restaurant.di;

import com.nexus.restaurant.data.local.MenuItemDao;
import com.nexus.restaurant.data.local.NexusDatabase;
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
public final class DatabaseModule_ProvideMenuItemDaoFactory implements Factory<MenuItemDao> {
  private final Provider<NexusDatabase> databaseProvider;

  public DatabaseModule_ProvideMenuItemDaoFactory(Provider<NexusDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MenuItemDao get() {
    return provideMenuItemDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideMenuItemDaoFactory create(
      Provider<NexusDatabase> databaseProvider) {
    return new DatabaseModule_ProvideMenuItemDaoFactory(databaseProvider);
  }

  public static MenuItemDao provideMenuItemDao(NexusDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMenuItemDao(database));
  }
}
