package ar.com.instafood.adapters

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.com.instafood.models.Restaurant
import ar.com.instafood.activities.R
import ar.com.instafood.interfaces.adapterCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_restaurant_card.view.*

class MainRestaurantAdapter(val restaurants : List<Restaurant>?, var cb : adapterCallback) : RecyclerView.Adapter<MainRestaurantAdapter.RestaurantViewHolder>() {

    var items : List<Restaurant> = ArrayList()
    init{
        this.cb = cb
    }

    class RestaurantViewHolder(var card: View, adapterCallback: adapterCallback) : RecyclerView.ViewHolder(card) {
        init {
            card.setOnClickListener { v: View ->
                var position: Int = adapterPosition
                adapterCallback.onItemClick(position)
            }
        }

    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        if (holder != null) {
            val restaurant = items[position]
            holder.card.tv_title.text = restaurant.title
            holder.card.tv_description.text = restaurant.description
            holder.card.tv_distance.text = restaurant.distance
            Picasso.get().load(restaurant.resource).into(holder.card.iv_icon)
        }
    }
    //Create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_restaurant_card, parent, false)
        return RestaurantViewHolder(view,cb)
    }

    override fun getItemCount(): Int {
        return if(items !== null) items.size else 0
    }
}