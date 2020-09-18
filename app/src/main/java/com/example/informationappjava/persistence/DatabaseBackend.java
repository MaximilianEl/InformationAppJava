package com.example.informationappjava.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseBackend extends SQLiteOpenHelper {

    private static final String LOGTAG = "DatabaseBackend";
    private static DatabaseBackend instance = null;
    private static final String DATABASE_NAME = "roosterPlus";
    private static final int DATABASE_VERSION = 1;

    private DatabaseBackend(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
