package com.example.codechallenge_plentina.ui.fragment.animalsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codechallenge_plentina.R
import com.example.codechallenge_plentina.model.Animal
import com.example.codechallenge_plentina.ui.fragment.animalDetail.AnimalDetailFragment
import com.example.codechallenge_plentina.util.Util

class AnimalsListAdapter(private val fragmentActivity: FragmentActivity, private val mListAnimals: List<Animal>): RecyclerView.Adapter<AnimalsListAdapter.AnimalsViewHolder>() {

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
            Glide.with(fragmentActivity).load(obj.imageLink).placeholder(R.drawable.img_placeholder).into(holder.ivAnimal)
        }

        holder.itemView.setOnClickListener{
            AnimalDetailFragment.obj = obj
            val extras = FragmentNavigatorExtras(holder.ivAnimal to "viewDetail")
            Util.performNavigation(fragmentActivity, extras, R.id.action_itemsListFragment_to_itemDetailFragment)
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