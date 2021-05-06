package com.newage.cozastore.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.newage.cozastore.api.models.Customer
import com.newage.cozastore.api.models.product.Item


class DatabaseHelper(context: Context): SQLiteOpenHelper(context,databaseName,null,databaseVersion) {
    companion object {

        private const val databaseVersion = 1
        private const val databaseName = "CozaStore"

        private const val customerTable = "customer"
        private const val wishTable = "wish"

        //Customer table column
        private const val id:String = "id"
        private const val first_name: String = "first_name"
        private const val last_name: String = "last_name"
        private const val email_address: String = "email_address"
        private const val password:String = "password"
        private const val phone_number: String = "phone_number"
        private const val address:String = "address"

        //Create customer table
        private const val createCustomerTable:String = "create table $customerTable($id integer primary key, " +
                "$first_name text," +
                "$last_name text, " +
                "$email_address text," +
                "$password text," +
                "$phone_number text," +
                "$address text)"

        //Wish table column
        private const val category_id: String = "category_id"
        private const val brand_id: String = "brand_id"
        private const val user_id: String = "user_id"
        private const val product_name:String = "product_name"
        private const val product_price: String = "product_price"
        private const val product_quantity:String = "product_quantity"
        private const val short_description:String = "short_description"
        private const val long_description: String = "long_description"
        private const val product_image: String = "product_image"
        private const val product_image_mid: String = "product_image_mid"
        private const val product_image_low: String = "product_image_low"
        private const val publication_status:String = "publication_status"
        private const val created_at: String = "created_at"
        private const val updated_at:String = "updated_at"
        private const val category_name: String = "category_name"
        private const val brand_name:String = "brand_name"

        //Create wish table
        private const val createWishTable:String = "create table $wishTable($id integer primary key, " +
                "$category_id int," +
                "$brand_id int, " +
                "$user_id int," +
                "$product_name text," +
                "$product_price float," +
                "$product_quantity int, " +
                "$short_description text," +
                "$long_description text, " +
                "$product_image text," +
                "$product_image_mid text," +
                "$product_image_low text," +
                "$publication_status int, " +
                "$created_at text," +
                "$updated_at text, " +
                "$category_name text," +
                "$brand_name text)"



    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(createCustomerTable)
        db?.execSQL(createWishTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("drop table if exists $customerTable")
        db?.execSQL("drop table if exists $wishTable")

        onCreate(db)
    }

    //Customer table
    fun saveCustomer(customer: Customer?):Long{
        val db = this.writableDatabase
        val values = ContentValues()

        if (customer != null) {
            values.put(id, customer.id)
            values.put(first_name, customer.first_name)
            values.put(last_name, customer.last_name)
            values.put(email_address, customer.email_address)
            values.put(password, customer.password)
            values.put(phone_number, customer.phone_number)
            values.put(address, customer.address)
        }
        val success = db.insert(customerTable, null, values)
        db.close()
        return success
    }

    fun getCustomer():Customer?{
        val selectQuery = "select  * from $customerTable"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var customer: Customer? = null

        if(cursor.moveToFirst()){
            cursor.moveToFirst()

            val id  = cursor.getInt(cursor.getColumnIndex("id"))
            val firstName  = cursor.getString(cursor.getColumnIndex("first_name"))
            val lastName = cursor.getString(cursor.getColumnIndex("last_name"))
            val emailAddress  = cursor.getString(cursor.getColumnIndex("email_address"))
            val password = cursor.getString(cursor.getColumnIndex("password"))
            val phoneNumber  = cursor.getString(cursor.getColumnIndex("phone_number"))
            val address = cursor.getString(cursor.getColumnIndex("address"))

            customer= Customer(id,firstName,lastName,emailAddress,password,password,phoneNumber,address)
            cursor.close()
        }
        db.close()
        return customer
    }

    fun deleteCustomer(){
        val db = this.writableDatabase
        db.delete(customerTable, null, null);
        db.close()
    }

    //wish table
    fun saveWish(item: Item?):Long{
        val db = this.writableDatabase
        val values = ContentValues()

        if (item != null) {
            values.put(id, item.id)
            values.put(category_id, item.category_id)
            values.put(brand_id, item.brand_id)
            values.put(user_id, item.user_id)
            values.put(product_name, item.product_name)
            values.put(product_price, item.product_price)
            values.put(product_quantity, item.product_quantity)
            values.put(short_description, item.short_description)
            values.put(long_description, item.long_description)
            values.put(product_image, item.product_image)
            values.put(product_image_mid, item.product_image_mid)
            values.put(product_image_low, item.product_image_low)
            values.put(publication_status, item.publication_status)
            values.put(created_at, item.created_at)
            values.put(updated_at, item.updated_at)
            values.put(category_name, item.category_name)
            values.put(brand_name, item.brand_name)
        }
        val success = db.insert(wishTable, null, values)
        db.close()
        return success
    }

    @SuppressLint("Recycle")
    fun getWishList():ArrayList<Item>{

        val wishList:ArrayList<Item> = ArrayList()

        val selectQuery = "select  * from $wishTable"
        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val id  = cursor.getInt(cursor.getColumnIndex("id"))
                val categoryId  = cursor.getInt(cursor.getColumnIndex("category_id"))
                val brandId  = cursor.getInt(cursor.getColumnIndex("brand_id"))
                val userId  = cursor.getInt(cursor.getColumnIndex("user_id"))
                val productName  = cursor.getString(cursor.getColumnIndex("product_name"))
                val productPrice  = cursor.getFloat(cursor.getColumnIndex("product_price"))
                val productQuantity  = cursor.getInt(cursor.getColumnIndex("product_quantity"))
                val shortDescription  = cursor.getString(cursor.getColumnIndex("short_description"))
                val longDescription  = cursor.getString(cursor.getColumnIndex("long_description"))
                val productImage  = cursor.getString(cursor.getColumnIndex("product_image"))
                val productImageMid  = cursor.getString(cursor.getColumnIndex("product_image_mid"))
                val productImageLow  = cursor.getString(cursor.getColumnIndex("product_image_low"))
                val publicationStatus  = cursor.getInt(cursor.getColumnIndex("publication_status"))
                val createdAt  = cursor.getString(cursor.getColumnIndex("created_at"))
                val updatedAt  = cursor.getString(cursor.getColumnIndex("updated_at"))
                val categoryName  = cursor.getString(cursor.getColumnIndex("category_name"))
                val brandName  = cursor.getString(cursor.getColumnIndex("brand_name"))

                val routine= Item(id,categoryId, brandId, userId, productName,
                        productPrice, productQuantity, shortDescription, longDescription, productImage, productImageMid,
                        productImageLow, publicationStatus, createdAt, updatedAt, categoryName, brandName)

                wishList.add(routine)
            } while (cursor.moveToNext())
        }
        return wishList
    }

    fun clearWishList(){
        val db = this.writableDatabase
        db.delete(wishTable, null, null);
        db.close()
    }

    fun removeWishById(id: String) {
        val db = this.writableDatabase
        db.execSQL("delete from $wishTable where id='$id'")
        db.close()
    }





}
