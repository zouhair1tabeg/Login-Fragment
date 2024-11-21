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
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getWatch().enqueue(object : Callback<List<Smart_Watch>> {
            override fun onResponse(call: Call<List<Smart_Watch>>, response: Response<List<Smart_Watch>>) {
                if (response.isSuccessful) {
                    val watches = response.body() ?: emptyList()

                    gridLayout.removeAllViews()

                    for (watch in watches) {
                        val watchLayout = LinearLayout(context).apply {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            setPadding(16, 16, 16, 16)
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = GridLayout.LayoutParams.WRAP_CONTENT
                                height = GridLayout.LayoutParams.WRAP_CONTENT
                                setMargins(16, 16, 16, 16)
                            }
                        }

                        val imageView = ImageView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(400, 400)
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            Glide.with(this)
                                .load(watch.image_url)
                                .into(this)
                        }

                        val textView = TextView(context).apply {
                            text = watch.name
                            gravity = Gravity.CENTER
                            textSize = 16f
                            setTextColor(Color.BLACK)
                        }

                        val textView2 = TextView(context).apply {
                            text = "${watch.price} MAD"
                            gravity = Gravity.CENTER
                            textSize = 14f
                            setTextColor(Color.BLACK)
                        }

                        val textView3 = TextView(context).apply {
                            text = "${watch.battery_life}"
                            gravity = Gravity.CENTER
                            textSize = 14f
                            setTextColor(Color.BLACK)
                        }

                        watchLayout.addView(imageView)
                        watchLayout.addView(textView)
                        watchLayout.addView(textView2)
                        watchLayout.addView(textView3)

                        gridLayout.addView(watchLayout)
                    }
                } else {
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Smart_Watch>>, t: Throwable) {
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
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}