package com.unitarcafe.hegaa.unitarcafe.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper{

    private static final String DB_NAME = "FoodOrderAppDB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //Method foe get Cart List
    public List<Order> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null,null, null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()){
            do{
               result.add(new Order(
                       c.getString(c.getColumnIndex("ProductName")),
                       c.getString(c.getColumnIndex("Quantity")),
                       c.getDouble(c.getColumnIndex("Price")),
                       c.getInt(c.getColumnIndex("Discount"))
                       ));
            }while (c.moveToNext());
        }
        return result;
    }

    //Method for add value to cart
    public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductName, Quantity, Price, Discount) VALUES ('%s', '%s', '%s', '%s');",
                order.getProduName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }

    //
    //Method for Clear value from cart
    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
