package id.ac.its.anif.pikti.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul Munif on 3/5/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/id.ac.its.anif.pikti.resepmasakanku/databases/";
    private static String DB_NAME = "resep.db";
    private SQLiteDatabase myDatabase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if(dbExist) {

        } else {
            this.getReadableDatabase();
            this.close();
            try {
                this.close();
                copyDatabase();
            } catch(IOException e) {
                //throw new Error("Error copying database");
                throw e;
            }
        }
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[8192];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDatabase() {
        File databasePath = myContext.getDatabasePath(DB_NAME);
        return databasePath.exists();
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Resep> getAllResep() {
        List<Resep> reseps = new ArrayList<Resep>();

        String selectQuery = "SELECT * FROM resep";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()) {
            do {
                Resep resep = new Resep();
                resep.setResepId(c.getInt(0));
                resep.setResepNama(c.getString(1));
                resep.setResepIntro(c.getString(2));
                resep.setResepBahan(c.getString(3));
                resep.setResepInstruksi(c.getString(4));
                resep.setResepGambar(c.getString(5));
                resep.setKategoriId(c.getInt(6));

                reseps.add(resep);

            } while (c.moveToNext());
        }
        c.close();

        return reseps;
    }
}
