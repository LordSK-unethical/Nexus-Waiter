package com.nexus.restaurant.data.repository;

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
public final class SocketRepositoryImpl_Factory implements Factory<SocketRepositoryImpl> {
  private final Provider<SocketManager> socketManagerProvider;

  public SocketRepositoryImpl_Factory(Provider<SocketManager> socketManagerProvider) {
    this.socketManagerProvider = socketManagerProvider;
  }

  @Override
  public SocketRepositoryImpl get() {
    return newInstance(socketManagerProvider.get());
  }

  public static SocketRepositoryImpl_Factory create(Provider<SocketManager> socketManagerProvider) {
    return new SocketRepositoryImpl_Factory(socketManagerProvider);
  }

  public static SocketRepositoryImpl newInstance(SocketManager socketManager) {
    return new SocketRepositoryImpl(socketManager);
  }
}
