package com.nexus.restaurant.presentation.kitchen;

import com.nexus.restaurant.domain.repository.OrderRepository;
import com.nexus.restaurant.domain.repository.SocketRepository;
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
public final class KitchenViewModel_Factory implements Factory<KitchenViewModel> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  private final Provider<SocketRepository> socketRepositoryProvider;

  public KitchenViewModel_Factory(Provider<OrderRepository> orderRepositoryProvider,
      Provider<SocketRepository> socketRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
    this.socketRepositoryProvider = socketRepositoryProvider;
  }

  @Override
  public KitchenViewModel get() {
    return newInstance(orderRepositoryProvider.get(), socketRepositoryProvider.get());
  }

  public static KitchenViewModel_Factory create(Provider<OrderRepository> orderRepositoryProvider,
      Provider<SocketRepository> socketRepositoryProvider) {
    return new KitchenViewModel_Factory(orderRepositoryProvider, socketRepositoryProvider);
  }

  public static KitchenViewModel newInstance(OrderRepository orderRepository,
      SocketRepository socketRepository) {
    return new KitchenViewModel(orderRepository, socketRepository);
  }
}
