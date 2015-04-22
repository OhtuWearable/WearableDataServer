package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.List;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorDatabase extends SQLiteOpenHelper {
    public List<Sensor> sensors;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SensorDB";
    private static final String KEY_ID = "id";
    private static final String KEY_DATA = "data";
    private static final String[] COLUMNS = {KEY_ID, KEY_DATA};

    public SensorDatabase(Context context, List<Sensor> sensors) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.sensors = sensors;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        Log.d("SensorDatabase", "*** Database created ***");
    }

    /**
     * Create tables for all the sensors.
     */
    public void createTables(SQLiteDatabase db) {
        StringBuilder help;
        for (int i = 0; i < sensors.size(); i++) {
            help = new StringBuilder();
            help.append("CREATE TABLE ");
            help.append("\"" + sensors.get(i).getName() + "\"");
            help.append(" ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            help.append(KEY_DATA + " TEXT NOT NULL);");
            db.execSQL(help.toString());
        }
    }

    /**
     * Add sensorUnit and its values to the database.
     * @param unit SensorUnit containing the sensor data
     */
    public void addSensorUnit(SensorUnit unit){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        // Get unit data values and convert them to JSON:
        JSONObject jEntry = JSONConverter.convertToDatabaseJSON(unit);
        //Add JSONObject to database as a string:
        //Log.d("SensorDatabase", "JSON entry as a string: " + jEntry.toString());

        values.put(KEY_DATA, jEntry.toString());
        String tableName = "\"" + unit.getSensorName()+ "\"";

        Log.d("SensorDatabase", "addSensorEvent query: " + tableName);

        //Insert into database:
        db.insert(tableName,
                null,
                values);
        db.close();
    }

    /**
     * Gets all the JSON data associated with the sensor.
     * Goes through all the entries in the database and collects data from each event to a list.
     * @param sensorName Name of the sensor.
     * @return A Linked List containing all the JSONObjects in the database.
     * @throws JSONException
     */
    public List<JSONObject> getAllSensorData(String sensorName) throws JSONException {
        Log.d("SensorDatabase","Getting all data");
        List<JSONObject> objectList = new LinkedList<>();
        String query = "SELECT * FROM " + "\"" + sensorName+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        JSONObject data = null;
        if (cursor.moveToFirst()) {
            Log.d("SensorDatabase", "cursor found");
            do {
                data = new JSONObject();

                String jsonString = cursor.getString(cursor.getColumnIndex("data"));
                JSONObject jsonObject = new JSONObject(jsonString);
                data = (JSONObject) jsonObject.get("data");
                objectList.add(data);
                Log.d("JSONDATA1: ", data.toString());

            } while (cursor.moveToNext());
        }
        db.close();
        return objectList;
    }

    /**
     * Deletes all entries of all sensor tables in the database.
     */
    public void deleteEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < sensors.size(); i++) {
            db.delete("\"" + sensors.get(i).getName() + "\"", null, null);
        }
        db.close();
    }

    /**
     * Empties a single sensor table from the database
     * @param sensorName name of the sensor
     */
    public void emptySensorTable(String sensorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("\"" + sensorName + "\"", null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this can be empty, is not needed in this application uppgrade fiels
        //dropTables();
        //createTables(db);
    }

    /**
     * Restarts the database by dropping all the tables and creating them again.
     */
    public void restart() {
        SQLiteDatabase db = this.getWritableDatabase();
        dropTables();
        createTables(db);
    }
    /**
     * Drops all the tables in the database.
     */
    public void dropTables() {
        Log.d("SensorDatabase", "Dropping all tables");
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < sensors.size(); i++) {
            //db.delete("\"" + sensors.get(i).getName() + "\"", null, null);
            db.execSQL("DROP TABLE IF EXISTS " + "\"" + sensors.get(i).getName() + "\"");
        }
    }
}



