package com.example.codechallenge_plentina.ui.activity.animalsList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codechallenge_plentina.R
import com.example.codechallenge_plentina.model.Animal
import com.example.codechallenge_plentina.ui.activity.animalDetail.AnimalDetailActivity
import androidx.core.app.ActivityOptionsCompat


class AnimalsListAdapter(private val activity: Activity, private val mListAnimals: List<Animal>): RecyclerView.Adapter<AnimalsListAdapter.AnimalsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_animal, parent, false)
        return AnimalsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        val obj = mListAnimals[position]
        if(obj.name?.isNotEmpty() == true){
            holder.tvName.text = obj.name
        }
        if(obj.animalType?.isNotEmpty() == true){
            holder.tvType.text = obj.animalType
        }
        if(obj.imageLink?.isNotEmpty() == true){
            Glide.with(activity).load(obj.imageLink).placeholder(R.drawable.img_placeholder).into(holder.ivAnimal)
        }

        holder.itemView.setOnClickListener{
            AnimalDetailActivity.obj = obj
            val intent = Intent(activity, AnimalDetailActivity::class.java)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, holder.ivAnimal, "viewDetail")
            activity.startActivity(intent, activityOptions.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return mListAnimals.size
    }

    inner class AnimalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName) 
        val tvType: TextView = itemView.findViewById(R.id.tvType) 
        val ivAnimal: ImageView = itemView.findViewById(R.id.ivAnimal) 
    }
}