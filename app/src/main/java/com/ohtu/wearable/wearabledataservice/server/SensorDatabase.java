package com.ohtu.wearable.wearabledataservice.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.System;import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.hardware.SensorEvent;
import android.util.Log;

import com.google.android.gms.analytics.ecommerce.Product;
import com.ohtu.wearable.wearabledataservice.sensors.JSONConverter;
import com.ohtu.wearable.wearabledataservice.sensors.SensorUnit;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorDatabase extends SQLiteOpenHelper {
    public List<Sensor> sensors;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SensorDB";
    private static final String KEY_ID = "id";
    public static final String COLUMN_TIME_STAMP = "timestamp";

    private static final String KEY_DATA = "data";

    private static final String[] COLUMNS = {KEY_ID, COLUMN_TIME_STAMP, KEY_DATA};

    private SensorsHandler sensorsHandler;

    public SensorDatabase(Context context, List<Sensor> sensors) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.sensorsHandler = sensorsHandler;
        //Log.d("SensorDatabase", "****created");
        this.sensors = sensors;
    }

    //note: if database already exists, onCreate will not be called again!
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SensorDatabase", "********************************************************");

        // TODO: Checking is table already in Database
        createTables(db);

        Log.d("SensorDatabase", "**************************** Database created");
    }

    /**
     * Create tables for all the sensors in the database.
     * @param db Database where the sensors are created.
     */
    public void createTables(SQLiteDatabase db) {
        StringBuilder help;
        for (int i = 0; i < sensors.size(); i++) {
            help = new StringBuilder();
            help.append("CREATE TABLE ");
            help.append("\"" + sensors.get(i).getName() + "\"");
            help.append(" ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            help.append(COLUMN_TIME_STAMP + " INTEGER, ");
            help.append(KEY_DATA + " TEXT NOT NULL);");

            Log.d("SensorDatabase query" , help.toString());

            db.execSQL(help.toString());
        }

    }

    /**
     * Drops all the tables in the database.
     * @param db Database where the sensors are deleted.
     */
    public void dropTables(SQLiteDatabase db) {
        StringBuilder help;
        for (int i = 0; i < sensors.size(); i++) {
            db.delete("\"" + sensors.get(i).getName() + "\"", null, null);
        }
        db.close();
    }

    /**
     * Add sensorUnit and all it's values to the database
     * @param unit SensorUnit containing the sensor data
     */
    public void addSensorUnit(SensorUnit unit){
        //for logging
        Log.d("addSensor ", unit.getSensorName());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //get unit data values and convert them to JSON:
        JSONObject jEntry = JSONConverter.convertToDatabaseJSON(unit);
        //add jsonObject to database as a string:
        values.put(KEY_DATA, jEntry.toString());
        Log.d("SensorDatabase", "JSON entry as a string: " + jEntry.toString());

        //get table name:
        String help = "TABLE_" + "\"" + unit.getSensorName()+ "\"";
        Log.d("MySQLiteHelper", "addSensorEvent query: " + help);

        // 3. insert
        db.insert(help, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    /** Get sensor data as an JSONObject */
    public JSONObject getJSONSensorData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String help = "TABLE_" + "\"" + sensors.get(id).getName() + "\"";
        Cursor cursor = db.query(help,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        SensorUnit sensorUnit = new SensorUnit();
        String jsonString = cursor.getString(2);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String name = (String) jsonObject.get("sensor");
            JSONObject data = (JSONObject) jsonObject.get("data");

            return data;
        } catch(JSONException e) {

        }
        return null;
    }

    /*
    //does not work currently
    public void getAllSensorData(Sensor sensor) {
        SQLiteDatabase db = this.getReadableDatabase();

        String TABLE_SENSOR = "TABLE_" + "\"" + sensor.getName() + "\"";

        String sortOrder =
                COLUMN_TIME_STAMP + " DESC";

        Cursor c = db.query(
                TABLE_SENSOR,  // The table to query
                COLUMNS,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        c.moveToFirst();

    }
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this can be empty, is not needed in this application uppgrade fiels
        dropTables(db);
        createTables(db);
    }
}



