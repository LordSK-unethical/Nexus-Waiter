package com.nexus.restaurant;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.nexus.restaurant.data.local.MenuItemDao;
import com.nexus.restaurant.data.local.NexusDatabase;
import com.nexus.restaurant.data.local.OrderDao;
import com.nexus.restaurant.data.local.TableDao;
import com.nexus.restaurant.data.repository.MenuRepositoryImpl;
import com.nexus.restaurant.data.repository.OrderRepositoryImpl;
import com.nexus.restaurant.data.repository.SocketRepositoryImpl;
import com.nexus.restaurant.data.repository.TableRepositoryImpl;
import com.nexus.restaurant.data.socket.SocketManager;
import com.nexus.restaurant.di.DatabaseModule_ProvideDatabaseFactory;
import com.nexus.restaurant.di.DatabaseModule_ProvideMenuItemDaoFactory;
import com.nexus.restaurant.di.DatabaseModule_ProvideOrderDaoFactory;
import com.nexus.restaurant.di.DatabaseModule_ProvideTableDaoFactory;
import com.nexus.restaurant.presentation.MainActivity;
import com.nexus.restaurant.presentation.cashier.CashierViewModel;
import com.nexus.restaurant.presentation.cashier.CashierViewModel_HiltModules;
import com.nexus.restaurant.presentation.kitchen.KitchenViewModel;
import com.nexus.restaurant.presentation.kitchen.KitchenViewModel_HiltModules;
import com.nexus.restaurant.presentation.login.LoginViewModel;
import com.nexus.restaurant.presentation.login.LoginViewModel_HiltModules;
import com.nexus.restaurant.presentation.waiter.WaiterViewModel;
import com.nexus.restaurant.presentation.waiter.WaiterViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerNexusRestaurantApp_HiltComponents_SingletonC {
  private DaggerNexusRestaurantApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public NexusRestaurantApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements NexusRestaurantApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements NexusRestaurantApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements NexusRestaurantApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements NexusRestaurantApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements NexusRestaurantApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements NexusRestaurantApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements NexusRestaurantApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public NexusRestaurantApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends NexusRestaurantApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends NexusRestaurantApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends NexusRestaurantApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends NexusRestaurantApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(4).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_cashier_CashierViewModel, CashierViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_kitchen_KitchenViewModel, KitchenViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_login_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_waiter_WaiterViewModel, WaiterViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nexus_restaurant_presentation_login_LoginViewModel = "com.nexus.restaurant.presentation.login.LoginViewModel";

      static String com_nexus_restaurant_presentation_cashier_CashierViewModel = "com.nexus.restaurant.presentation.cashier.CashierViewModel";

      static String com_nexus_restaurant_presentation_kitchen_KitchenViewModel = "com.nexus.restaurant.presentation.kitchen.KitchenViewModel";

      static String com_nexus_restaurant_presentation_waiter_WaiterViewModel = "com.nexus.restaurant.presentation.waiter.WaiterViewModel";

      @KeepFieldType
      LoginViewModel com_nexus_restaurant_presentation_login_LoginViewModel2;

      @KeepFieldType
      CashierViewModel com_nexus_restaurant_presentation_cashier_CashierViewModel2;

      @KeepFieldType
      KitchenViewModel com_nexus_restaurant_presentation_kitchen_KitchenViewModel2;

      @KeepFieldType
      WaiterViewModel com_nexus_restaurant_presentation_waiter_WaiterViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NexusRestaurantApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CashierViewModel> cashierViewModelProvider;

    private Provider<KitchenViewModel> kitchenViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<WaiterViewModel> waiterViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.cashierViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.kitchenViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.waiterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(4).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_cashier_CashierViewModel, ((Provider) cashierViewModelProvider)).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_kitchen_KitchenViewModel, ((Provider) kitchenViewModelProvider)).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_login_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.com_nexus_restaurant_presentation_waiter_WaiterViewModel, ((Provider) waiterViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nexus_restaurant_presentation_waiter_WaiterViewModel = "com.nexus.restaurant.presentation.waiter.WaiterViewModel";

      static String com_nexus_restaurant_presentation_login_LoginViewModel = "com.nexus.restaurant.presentation.login.LoginViewModel";

      static String com_nexus_restaurant_presentation_cashier_CashierViewModel = "com.nexus.restaurant.presentation.cashier.CashierViewModel";

      static String com_nexus_restaurant_presentation_kitchen_KitchenViewModel = "com.nexus.restaurant.presentation.kitchen.KitchenViewModel";

      @KeepFieldType
      WaiterViewModel com_nexus_restaurant_presentation_waiter_WaiterViewModel2;

      @KeepFieldType
      LoginViewModel com_nexus_restaurant_presentation_login_LoginViewModel2;

      @KeepFieldType
      CashierViewModel com_nexus_restaurant_presentation_cashier_CashierViewModel2;

      @KeepFieldType
      KitchenViewModel com_nexus_restaurant_presentation_kitchen_KitchenViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nexus.restaurant.presentation.cashier.CashierViewModel 
          return (T) new CashierViewModel(singletonCImpl.orderRepositoryImplProvider.get(), singletonCImpl.socketRepositoryImplProvider.get());

          case 1: // com.nexus.restaurant.presentation.kitchen.KitchenViewModel 
          return (T) new KitchenViewModel(singletonCImpl.orderRepositoryImplProvider.get(), singletonCImpl.socketRepositoryImplProvider.get());

          case 2: // com.nexus.restaurant.presentation.login.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.socketRepositoryImplProvider.get());

          case 3: // com.nexus.restaurant.presentation.waiter.WaiterViewModel 
          return (T) new WaiterViewModel(singletonCImpl.tableRepositoryImplProvider.get(), singletonCImpl.orderRepositoryImplProvider.get(), singletonCImpl.socketRepositoryImplProvider.get(), singletonCImpl.menuRepositoryImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends NexusRestaurantApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends NexusRestaurantApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends NexusRestaurantApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<NexusDatabase> provideDatabaseProvider;

    private Provider<OrderDao> provideOrderDaoProvider;

    private Provider<SocketManager> socketManagerProvider;

    private Provider<OrderRepositoryImpl> orderRepositoryImplProvider;

    private Provider<SocketRepositoryImpl> socketRepositoryImplProvider;

    private Provider<TableDao> provideTableDaoProvider;

    private Provider<TableRepositoryImpl> tableRepositoryImplProvider;

    private Provider<MenuItemDao> provideMenuItemDaoProvider;

    private Provider<MenuRepositoryImpl> menuRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<NexusDatabase>(singletonCImpl, 2));
      this.provideOrderDaoProvider = DoubleCheck.provider(new SwitchingProvider<OrderDao>(singletonCImpl, 1));
      this.socketManagerProvider = DoubleCheck.provider(new SwitchingProvider<SocketManager>(singletonCImpl, 3));
      this.orderRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<OrderRepositoryImpl>(singletonCImpl, 0));
      this.socketRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SocketRepositoryImpl>(singletonCImpl, 4));
      this.provideTableDaoProvider = DoubleCheck.provider(new SwitchingProvider<TableDao>(singletonCImpl, 6));
      this.tableRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TableRepositoryImpl>(singletonCImpl, 5));
      this.provideMenuItemDaoProvider = DoubleCheck.provider(new SwitchingProvider<MenuItemDao>(singletonCImpl, 8));
      this.menuRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<MenuRepositoryImpl>(singletonCImpl, 7));
    }

    @Override
    public void injectNexusRestaurantApp(NexusRestaurantApp nexusRestaurantApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nexus.restaurant.data.repository.OrderRepositoryImpl 
          return (T) new OrderRepositoryImpl(singletonCImpl.provideOrderDaoProvider.get(), singletonCImpl.socketManagerProvider.get());

          case 1: // com.nexus.restaurant.data.local.OrderDao 
          return (T) DatabaseModule_ProvideOrderDaoFactory.provideOrderDao(singletonCImpl.provideDatabaseProvider.get());

          case 2: // com.nexus.restaurant.data.local.NexusDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.nexus.restaurant.data.socket.SocketManager 
          return (T) new SocketManager();

          case 4: // com.nexus.restaurant.data.repository.SocketRepositoryImpl 
          return (T) new SocketRepositoryImpl(singletonCImpl.socketManagerProvider.get());

          case 5: // com.nexus.restaurant.data.repository.TableRepositoryImpl 
          return (T) new TableRepositoryImpl(singletonCImpl.provideTableDaoProvider.get(), singletonCImpl.socketManagerProvider.get());

          case 6: // com.nexus.restaurant.data.local.TableDao 
          return (T) DatabaseModule_ProvideTableDaoFactory.provideTableDao(singletonCImpl.provideDatabaseProvider.get());

          case 7: // com.nexus.restaurant.data.repository.MenuRepositoryImpl 
          return (T) new MenuRepositoryImpl(singletonCImpl.provideMenuItemDaoProvider.get());

          case 8: // com.nexus.restaurant.data.local.MenuItemDao 
          return (T) DatabaseModule_ProvideMenuItemDaoFactory.provideMenuItemDao(singletonCImpl.provideDatabaseProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
