package com.alarcin.login4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (private val userList: ArrayList<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder> () {


        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder)
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           val adisoyadi_tv:TextView = view.findViewById(R.id.card_adisoyadi_tv)
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.kullanici_item_list, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            viewHolder.adisoyadi_tv.text = userList[position].adisoyadi
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = userList.size

    }

