package com.nexus.restaurant.presentation.waiter;

import com.nexus.restaurant.domain.repository.MenuRepository;
import com.nexus.restaurant.domain.repository.OrderRepository;
import com.nexus.restaurant.domain.repository.SocketRepository;
import com.nexus.restaurant.domain.repository.TableRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class WaiterViewModel_Factory implements Factory<WaiterViewModel> {
  private final Provider<TableRepository> tableRepositoryProvider;

  private final Provider<OrderRepository> orderRepositoryProvider;

  private final Provider<SocketRepository> socketRepositoryProvider;

  private final Provider<MenuRepository> menuRepositoryProvider;

  public WaiterViewModel_Factory(Provider<TableRepository> tableRepositoryProvider,
      Provider<OrderRepository> orderRepositoryProvider,
      Provider<SocketRepository> socketRepositoryProvider,
      Provider<MenuRepository> menuRepositoryProvider) {
    this.tableRepositoryProvider = tableRepositoryProvider;
    this.orderRepositoryProvider = orderRepositoryProvider;
    this.socketRepositoryProvider = socketRepositoryProvider;
    this.menuRepositoryProvider = menuRepositoryProvider;
  }

  @Override
  public WaiterViewModel get() {
    return newInstance(tableRepositoryProvider.get(), orderRepositoryProvider.get(), socketRepositoryProvider.get(), menuRepositoryProvider.get());
  }

  public static WaiterViewModel_Factory create(Provider<TableRepository> tableRepositoryProvider,
      Provider<OrderRepository> orderRepositoryProvider,
      Provider<SocketRepository> socketRepositoryProvider,
      Provider<MenuRepository> menuRepositoryProvider) {
    return new WaiterViewModel_Factory(tableRepositoryProvider, orderRepositoryProvider, socketRepositoryProvider, menuRepositoryProvider);
  }

  public static WaiterViewModel newInstance(TableRepository tableRepository,
      OrderRepository orderRepository, SocketRepository socketRepository,
      MenuRepository menuRepository) {
    return new WaiterViewModel(tableRepository, orderRepository, socketRepository, menuRepository);
  }
}
