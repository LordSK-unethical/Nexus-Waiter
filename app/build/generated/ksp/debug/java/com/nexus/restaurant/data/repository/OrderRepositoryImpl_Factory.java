package com.nexus.restaurant.data.repository;

import com.nexus.restaurant.data.local.OrderDao;
import com.nexus.restaurant.data.socket.SocketManager;
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
public final class OrderRepositoryImpl_Factory implements Factory<OrderRepositoryImpl> {
  private final Provider<OrderDao> orderDaoProvider;

  private final Provider<SocketManager> socketManagerProvider;

  public OrderRepositoryImpl_Factory(Provider<OrderDao> orderDaoProvider,
      Provider<SocketManager> socketManagerProvider) {
    this.orderDaoProvider = orderDaoProvider;
    this.socketManagerProvider = socketManagerProvider;
  }

  @Override
  public OrderRepositoryImpl get() {
    return newInstance(orderDaoProvider.get(), socketManagerProvider.get());
  }

  public static OrderRepositoryImpl_Factory create(Provider<OrderDao> orderDaoProvider,
      Provider<SocketManager> socketManagerProvider) {
    return new OrderRepositoryImpl_Factory(orderDaoProvider, socketManagerProvider);
  }

  public static OrderRepositoryImpl newInstance(OrderDao orderDao, SocketManager socketManager) {
    return new OrderRepositoryImpl(orderDao, socketManager);
  }
}
