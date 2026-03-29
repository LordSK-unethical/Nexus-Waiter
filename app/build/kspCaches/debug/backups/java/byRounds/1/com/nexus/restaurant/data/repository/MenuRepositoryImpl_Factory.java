package com.nexus.restaurant.data.repository;

import com.nexus.restaurant.data.local.MenuItemDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MenuRepositoryImpl_Factory implements Factory<MenuRepositoryImpl> {
  private final Provider<MenuItemDao> menuItemDaoProvider;

  public MenuRepositoryImpl_Factory(Provider<MenuItemDao> menuItemDaoProvider) {
    this.menuItemDaoProvider = menuItemDaoProvider;
  }

  @Override
  public MenuRepositoryImpl get() {
    return newInstance(menuItemDaoProvider.get());
  }

  public static MenuRepositoryImpl_Factory create(Provider<MenuItemDao> menuItemDaoProvider) {
    return new MenuRepositoryImpl_Factory(menuItemDaoProvider);
  }

  public static MenuRepositoryImpl newInstance(MenuItemDao menuItemDao) {
    return new MenuRepositoryImpl(menuItemDao);
  }
}
