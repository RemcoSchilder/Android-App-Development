package com.example.shoppinglist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglist.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): List<Product>

    @Insert
    fun insertProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("DELETE FROM product_table")
    fun deleteAllProducts()

}