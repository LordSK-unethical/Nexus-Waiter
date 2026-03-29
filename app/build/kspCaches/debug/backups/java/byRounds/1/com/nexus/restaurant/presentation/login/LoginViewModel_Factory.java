package com.nexus.restaurant.presentation.login;

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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<SocketRepository> socketRepositoryProvider;

  public LoginViewModel_Factory(Provider<SocketRepository> socketRepositoryProvider) {
    this.socketRepositoryProvider = socketRepositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(socketRepositoryProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<SocketRepository> socketRepositoryProvider) {
    return new LoginViewModel_Factory(socketRepositoryProvider);
  }

  public static LoginViewModel newInstance(SocketRepository socketRepository) {
    return new LoginViewModel(socketRepository);
  }
}
