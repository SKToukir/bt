package bdtube.vumobile.com.bdtube.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toukirul on 30/1/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "offline_video";
    private static final String TABLE_OFFLINE = "tbloffline";

    private static final String key_id = "_id";
    private static final String KEY_IMAGE_FILE_NAME = "image_file_name";
    private static final String KEY_VIDEO_FILE_NAME = "video_file_name";
    private static final String KEY_IMAGE_FILE_PATH = "image_file_path";
    private static final String KEY_VIDEO_FILE_PATH = "video_file_path";
    private static final String KEY_VIDEO_TOTAL_LIKES = "video_total_likes";
    private static final String KEY_VIDEO_TOTAL_VIEWS = "video_total_views";
    private static final String KEY_VIDEO_GENRE = "genre";
    private static final String KEY_VIDEO_INFO = "info";
    private static final String KEY_VIDEO_DURATION = "duration";
    private static final String KEY_VIDEO_TITLE = "video_title";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BARCODE_TABLE = "CREATE TABLE " + TABLE_OFFLINE + "("
                + key_id + " INTEGER PRIMARY KEY,"
                + KEY_VIDEO_TITLE + " TEXT,"
                + KEY_VIDEO_TOTAL_LIKES + " TEXT,"
                + KEY_VIDEO_TOTAL_VIEWS + " TEXT,"
                + KEY_VIDEO_GENRE + " TEXT,"
                + KEY_VIDEO_INFO + " TEXT,"
                + KEY_VIDEO_DURATION + " TEXT,"
                + KEY_IMAGE_FILE_NAME + " TEXT,"
                + KEY_VIDEO_FILE_NAME + " TEXT,"
                + KEY_VIDEO_FILE_PATH + " TEXT,"
                + KEY_IMAGE_FILE_PATH + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_BARCODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_OFFLINE);
        onCreate(sqLiteDatabase);
    }

    public void addItem(DbModelClass modelClass){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VIDEO_TITLE, modelClass.getVideoTitle());
        values.put(KEY_VIDEO_TOTAL_LIKES, modelClass.getTotal_likes());
        values.put(KEY_VIDEO_TOTAL_VIEWS, modelClass.getTotal_views());
        values.put(KEY_VIDEO_GENRE, modelClass.getGenre());
        values.put(KEY_VIDEO_INFO, modelClass.getInfo());
        values.put(KEY_VIDEO_DURATION, modelClass.getDuration());
        values.put(KEY_IMAGE_FILE_NAME, modelClass.getImageFileName());
        values.put(KEY_VIDEO_FILE_NAME, modelClass.getVideoFileName());
        values.put(KEY_VIDEO_FILE_PATH, modelClass.getVideoFilePath());
        values.put(KEY_IMAGE_FILE_PATH, modelClass.getImageFilePath());

        db.insert(TABLE_OFFLINE ,null ,values);
        db.close();
    }

    public DbModelClass getItem(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OFFLINE, new String[]{key_id,KEY_VIDEO_TITLE, KEY_VIDEO_TOTAL_LIKES, KEY_VIDEO_TOTAL_VIEWS, KEY_VIDEO_GENRE, KEY_VIDEO_INFO, KEY_VIDEO_DURATION, KEY_VIDEO_FILE_NAME, KEY_VIDEO_FILE_NAME, KEY_VIDEO_FILE_PATH, KEY_IMAGE_FILE_PATH}, key_id + "=?"
                ,new String[]{String.valueOf(id)},null, null, null, null);

        if (cursor!=null){
            cursor.moveToFirst();
        }

        DbModelClass dbModelClass = new DbModelClass();
        dbModelClass.setId(Integer.parseInt(cursor.getString(0)));
        dbModelClass.setVideoTitle(cursor.getString(1));
        dbModelClass.setTotal_likes(cursor.getString(2));
        dbModelClass.setTotal_views(cursor.getString(3));
        dbModelClass.setGenre(cursor.getString(4));
        dbModelClass.setInfo(cursor.getString(5));
        dbModelClass.setDuration(cursor.getString(6));
        dbModelClass.setImageFileName(cursor.getString(7));
        dbModelClass.setVideoFileName(cursor.getString(8));
        dbModelClass.setVideoFilePath(cursor.getString(9));
        dbModelClass.setImageFilePath(cursor.getString(10));

        return dbModelClass;
    }

    // Getting All Contacts
    public List<DbModelClass> getAllContacts() {
        List<DbModelClass> contactList = new ArrayList<DbModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OFFLINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DbModelClass dbModelClass = new DbModelClass();
                dbModelClass.setId(Integer.parseInt(cursor.getString(0)));
                dbModelClass.setVideoTitle(cursor.getString(1));
                dbModelClass.setTotal_likes(cursor.getString(2));
                dbModelClass.setTotal_views(cursor.getString(3));
                dbModelClass.setGenre(cursor.getString(4));
                dbModelClass.setInfo(cursor.getString(5));
                dbModelClass.setDuration(cursor.getString(6));
                dbModelClass.setImageFileName(cursor.getString(7));
                dbModelClass.setVideoFileName(cursor.getString(8));
                dbModelClass.setVideoFilePath(cursor.getString(9));
                dbModelClass.setImageFilePath(cursor.getString(10));
                // Adding contact to list
                contactList.add(dbModelClass);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Deleting single contact
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OFFLINE, key_id + "=?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OFFLINE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int c = cursor.getCount();
        cursor.close();

        // return count
        return c;
    }
}
