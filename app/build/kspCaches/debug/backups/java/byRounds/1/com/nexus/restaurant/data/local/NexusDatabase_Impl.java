package com.nexus.restaurant.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NexusDatabase_Impl extends NexusDatabase {
  private volatile OrderDao _orderDao;

  private volatile TableDao _tableDao;

  private volatile MenuItemDao _menuItemDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`orderId` TEXT NOT NULL, `tableNo` TEXT NOT NULL, `people` INTEGER NOT NULL, `items` TEXT NOT NULL, `status` TEXT NOT NULL, `notes` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `totalAmount` REAL NOT NULL, PRIMARY KEY(`orderId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tables` (`tableNo` TEXT NOT NULL, `status` TEXT NOT NULL, `capacity` INTEGER NOT NULL, `currentOrderId` TEXT, `reservationName` TEXT, `reservationTime` INTEGER, `occupiedSince` INTEGER, PRIMARY KEY(`tableNo`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `menu_items` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `price` REAL NOT NULL, `category` TEXT NOT NULL, `imageUrl` TEXT, `isAvailable` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '71e19878f7b20f8fe3841d8c101bb196')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `orders`");
        db.execSQL("DROP TABLE IF EXISTS `tables`");
        db.execSQL("DROP TABLE IF EXISTS `menu_items`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsOrders = new HashMap<String, TableInfo.Column>(8);
        _columnsOrders.put("orderId", new TableInfo.Column("orderId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("tableNo", new TableInfo.Column("tableNo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("people", new TableInfo.Column("people", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("items", new TableInfo.Column("items", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("totalAmount", new TableInfo.Column("totalAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrders = new TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders);
        final TableInfo _existingOrders = TableInfo.read(db, "orders");
        if (!_infoOrders.equals(_existingOrders)) {
          return new RoomOpenHelper.ValidationResult(false, "orders(com.nexus.restaurant.data.local.OrderEntity).\n"
                  + " Expected:\n" + _infoOrders + "\n"
                  + " Found:\n" + _existingOrders);
        }
        final HashMap<String, TableInfo.Column> _columnsTables = new HashMap<String, TableInfo.Column>(7);
        _columnsTables.put("tableNo", new TableInfo.Column("tableNo", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("capacity", new TableInfo.Column("capacity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("currentOrderId", new TableInfo.Column("currentOrderId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("reservationName", new TableInfo.Column("reservationName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("reservationTime", new TableInfo.Column("reservationTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTables.put("occupiedSince", new TableInfo.Column("occupiedSince", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTables = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTables = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTables = new TableInfo("tables", _columnsTables, _foreignKeysTables, _indicesTables);
        final TableInfo _existingTables = TableInfo.read(db, "tables");
        if (!_infoTables.equals(_existingTables)) {
          return new RoomOpenHelper.ValidationResult(false, "tables(com.nexus.restaurant.data.local.TableEntity).\n"
                  + " Expected:\n" + _infoTables + "\n"
                  + " Found:\n" + _existingTables);
        }
        final HashMap<String, TableInfo.Column> _columnsMenuItems = new HashMap<String, TableInfo.Column>(7);
        _columnsMenuItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("isAvailable", new TableInfo.Column("isAvailable", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMenuItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMenuItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMenuItems = new TableInfo("menu_items", _columnsMenuItems, _foreignKeysMenuItems, _indicesMenuItems);
        final TableInfo _existingMenuItems = TableInfo.read(db, "menu_items");
        if (!_infoMenuItems.equals(_existingMenuItems)) {
          return new RoomOpenHelper.ValidationResult(false, "menu_items(com.nexus.restaurant.data.local.MenuItemEntity).\n"
                  + " Expected:\n" + _infoMenuItems + "\n"
                  + " Found:\n" + _existingMenuItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "71e19878f7b20f8fe3841d8c101bb196", "da0f13b8410ce2d0424dafe45c2749ae");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "orders","tables","menu_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `orders`");
      _db.execSQL("DELETE FROM `tables`");
      _db.execSQL("DELETE FROM `menu_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TableDao.class, TableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MenuItemDao.class, MenuItemDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }

  @Override
  public TableDao tableDao() {
    if (_tableDao != null) {
      return _tableDao;
    } else {
      synchronized(this) {
        if(_tableDao == null) {
          _tableDao = new TableDao_Impl(this);
        }
        return _tableDao;
      }
    }
  }

  @Override
  public MenuItemDao menuItemDao() {
    if (_menuItemDao != null) {
      return _menuItemDao;
    } else {
      synchronized(this) {
        if(_menuItemDao == null) {
          _menuItemDao = new MenuItemDao_Impl(this);
        }
        return _menuItemDao;
      }
    }
  }
}
