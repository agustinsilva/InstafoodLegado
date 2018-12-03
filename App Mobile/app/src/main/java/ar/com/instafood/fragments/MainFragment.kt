package ar.com.instafood.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import ar.com.instafood.activities.R
import ar.com.instafood.activities.ScanActivity
import ar.com.instafood.activities.SearchRestaurantsActivity
import ar.com.instafood.adapters.MainRestaurantAdapter
import ar.com.instafood.interfaces.RestaurantsService
import ar.com.instafood.interfaces.adapterCallback
import ar.com.instafood.models.Restaurant
import ar.com.instafood.models.setDistances
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import ar.com.instafood.activities.MainActivity



/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment(), adapterCallback {


    private var locationManager : LocationManager? = null
    private var currentLocation : Location? = null
    private var restaurants : List<Restaurant>? = null
    private var adapter : MainRestaurantAdapter? = null
    private var recyclerViewMainRestaurant : RecyclerView? = null
    private var spinner : View? = null
    private var self = this
    private var disposable: Disposable? = null
    private val restaurantAPIServe by lazy {
        RestaurantsService.create()
    }
    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerViewMainRestaurant = view.findViewById(R.id.recyclerViewMainRestaurant)
        spinner = view.findViewById(R.id.main_progress_bar_loading)
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        return view
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location
            if(restaurants !== null) {
                setDistances(restaurants, currentLocation)
                restaurants = restaurants!!.filter{it.distanceDouble <= 10}
                restaurants = restaurants!!.sortedWith(compareBy {it.distanceDouble})
                adapter!!.items = restaurants!!
                adapter!!.notifyDataSetChanged()
                if(restaurants!!.isEmpty()){
                    closeMeNoData?.visibility = VISIBLE
                }else{
                    closeMeNoData?.visibility = GONE
                    enableVisibility()
                }
                //val recyclerViewMainRestaurant = getView()?.findViewById<RecyclerView>(R.id.recyclerViewMainRestaurant)
                //recyclerViewMainRestaurant?.adapter = MainRestaurantAdapter(restaurants,self)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onItemClick(position : Int){
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        var menuFragment = MenuFragment()
        var args = Bundle()
        var rest : Restaurant = restaurants!![position]
        val myActivity = activity as MainActivity?
        myActivity!!.setRestaurantProp(rest)
        args.putSerializable("restaurant",rest)
        menuFragment.setArguments(args)
        transaction.replace(R.id.fragment_container, menuFragment, menuFragment.tag).addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchSearchRestaurants()
        setQRscan()
        if(restaurants == null) {
            disableVisibility()
            val handler = Handler()
            handler.postDelayed({
                getRests()
            }, 800)
        }
        recyclerViewMainRestaurant?.setHasFixedSize(true)
        recyclerViewMainRestaurant?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMainRestaurant?.adapter = MainRestaurantAdapter(restaurants,this)
    }

    private fun setQRscan(){
        val btn_QRScan = getView()?.findViewById<Button>(R.id.QRScan)
        btn_QRScan?.setOnClickListener { _ ->
            val intent = Intent(activity, ScanActivity::class.java)
            activity?.startActivityForResult(intent,1)
        }
    }
    private fun switchSearchRestaurants() {
        val restSearch = view?.findViewById<Button>(R.id.restaurantSearch)
        restSearch?.setOnClickListener { _ ->
            activity?.startActivityForResult(Intent(activity, SearchRestaurantsActivity::class.java),1)
        }
    }

    private fun getRests() {
        if (recyclerViewMainRestaurant is RecyclerView) {
            with(view) {
                adapter = MainRestaurantAdapter(restaurants,self)
                recyclerViewMainRestaurant!!.adapter = adapter
                disposable = restaurantAPIServe.getRestaurants().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    if(result.restaurants !== null){
                                    adapter!!.items = result.restaurants
                                    adapter!!.notifyDataSetChanged()
                                    restaurants = result.restaurants
                                        if (currentLocation !== null) {
                                            setDistances(restaurants, currentLocation)
                                            restaurants = restaurants!!.sortedWith(compareBy {it.distanceDouble})
                                            adapter!!.items = restaurants!!
                                            adapter!!.notifyDataSetChanged()
                                            enableVisibility()
                                        } else {
                                            disableVisibility()
                                        }
                                    }},
                                { error -> Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show() }
                        )
            }
        }

    }

    private fun enableVisibility(){
        recyclerViewMainRestaurant!!.visibility = View.VISIBLE
        spinner!!.visibility = View.GONE
    }

    private fun disableVisibility(){
        recyclerViewMainRestaurant!!.visibility = View.GONE
        spinner!!.visibility = View.VISIBLE
    }
}
