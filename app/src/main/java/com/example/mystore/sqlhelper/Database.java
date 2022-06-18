package com.example.mystore.sqlhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "DB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //USER
        String USERS = "CREATE TABLE USERS ( " +
                " UID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " EMAIL TEXT ," +
                " PASSWORD TEXT," +
                " NAME TEXT )";
        db.execSQL(USERS);
        //STORES
        String STORES = "CREATE TABLE STORES (" +
                " UID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " EMAIL TEXT," +
                " ADDRESS TEXT," +
                " PHONE TEXT)";
        db.execSQL(STORES);
        //PRODUCTS
        String PRODUCTCS = "CREATE TABLE PRODUCTS (" +
                " UID INTEGER REFERENCES USERS(UID) ," +
                " KEYPRODUCT INTERGER PRIMARY KEY AUTOINCREMENT ," +
                " PDNAME TEXT," +
                " PDPRICE TEXT," +
                " PDDECRIPTION TEXT," +
                " PDIMAGE TEXT)";
        db.execSQL(PRODUCTCS);

        String CUSTOMER = "CREATE TABLE CUSTOMER (" +
                " UID INTERGER ," +
                " KEYCUSTOMER INTERGER PRIMARY KEY AUTOINCREMENT ," +
                " CUSTOMERNAME TEXT ," +
                " CUSTOMERPHONE TEXT ," +
                " RULE TEXT)";
        db.execSQL(CUSTOMER);
        String ORDERS = "CREATE TABLE ORDERS (" +
                " UID INTERGER REFERENCES USERS(UID) ," +
                " KEYORDER PRIMARY KEY AUTOINCREMENT ," +
                " CUSTOMERKEY INTERGER ," +
                " DATE DATE," +
                " DISCOUNT INTERGER, " +
                " ORDERNOTE TEXT, " +
                " STATUS TEXT)";
        db.execSQL(ORDERS);

        String ORDERDETAILS = "CREATE TABLE ORDERDETAILS(" +
                " UID INTERGER," +
                " KEYORDER REFERENCES ORDERS(KEYORDER)," +
                " KEYORDERDETAILS PRIMARY KEY AUTOINCREMENT," +
                " KEYPRODUCTDETAIL INTERGER," +
                " NUMBER INTERGER," +
                " PRODUCTKEY INTERGER)";
        db.execSQL(ORDERDETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
