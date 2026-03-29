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
import java.lang.Long;
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
public final class TableDao_Impl implements TableDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TableEntity> __insertionAdapterOfTableEntity;

  private final EntityDeletionOrUpdateAdapter<TableEntity> __updateAdapterOfTableEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTables;

  public TableDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTableEntity = new EntityInsertionAdapter<TableEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tables` (`tableNo`,`status`,`capacity`,`currentOrderId`,`reservationName`,`reservationTime`,`occupiedSince`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TableEntity entity) {
        statement.bindString(1, entity.getTableNo());
        statement.bindString(2, entity.getStatus());
        statement.bindLong(3, entity.getCapacity());
        if (entity.getCurrentOrderId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCurrentOrderId());
        }
        if (entity.getReservationName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getReservationName());
        }
        if (entity.getReservationTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getReservationTime());
        }
        if (entity.getOccupiedSince() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getOccupiedSince());
        }
      }
    };
    this.__updateAdapterOfTableEntity = new EntityDeletionOrUpdateAdapter<TableEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tables` SET `tableNo` = ?,`status` = ?,`capacity` = ?,`currentOrderId` = ?,`reservationName` = ?,`reservationTime` = ?,`occupiedSince` = ? WHERE `tableNo` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TableEntity entity) {
        statement.bindString(1, entity.getTableNo());
        statement.bindString(2, entity.getStatus());
        statement.bindLong(3, entity.getCapacity());
        if (entity.getCurrentOrderId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCurrentOrderId());
        }
        if (entity.getReservationName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getReservationName());
        }
        if (entity.getReservationTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getReservationTime());
        }
        if (entity.getOccupiedSince() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getOccupiedSince());
        }
        statement.bindString(8, entity.getTableNo());
      }
    };
    this.__preparedStmtOfDeleteAllTables = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tables";
        return _query;
      }
    };
  }

  @Override
  public Object insertTable(final TableEntity table, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTableEntity.insert(table);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTables(final List<TableEntity> tables,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTableEntity.insert(tables);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTable(final TableEntity table, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTableEntity.handle(table);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllTables(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTables.acquire();
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
          __preparedStmtOfDeleteAllTables.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TableEntity>> getAllTables() {
    final String _sql = "SELECT * FROM tables";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tables"}, new Callable<List<TableEntity>>() {
      @Override
      @NonNull
      public List<TableEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTableNo = CursorUtil.getColumnIndexOrThrow(_cursor, "tableNo");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
          final int _cursorIndexOfCurrentOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "currentOrderId");
          final int _cursorIndexOfReservationName = CursorUtil.getColumnIndexOrThrow(_cursor, "reservationName");
          final int _cursorIndexOfReservationTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reservationTime");
          final int _cursorIndexOfOccupiedSince = CursorUtil.getColumnIndexOrThrow(_cursor, "occupiedSince");
          final List<TableEntity> _result = new ArrayList<TableEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TableEntity _item;
            final String _tmpTableNo;
            _tmpTableNo = _cursor.getString(_cursorIndexOfTableNo);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpCapacity;
            _tmpCapacity = _cursor.getInt(_cursorIndexOfCapacity);
            final String _tmpCurrentOrderId;
            if (_cursor.isNull(_cursorIndexOfCurrentOrderId)) {
              _tmpCurrentOrderId = null;
            } else {
              _tmpCurrentOrderId = _cursor.getString(_cursorIndexOfCurrentOrderId);
            }
            final String _tmpReservationName;
            if (_cursor.isNull(_cursorIndexOfReservationName)) {
              _tmpReservationName = null;
            } else {
              _tmpReservationName = _cursor.getString(_cursorIndexOfReservationName);
            }
            final Long _tmpReservationTime;
            if (_cursor.isNull(_cursorIndexOfReservationTime)) {
              _tmpReservationTime = null;
            } else {
              _tmpReservationTime = _cursor.getLong(_cursorIndexOfReservationTime);
            }
            final Long _tmpOccupiedSince;
            if (_cursor.isNull(_cursorIndexOfOccupiedSince)) {
              _tmpOccupiedSince = null;
            } else {
              _tmpOccupiedSince = _cursor.getLong(_cursorIndexOfOccupiedSince);
            }
            _item = new TableEntity(_tmpTableNo,_tmpStatus,_tmpCapacity,_tmpCurrentOrderId,_tmpReservationName,_tmpReservationTime,_tmpOccupiedSince);
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
  public Flow<TableEntity> getTableById(final String tableNo) {
    final String _sql = "SELECT * FROM tables WHERE tableNo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tableNo);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tables"}, new Callable<TableEntity>() {
      @Override
      @Nullable
      public TableEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTableNo = CursorUtil.getColumnIndexOrThrow(_cursor, "tableNo");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "capacity");
          final int _cursorIndexOfCurrentOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "currentOrderId");
          final int _cursorIndexOfReservationName = CursorUtil.getColumnIndexOrThrow(_cursor, "reservationName");
          final int _cursorIndexOfReservationTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reservationTime");
          final int _cursorIndexOfOccupiedSince = CursorUtil.getColumnIndexOrThrow(_cursor, "occupiedSince");
          final TableEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpTableNo;
            _tmpTableNo = _cursor.getString(_cursorIndexOfTableNo);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final int _tmpCapacity;
            _tmpCapacity = _cursor.getInt(_cursorIndexOfCapacity);
            final String _tmpCurrentOrderId;
            if (_cursor.isNull(_cursorIndexOfCurrentOrderId)) {
              _tmpCurrentOrderId = null;
            } else {
              _tmpCurrentOrderId = _cursor.getString(_cursorIndexOfCurrentOrderId);
            }
            final String _tmpReservationName;
            if (_cursor.isNull(_cursorIndexOfReservationName)) {
              _tmpReservationName = null;
            } else {
              _tmpReservationName = _cursor.getString(_cursorIndexOfReservationName);
            }
            final Long _tmpReservationTime;
            if (_cursor.isNull(_cursorIndexOfReservationTime)) {
              _tmpReservationTime = null;
            } else {
              _tmpReservationTime = _cursor.getLong(_cursorIndexOfReservationTime);
            }
            final Long _tmpOccupiedSince;
            if (_cursor.isNull(_cursorIndexOfOccupiedSince)) {
              _tmpOccupiedSince = null;
            } else {
              _tmpOccupiedSince = _cursor.getLong(_cursorIndexOfOccupiedSince);
            }
            _result = new TableEntity(_tmpTableNo,_tmpStatus,_tmpCapacity,_tmpCurrentOrderId,_tmpReservationName,_tmpReservationTime,_tmpOccupiedSince);
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
