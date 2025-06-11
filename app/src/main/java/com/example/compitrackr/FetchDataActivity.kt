package com.example.compitrackr

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest

data class Contest(val name: String, val rank: Int)

class FetchDataActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contestList: MutableList<Contest>
    private lateinit var adapter: ContestAdapter
    private lateinit var handle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_data)

        // ✅ Get saved handle from SharedPreferences
        val prefs = getSharedPreferences("UserProfile", MODE_PRIVATE)
        handle = prefs.getString("handle", "tourist") ?: "tourist" // fallback handle

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        contestList = mutableListOf()
        adapter = ContestAdapter(contestList)
        recyclerView.adapter = adapter

        fetchLatestContestData()
    }


    private fun fetchLatestContestData() {
        val url = "https://codeforces.com/api/user.rating?handle=$handle"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(url, { response ->
            val resultArray = response.getJSONArray("result")
            if (resultArray.length() > 0) {
                val latest = resultArray.getJSONObject(resultArray.length() - 1)
                val contestName = latest.getString("contestName")
                val rank = latest.getInt("rank")

                // Add the fetched data to the list and update the RecyclerView
                contestList.add(Contest(contestName, rank))
                adapter.notifyDataSetChanged()  // Notify the adapter to update the RecyclerView
            }
        }, { error ->
            Log.e("FetchData", "Error fetching data: ${error.message}")
        })

        queue.add(request)  // Add the request to the Volley queue
    }
}
