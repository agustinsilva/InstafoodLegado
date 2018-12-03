package ar.com.instafood.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import ar.com.instafood.fragments.SearchRestaurantFragment
import kotlinx.android.synthetic.main.activity_search_restaurants.*
import kotlinx.android.synthetic.main.toolbar.*

class SearchRestaurantsActivity : AppCompatActivity() {

    private val searchRestaurantFragment : SearchRestaurantFragment = SearchRestaurantFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_restaurants)
        SetActionBar()
        search_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.search_fragment_container, searchRestaurantFragment)
        transaction.commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        when (item.itemId){
            R.id.navigation_home ->  this.finish()
        }
        transaction.commit()
        true
    }

    private fun SetActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Buscar restaurantes")
    }
}