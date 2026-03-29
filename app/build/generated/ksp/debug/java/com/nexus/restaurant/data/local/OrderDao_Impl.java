package com.nexus.restaurant.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
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
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderEntity> __insertionAdapterOfOrderEntity;

  private final EntityDeletionOrUpdateAdapter<OrderEntity> __updateAdapterOfOrderEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOrder;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllOrders;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderEntity = new EntityInsertionAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`orderId`,`tableNo`,`people`,`items`,`status`,`notes`,`timestamp`,`totalAmount`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderEntity entity) {
        statement.bindString(1, entity.getOrderId());
        statement.bindString(2, entity.getTableNo());
        statement.bindLong(3, entity.getPeople());
        statement.bindString(4, entity.getItems());
        statement.bindString(5, entity.getStatus());
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getTimestamp());
        statement.bindDouble(8, entity.getTotalAmount());
      }
    };
    this.__updateAdapterOfOrderEntity = new EntityDeletionOrUpdateAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `orders` SET `orderId` = ?,`tableNo` = ?,`people` = ?,`items` = ?,`status` = ?,`notes` = ?,`timestamp` = ?,`totalAmount` = ? WHERE `orderId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderEntity entity) {
        statement.bindString(1, entity.getOrderId());
        statement.bindString(2, entity.getTableNo());
        statement.bindLong(3, entity.getPeople());
        statement.bindString(4, entity.getItems());
        statement.bindString(5, entity.getStatus());
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getTimestamp());
        statement.bindDouble(8, entity.getTotalAmount());
        statement.bindString(9, entity.getOrderId());
      }
    };
    this.__preparedStmtOfDeleteOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orders WHERE orderId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllOrders = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orders";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final OrderEntity order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrders(final List<OrderEntity> orders,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(orders);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrder(final OrderEntity order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfOrderEntity.handle(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOrder(final String orderId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOrder.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, orderId);
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
          __preparedStmtOfDeleteOrder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllOrders(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllOrders.acquire();
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
          __preparedStmtOfDeleteAllOrders.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<OrderEntity>> getAllOrders() {
    final String _sql = "SELECT * FROM orders ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfTableNo = CursorUtil.getColumnIndexOrThrow(_cursor, "tableNo");
          final int _cursorIndexOfPeople = CursorUtil.getColumnIndexOrThrow(_cursor, "people");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpOrderId;
            _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            final String _tmpTableNo;
            _tmpTableNo = _cursor.getString(_cursorIndexOfTableNo);
            final int _tmpPeople;
            _tmpPeople = _cursor.getInt(_cursorIndexOfPeople);
            final String _tmpItems;
            _tmpItems = _cursor.getString(_cursorIndexOfItems);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            _item = new OrderEntity(_tmpOrderId,_tmpTableNo,_tmpPeople,_tmpItems,_tmpStatus,_tmpNotes,_tmpTimestamp,_tmpTotalAmount);
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
  public Flow<List<OrderEntity>> getOrdersByStatus(final String status) {
    final String _sql = "SELECT * FROM orders WHERE status = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfTableNo = CursorUtil.getColumnIndexOrThrow(_cursor, "tableNo");
          final int _cursorIndexOfPeople = CursorUtil.getColumnIndexOrThrow(_cursor, "people");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpOrderId;
            _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            final String _tmpTableNo;
            _tmpTableNo = _cursor.getString(_cursorIndexOfTableNo);
            final int _tmpPeople;
            _tmpPeople = _cursor.getInt(_cursorIndexOfPeople);
            final String _tmpItems;
            _tmpItems = _cursor.getString(_cursorIndexOfItems);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            _item = new OrderEntity(_tmpOrderId,_tmpTableNo,_tmpPeople,_tmpItems,_tmpStatus,_tmpNotes,_tmpTimestamp,_tmpTotalAmount);
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
  public Flow<OrderEntity> getOrderById(final String orderId) {
    final String _sql = "SELECT * FROM orders WHERE orderId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, orderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<OrderEntity>() {
      @Override
      @Nullable
      public OrderEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfTableNo = CursorUtil.getColumnIndexOrThrow(_cursor, "tableNo");
          final int _cursorIndexOfPeople = CursorUtil.getColumnIndexOrThrow(_cursor, "people");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final OrderEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpOrderId;
            _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            final String _tmpTableNo;
            _tmpTableNo = _cursor.getString(_cursorIndexOfTableNo);
            final int _tmpPeople;
            _tmpPeople = _cursor.getInt(_cursorIndexOfPeople);
            final String _tmpItems;
            _tmpItems = _cursor.getString(_cursorIndexOfItems);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            _result = new OrderEntity(_tmpOrderId,_tmpTableNo,_tmpPeople,_tmpItems,_tmpStatus,_tmpNotes,_tmpTimestamp,_tmpTotalAmount);
          } else {
            _result = null;
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
