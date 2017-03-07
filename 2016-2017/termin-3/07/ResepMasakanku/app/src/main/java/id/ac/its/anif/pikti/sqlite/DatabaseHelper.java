package id.ac.its.anif.pikti.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public List<Resep> getResepByKategori(int kategoriId) {
        List<Resep> reseps = new ArrayList<Resep>();

        String selectQuery = "SELECT * FROM resep  WHERE kategori_id = " + kategoriId;

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

    public List<Resep> getResepByNama(String resepNama) {
        List<Resep> reseps = new ArrayList<Resep>();

        String selectQuery = "SELECT * FROM resep  WHERE resep_nama LIKE '%" + resepNama + "%'" ;

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




    public List<Resep> getAllResepFavorit() {
        List<Resep> reseps = new ArrayList<Resep>();

        String selectQuery = "SELECT r.* FROM resep r, bookmark b WHERE r.resep_id = b.resep_id";

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

    public boolean isFavorit(int resepId) {
        String selectQuery = "SELECT * FROM bookmark WHERE resep_id = " + resepId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount() > 0)
            return true;
        else
            return false;
    }

    public long addFavorit(int resepId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("resep_id", resepId);

        long bookmarkId = db.insert("bookmark", null, values);
        return bookmarkId;
    }

    public int deleteFavorit(int resepId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("bookmark", "resep_id = ?", new String[]{ String.valueOf(resepId)} );
        return result;
    }

    public List<Kategori> getAllKategori() {
        List<Kategori> kategoriList = new ArrayList<>();

        String selectQuery = "SELECT * FROM kategori";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()) {
            do {
                Kategori kategori = new Kategori();
                kategori.setKategoriId(c.getInt(0));
                kategori.setKategoriNama(c.getString(1));
                kategori.setKategoriKeterangan(c.getString(2));
                kategoriList.add(kategori);
            } while (c.moveToNext());
        }
        c.close();

        return kategoriList;
    }

    public long addResep(Resep resep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("resep_nama", resep.getResepNama());
        values.put("resep_intro", resep.getResepIntro());
        values.put("resep_bahan", resep.getResepBahan());
        values.put("resep_instruksi", resep.getResepInstruksi());
        values.put("resep_gambar", resep.getResepGambar());
        values.put("kategori_id", resep.getKategoriId());

        long resepId = db.insert("resep", null, values);
        return resepId;
    }

    public int deleteResep(int resepId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete dari bookmark
        int result = db.delete("bookmark", "resep_id = ?", new String[]{ String.valueOf(resepId)} );

        // Delete dari resep
        result = db.delete("resep", "resep_id = ?", new String[]{String.valueOf(resepId)});

        return result;
    }

    public int updateResep(Resep resep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("resep_nama", resep.getResepNama());
        values.put("resep_intro", resep.getResepIntro());
        values.put("resep_bahan", resep.getResepBahan());
        values.put("resep_instruksi", resep.getResepInstruksi());
        values.put("resep_gambar", resep.getResepGambar());
        values.put("kategori_id", resep.getKategoriId());
        Log.d("Update Resep", "resepId = " + resep.getResepId());

        int result = db.update("resep", values, "resep_id = ?", new String[]{String.valueOf(resep.getResepId())});
        return result;
    }
}
