package com.nexus.restaurant.data.repository;

import com.nexus.restaurant.data.local.TableDao;
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
public final class TableRepositoryImpl_Factory implements Factory<TableRepositoryImpl> {
  private final Provider<TableDao> tableDaoProvider;

  private final Provider<SocketManager> socketManagerProvider;

  public TableRepositoryImpl_Factory(Provider<TableDao> tableDaoProvider,
      Provider<SocketManager> socketManagerProvider) {
    this.tableDaoProvider = tableDaoProvider;
    this.socketManagerProvider = socketManagerProvider;
  }

  @Override
  public TableRepositoryImpl get() {
    return newInstance(tableDaoProvider.get(), socketManagerProvider.get());
  }

  public static TableRepositoryImpl_Factory create(Provider<TableDao> tableDaoProvider,
      Provider<SocketManager> socketManagerProvider) {
    return new TableRepositoryImpl_Factory(tableDaoProvider, socketManagerProvider);
  }

  public static TableRepositoryImpl newInstance(TableDao tableDao, SocketManager socketManager) {
    return new TableRepositoryImpl(tableDao, socketManager);
  }
}
