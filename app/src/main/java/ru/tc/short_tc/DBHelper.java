package ru.tc.short_tc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Создаем таблицу
        db.execSQL(
                "create table dse_table (" +
                "id integer primary key autoincrement," +
                "dse_id text," +
                "dse_name text);");

        //Заполняем данные
        db.execSQL("insert into dse_table (dse_id, dse_name) values ('D_1', 'Втулка');");
        db.execSQL("insert into dse_table (dse_id, dse_name) values ('D_2', 'Опора');");
        db.execSQL("insert into dse_table (dse_id, dse_name) values ('D_3', 'Вал');");
        db.execSQL("insert into dse_table (dse_id, dse_name) values ('SE_1', 'Корпус');");
        db.execSQL("insert into dse_table (dse_id, dse_name) values ('SE_1', 'Кронштейн');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        //db.execSQL("drop table if exists ...");
    }

    public List<DSEModel> getAllDSE() {
        List<DSEModel> res = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor dse_table = db.query("dse_table", null, null, null, null, null, null);

        if (dse_table.moveToFirst()) {
            do {
                DSEModel dse = new DSEModel();
                dse.id = dse_table.getInt(0);
                dse.dse_id = dse_table.getString(1);
                dse.dse_name = dse_table.getString(2);

                res.add(dse);
            } while (dse_table.moveToNext());
        }

        db.close();
        return res;
    }
}
