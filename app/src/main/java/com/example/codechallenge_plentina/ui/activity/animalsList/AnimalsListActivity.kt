package com.example.codechallenge_plentina.ui.activity.animalsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.builbee.app.util.Internet
import com.builbee.app.util.Loading
import com.example.codechallenge_plentina.R
import com.example.codechallenge_plentina.model.Animal
import com.example.codechallenge_plentina.network.RestApi
import com.example.codechallenge_plentina.util.Alert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AnimalsListActivity : AppCompatActivity() {

    private val TAG = "AnimalsListActivity"
    private val rvAnimals: RecyclerView by lazy { findViewById(R.id.rvAnimals) }
    private val tvPlaceholder: TextView by lazy { findViewById(R.id.tvPlaceholder) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_list)
        getListOfAnimals()
    }

    private fun getListOfAnimals() {
        if(!Internet.isAvailable(this)){
            Internet.displayNetworkError(this, object : Internet.NetworkError{
                override fun error(result: Boolean) {
                    getListOfAnimals()
                }
            })
            return
        }

        Loading.showLoading(this, false)
        val call: Call<List<Animal>?>? = RestApi.getService()?.getAnimalsList(5)
        call?.enqueue(object : Callback<List<Animal>?> {
            override fun onResponse(call: Call<List<Animal>?>, response: Response<List<Animal>?>) {
                Loading.hideLoading()
                if (response.code()==200) {
                    Log.e(TAG, "List Items: "+ response.body().toString())
                    if(response.body()?.isNotEmpty() == true){
                        try {
                            setRecycler(response.body()!!)
                            tvPlaceholder.visibility = View.INVISIBLE
                        }catch (exception: Exception){
                            Log.e(TAG, "Exception: ${exception.message}" )
                        }
                    }else{
                        tvPlaceholder.visibility = View.VISIBLE
                    }
                } else {
                    Alert.show(this@AnimalsListActivity, "Error", "Something went wrong.")
                }
            }
            override fun onFailure(
                call: Call<List<Animal>?>,
                t: Throwable
            ) {
                Loading.hideLoading()
                Alert.show(this@AnimalsListActivity, "Error", "Something went wrong.")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    
    private fun setRecycler(list: List<Animal>) {
        rvAnimals.setHasFixedSize(true)
        rvAnimals.layoutManager = LinearLayoutManager(this)
        rvAnimals.adapter = AnimalsListAdapter(this, list)
    }

}