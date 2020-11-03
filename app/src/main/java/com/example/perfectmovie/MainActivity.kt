package com.example.perfectmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_expected_movies.*
import kotlinx.android.synthetic.main.fragment_top_movies.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetTopMovies()
        GetExpectedMovies()
        val expectedMoviesFragment = ExpectedMovies()
        val topMoviesFragment = TopMovies()
        makeCurrentFragment(expectedMoviesFragment)
        bnw.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.expected_movies -> makeCurrentFragment(expectedMoviesFragment)
                R.id.top_movies -> makeCurrentFragment(topMoviesFragment)
            }
            true
        }
    }

     fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
     fun GetTopMovies(){
         val client = OkHttpClient()
         val connectionString = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.API_KEY + "&language=ru-RU&page=1"
         val request = Request.Builder().url(connectionString).build()
         var list: ArrayList<ItemOfList> = arrayListOf()
         client.newCall(request).enqueue(object : Callback {
             override fun onFailure(call: Call, e: IOException) {}
             override fun onResponse(call: Call, response: Response){
                 val recyclerView = findViewById<RecyclerView>(R.id.TopMoviesRecyclerView)
                 var str = response!!.body()!!.string()
                 var movies = (JSONObject(str).getJSONArray("results"))
                 for (i in 0 until (movies.length() - 1)) {
                     var itemOfList: ItemOfList = ItemOfList(
                         (movies.getJSONObject(i).get("title")).toString(),
                         (movies.getJSONObject(i).get("overview")).toString(),
                         (movies.getJSONObject(i).get("vote_average")).toString(),
                         (movies.getJSONObject(i).get("poster_path")).toString(),
                         (movies.getJSONObject(i).get("release_date")).toString())
                     list.add(itemOfList)
                 }
                 this@MainActivity.runOnUiThread {
                     recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                     recyclerView.setHasFixedSize(true)
                     recyclerView.adapter = ItemsAdapter(this@MainActivity, list) {
                         val intent = Intent(this@MainActivity, DetailActivity::class.java)
                         intent.putExtra("OBJECT INTENT", it)
                         startActivity(intent)
                     }
                 }
             }
         })
     }
    fun GetExpectedMovies(){
        val client = OkHttpClient()
        val connectionString = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY + "&language=ru-RU&page=1"
        val request = Request.Builder().url(connectionString).build()
        var list: ArrayList<ItemOfList> = arrayListOf()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                val recyclerView = findViewById<RecyclerView>(R.id.ExpectedMoviesRecyclerView)
                var str = response!!.body()!!.string()
                var movies = (JSONObject(str).getJSONArray("results"))
                for (i in 0 until (movies.length() - 1)) {
                    var itemOfList: ItemOfList = ItemOfList(
                        (movies.getJSONObject(i).get("title")).toString(),
                        (movies.getJSONObject(i).get("overview")).toString(),
                        (movies.getJSONObject(i).get("vote_average")).toString(),
                        (movies.getJSONObject(i).get("poster_path")).toString(),
                        (movies.getJSONObject(i).get("release_date")).toString())
                    list.add(itemOfList)
                }
                this@MainActivity.runOnUiThread {

                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = ItemsAdapter(this@MainActivity, list) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("OBJECT INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}



