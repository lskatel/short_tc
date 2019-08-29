package ru.tc.short_tc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aName";
    private static final File ADATA = new File(
            Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS),
            "ADATA"
    );
    static final int EXPORT_CSV = 1;
    static final int UPLOAD_CSV = 2;
    static final int EXPORT_DONE = 3;
    static final int EXPORT_ERROR = -1;
    static boolean isNeedToReloadPodr = true;
    static boolean isNeedToReloadDolg = true;
    private static boolean needToReloadOper;
    private static boolean needToReloadFact;
    private String ДОБАВЛЕНО_ВРУЧНУЮ;
    private String НЕ_ЗАПОЛНЕНО;
    int uploadState = 0;

    static boolean isNeedToReloadOper()
    {
        return needToReloadOper;
    }

    static boolean isNeedToReloadFact()
    {
        return needToReloadFact;
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ДОБАВЛЕНО_ВРУЧНУЮ = context.getString(R.string.добавлено_вручную);
        this.НЕ_ЗАПОЛНЕНО = context.getString(R.string.не_заполнено);
        Integer checked = 0;
        Integer created = 0;
        try	{ ADATA.mkdirs();	} catch (...) { }
        try { checkAH(); checked++; } catch (...) createAH();
    }

    public void setNeedToReloadOper(boolean needToReloadOper)
    {
        ADatabase.needToReloadOper = needToReloadOper;
    }

    public void setNeedToReloadFact(boolean needToReloadFact)
    {
        ADatabase.needToReloadFact = needToReloadFact;
    }

    private void checkAH()
    {
        SQLiteDatabase db = getReadableDatabase();
        db.query("AH", new String[]{"ID"}, null, null, null, null, null);
    }

    private void createAH()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table AH (" +
                        "id integer primary key autoincrement, " +
                        "date_time text, " +
                        "worker_nik text, " +
                        "constraint date_time_n_head_unique unique (date_time, n_head)" +
                        ");"
        );
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("drop table if exists ...");
    }

    int addNewStructure(String name) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        db.insert("PODR", null, cv);

        db.close();

        Cursor c = getReadableDatabase().query("PODR",
                new String[]{"ID"},
                "NAME = ?",
                new String[]{name},
                null, null, null);

        if (c.moveToFirst())
        {
            return getIDandCloseCursor(c);
        } else
        {
            throw new Exception("Error creating op = " + name);
        }
    }

    void freeDatabase()
    {
        try (SQLiteDatabase db = this.getWritableDatabase())
        {
            delete...(db);

        } catch (Exception e)
        {
            Log.e("Error:", e.toString());
        }
    }


    private List<String> getStringListFromTable(String table, String[] fields)
    {
        List<String> res = new ArrayList<>();
        Cursor c = this.getReadableDatabase().query(table,
                fields,
                null, null, null, null, null);

        if (c.moveToFirst())
        {
            do
            {
                res.add(c.getString(1));
            } while (c.moveToNext());
            c.close();
        } else
        {
            c.close();
            res.add("");
        }
        return res;
    }

    private String getFieldsFromTableByID(String[] fieldNames, String tableName, String ID)
    {
        Cursor c = this.getReadableDatabase().query(tableName,
                fieldNames,
                "ID = ?",
                new String[]{ID},
                null, null, null);

        if (c.moveToFirst())
        {
            String a = "";
            int n = c.getColumnCount();
            for (int i = 0; i < n; i++)
            {
                String x = c.getString(i);
                if (x == null)
                {
                    a += " ";
                } else
                {
                    a += x;
                }
            }
            a = a.trim();
            c.close();
            return a;
        } else
        {
            return НЕ_ЗАПОЛНЕНО;
        }
    }

    int addNewAHead(String Adate,
                    int n_head,
                    int id_xpoh,
                    int id_podr,
                    int id_dolg,
                    String sm_nom,
                    String sm_dlit,
                    String rab_gr_a,
                    String rab_gr_b,
                    String num_men,
                    String worker_nik) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date_time", Adate);
        cv.put("n_head", n_head);
        cv.put("id_xpoh", id_xpoh);
        cv.put("id_podr", id_podr);
        cv.put("id_dolg", id_dolg);
        cv.put("sm_nom", sm_nom);
        cv.put("sm_dlit", sm_dlit);
        cv.put("rab_gr_a", rab_gr_a);
        cv.put("rab_gr_b", rab_gr_b);
        cv.put("num_men", num_men);
        cv.put("worker_nik", worker_nik);
        db.insert("AH", null, cv);
        db.close();

        Cursor c = this.getReadableDatabase().query("AH",
                new String[]{"ID"},
                "DATE_TIME = ? AND N_HEAD = ?",
                new String[]{Adate, String.valueOf(n_head)},
                null, null, null);

        if (c.moveToFirst())
        {
            return getIDandCloseCursor(c);
        } else
        {
            throw new Exception("Error creating Ahead = " + Adate);
        }
    }
}
