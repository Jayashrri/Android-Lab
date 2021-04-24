package com.example.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Product.db";

    private static final String TABLE_PRODUCT = "product";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MRP = "mrp";
    private static final String KEY_PRICE = "price";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_MRP + " DOUBLE(10,2),"
                + KEY_PRICE + " DOUBLE(10,2)"
                + ")";

        db.execSQL(CREATE_PRODUCT_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.getId());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_MRP, product.getMrp());
        values.put(KEY_PRICE, product.getPrice());

        long user_id = sqLiteDatabase.insert(TABLE_PRODUCT, null, values);
        sqLiteDatabase.close();
    }

    public void updateProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_MRP, product.getMrp());
        values.put(KEY_PRICE, product.getPrice());

        sqLiteDatabase.update(
                TABLE_PRODUCT,
                values,
                KEY_ID + "=?",
                new String[] { String.valueOf(product.getId()) }
        );

        sqLiteDatabase.close();
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_PRODUCT,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_MRP,
                        KEY_PRICE
                },
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public Product getProduct(int p_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                TABLE_PRODUCT,
                new String[] {
                        KEY_ID,
                        KEY_NAME,
                        KEY_MRP,
                        KEY_PRICE
                },
                KEY_ID + "=?",
                new String[] { String.valueOf(p_id) },
                null,
                null,
                null,
                null
        );

        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );

        return product;
    }
}
