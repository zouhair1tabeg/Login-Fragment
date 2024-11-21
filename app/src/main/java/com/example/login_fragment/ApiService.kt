package com.example.login_fragment

import retrofit2.http.GET

data class Smart_Watch(
    val id : Int,
    val name : String,
    val price : Double,
    val battery_life : String,
    val in_stock : Boolean,
    val image_url : String
)

data class Smartphone(
    val id: Int,
    val nom: String,
    val prix: Double,
    val image: String
)

interface ApiService {
    @GET("/WatchAPI/readAll.php")
    fun getWatch(): retrofit2.Call<List<Smart_Watch>>


    @GET("/SmartphoneAPI/readAll.php")
    fun getSmartphones(): retrofit2.Call<List<Smartphone>>
}