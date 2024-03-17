package edu.jsu.mcis.cs408.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final int CSV_RECORD_LENGTH = 2;
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_MEMOS = "memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";
    public static final String COLUMN_ADDRESS = "address";

    public static final String QUERY_CREATE_MEMOS_TABLE = "CREATE TABLE " + TABLE_MEMOS + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_MEMO + " text)";
    public static final String QUERY_DELETE_MEMOS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEMOS;

    public static final String QUERY_GET_ALL_MEMOS = "SELECT * FROM " + TABLE_MEMOS;
    public static final String QUERY_GET_MEMO = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = ?";

    public static final String QUERY_DELETE_WHERE = "_id=?";

    //private final List<String[]> init;

    public DatabaseHandler(Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        /*
        try {

            int id = R.raw.init;
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(id)));
            StringBuilder s = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                s.append(line).append('\n');
            }

            CSVReader reader = new CSVReaderBuilder(new StringReader(s.toString())).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            init = reader.readAll();

            br.close();
            reader.close();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_MEMOS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(QUERY_DELETE_MEMOS_TABLE);
        onCreate(db);

    }

    public void addMemo(Memo m) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, m.getMemo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();

    }

    public void deleteMemo(int selectedMemo) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, selectedMemo);

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEMOS, QUERY_DELETE_WHERE, new String[]{ String.valueOf(selectedMemo) });
        db.close();

    }

    public Memo getMemo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_GET_MEMO, new String[]{ String.valueOf(id) });
        Memo m = null;
        if (cursor.moveToFirst()) {
            int newId = cursor.getInt(0);
            String memo = cursor.getString(1);
            m = new Memo(newId,memo);
            cursor.close();
        }

        db.close();

        return m;

    }


    public ArrayList<Memo> getAllMemosAsList() {

        ArrayList<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_MEMOS, null);

        if (cursor.moveToFirst()) {

            cursor.moveToFirst();

            do {
                int id = cursor.getInt(0);
                allMemos.add( getMemo(id) );
            }
            while ( cursor.moveToNext() );

        }

        cursor.close();
        db.close();

        return allMemos;

    }

}