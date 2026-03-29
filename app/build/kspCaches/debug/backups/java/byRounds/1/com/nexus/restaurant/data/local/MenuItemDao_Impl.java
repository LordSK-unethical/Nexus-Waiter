package com.nexus.restaurant.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MenuItemDao_Impl implements MenuItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MenuItemEntity> __insertionAdapterOfMenuItemEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllMenuItems;

  public MenuItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMenuItemEntity = new EntityInsertionAdapter<MenuItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `menu_items` (`id`,`name`,`description`,`price`,`category`,`imageUrl`,`isAvailable`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MenuItemEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindDouble(4, entity.getPrice());
        statement.bindString(5, entity.getCategory());
        if (entity.getImageUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getImageUrl());
        }
        final int _tmp = entity.isAvailable() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfDeleteAllMenuItems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM menu_items";
        return _query;
      }
    };
  }

  @Override
  public Object insertMenuItems(final List<MenuItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMenuItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllMenuItems(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllMenuItems.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllMenuItems.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MenuItemEntity>> getMenuItemsByCategory(final String category) {
    final String _sql = "SELECT * FROM menu_items WHERE category = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"menu_items"}, new Callable<List<MenuItemEntity>>() {
      @Override
      @NonNull
      public List<MenuItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailable");
          final List<MenuItemEntity> _result = new ArrayList<MenuItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MenuItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsAvailable;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAvailable);
            _tmpIsAvailable = _tmp != 0;
            _item = new MenuItemEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpCategory,_tmpImageUrl,_tmpIsAvailable);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<MenuItemEntity>> getAllMenuItems() {
    final String _sql = "SELECT * FROM menu_items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"menu_items"}, new Callable<List<MenuItemEntity>>() {
      @Override
      @NonNull
      public List<MenuItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailable");
          final List<MenuItemEntity> _result = new ArrayList<MenuItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MenuItemEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsAvailable;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAvailable);
            _tmpIsAvailable = _tmp != 0;
            _item = new MenuItemEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpCategory,_tmpImageUrl,_tmpIsAvailable);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
