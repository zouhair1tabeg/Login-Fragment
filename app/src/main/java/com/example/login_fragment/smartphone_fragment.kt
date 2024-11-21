package com.example.login_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [smartphone_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class smartphone_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_smartphone_fragment, container, false)

        val rclView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getSmartphones().enqueue(object : Callback<List<Smartphone>> {
            override fun onResponse(call: Call<List<Smartphone>>, response: Response<List<Smartphone>>) {
                if (response.isSuccessful) {
                    val smartphones = response.body() ?: emptyList()

                    val adapter = ProductAdapter(smartphones)
                    rclView.layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL,false)
                    rclView.adapter = adapter
                } else {
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Smartphone>>, t: Throwable) {
                Toast.makeText(context, "Failed to load data: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment smartphone_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            smartphone_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}