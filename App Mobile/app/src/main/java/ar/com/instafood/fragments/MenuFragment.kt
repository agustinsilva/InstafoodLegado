package ar.com.instafood.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import ar.com.instafood.activities.R
import ar.com.instafood.adapters.MenuTabsAdapter
import ar.com.instafood.fragments.menuFragments.ProductFragment
import ar.com.instafood.models.Restaurant
import com.squareup.picasso.Picasso
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_menu.*
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuFragment : Fragment() {
    var listFragment = arrayListOf<Fragment>()
    var listTitle = arrayListOf<String>()
    var menuViewPager: ViewPager? = null
    var menuTabLayout: TabLayout? = null
    var menuAdapter : MenuTabsAdapter? = null
    var restaurant : Restaurant? = null
    var username : String? = null
    private lateinit var viewMenu : View

    init {
        listFragment.clear()
        listTitle.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val bundle = arguments
        restaurant = bundle!!.getSerializable("restaurant") as Restaurant
        username = bundle.getString("username")
        viewMenu = inflater.inflate(R.layout.fragment_menu, container, false)
        shareMenu()
        //initialise()
        prepareDataResource()
        setBar(viewMenu)

        menuViewPager = viewMenu.findViewById(R.id.menuViewPager)
        menuTabLayout = viewMenu.findViewById(R.id.menuTabs)


        if(menuAdapter == null) {
            menuAdapter = MenuTabsAdapter(childFragmentManager, listFragment, listTitle)
        }
        menuViewPager!!.adapter = menuAdapter

        menuTabLayout!!.setupWithViewPager(menuViewPager)

        return viewMenu
    }

    private fun setBar(view: View) {
        var toolbar = view.findViewById<android.support.v7.widget.Toolbar>(R.id.hbtoolbar)
        var titleRestaurant = view.findViewById<me.grantland.widget.AutofitTextView>(R.id.tv_title_restaurant)
        var addressRestaurant = view.findViewById<TextView>(R.id.tv_address)
        var logoRestaurant = view.findViewById<com.mikhaellopez.circularimageview.CircularImageView>(R.id.logo_comp)
        //htab_header_image
        //
        var backgroundRestaraunt = view.findViewById<ImageView>(R.id.htab_header_image)
        var scheduleRestaurant = view.findViewById<TextView>(R.id.tv_open)
        titleRestaurant.text = restaurant!!.title
        addressRestaurant.text = restaurant!!.description
        Picasso.get().load(restaurant!!.resource).into(logoRestaurant)
        Picasso.get().load(restaurant!!.background_url).into(backgroundRestaraunt)
        scheduleRestaurant.text = restaurant!!.schedule
        toolbar.title = restaurant!!.title
    }

    private fun initialise() {
        listTitle.clear()
        listFragment.clear()
    }

    private fun prepareDataResource() {
        if (listFragment.isEmpty()) {
            if(username != null) {
                addData(ProductFragment.newInstance("platosPrincipales", restaurant!!.id, username!!), "Platos Principales")
                addData(ProductFragment.newInstance("platosSecundarios", restaurant!!.id, username!!), "Entradas")
                addData(ProductFragment.newInstance("postreBebidas", restaurant!!.id, username!!), "Bebidas y Postres Especiales")
            }
            else{
                username = "";
                addData(ProductFragment.newInstance("platosPrincipales", restaurant!!.id, username!!), "Platos Principales")
                addData(ProductFragment.newInstance("platosSecundarios", restaurant!!.id, username!!), "Entradas")
                addData(ProductFragment.newInstance("postreBebidas", restaurant!!.id, username!!), "Bebidas y Postres Especiales")
            }
        }
    }

    private fun addData(fragment : Fragment, title : String ){
        listFragment.add(fragment)
        listTitle.add(title)

    }


    private fun shareMenu(){
        viewMenu.searchImageButton.setOnClickListener { _ ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            var showQRFragment = ShowQRFragment()
            var args = Bundle()
            args.putSerializable("restaurant",restaurant)
            showQRFragment.setArguments(args)
            transaction.replace(R.id.fragment_container, showQRFragment).addToBackStack(null)
            transaction.commit()
        }
    }


}

