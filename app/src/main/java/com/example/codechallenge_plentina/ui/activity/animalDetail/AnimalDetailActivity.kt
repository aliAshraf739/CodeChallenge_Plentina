package com.example.codechallenge_plentina.ui.activity.animalDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.codechallenge_plentina.R
import com.example.codechallenge_plentina.model.Animal

class AnimalDetailActivity : AppCompatActivity() {

    private val TAG = "AnimalDetailActivity"

    private val ivAnimal: ImageView by lazy { findViewById(R.id.ivAnimal) }
    private val tvName: TextView by lazy { findViewById(R.id.tvName) }
    private val tvType: TextView by lazy { findViewById(R.id.tvType) }
    private val tvDiet: TextView by lazy { findViewById(R.id.tvDiet) }
    private val tvLifeSpan: TextView by lazy { findViewById(R.id.tvLifeSpan) }
    private val tvHabitat: TextView by lazy { findViewById(R.id.tvHabitat) }
    private val tvGeoRange: TextView by lazy { findViewById(R.id.tvGeoRange) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)
        setData()
    }

    private fun setData() {
        try {

            if(obj?.imageLink?.isNotEmpty() == true){
                Glide.with(this).load(obj?.imageLink).placeholder(R.drawable.img_placeholder).into(ivAnimal)
            }

            if(obj?.name?.isNotEmpty() == true){
                tvName.text = obj?.name
            }
            if(obj?.animalType?.isNotEmpty() == true){
                tvType.text = obj?.animalType
            }
            if(obj?.diet?.isNotEmpty() == true){
                tvDiet.text = obj?.diet
            }
            if(obj?.lifespan?.isNotEmpty() == true){
                tvLifeSpan.text = obj?.lifespan
            }
            if(obj?.habitat?.isNotEmpty() == true){
                tvHabitat.text = obj?.habitat
            }
            if(obj?.geoRange?.isNotEmpty() == true){
                tvGeoRange.text = obj?.geoRange
            }

        }catch (ex:Exception){
            Log.e(TAG, "Exception: ${ex.message}" )
        }
    }

    companion object {
        var obj: Animal? =null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e(TAG, "onBackPressed: ")
        supportFinishAfterTransition()
    }

}