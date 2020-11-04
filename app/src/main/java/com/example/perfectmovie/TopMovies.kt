package com.example.perfectmovie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.BuildConfig
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopMovies.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopMovies : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var myView: View
    private var myContext: Context?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        myContext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_top_movies, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val client = OkHttpClient()
        val connectionString = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + com.example.perfectmovie.BuildConfig.API_KEY + "&language=ru-RU&page=1"
        val request = Request.Builder().url(connectionString).build()
        var list: ArrayList<ItemOfList> = arrayListOf()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val recyclerView = myView.findViewById<RecyclerView>(R.id.TopMoviesRecyclerView)
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
                //this@MainActivity.runOnUiThread {
//                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = ItemsAdapter(myContext, list) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("OBJECT INTENT", it)
                    startActivity(intent)
                }
                //    }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopMovies.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TopMovies().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}