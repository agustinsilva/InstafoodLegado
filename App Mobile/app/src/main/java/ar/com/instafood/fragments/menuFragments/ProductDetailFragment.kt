package ar.com.instafood.fragments.menuFragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ar.com.instafood.activities.R
import ar.com.instafood.activities.SearchRestaurantsActivity
import ar.com.instafood.activities.login.LoginActivity
import ar.com.instafood.application.SocketApplication
import ar.com.instafood.fragments.MenuFragment
import ar.com.instafood.models.Check
import ar.com.instafood.models.Product
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_product_detail.view.*

class ProductDetailFragment : Fragment() {
    private var tv_title: String? = null
    private var tv_description: String? = null
    private var tv_price: String? = null
    private var product_image_string: String? = null
    private var product_image: Int? = null
    private var username : String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tv_title = it.getString("title")
            tv_description = it.getString("description")
            tv_price = it.getString("price")
            product_image = it.getInt("image")
            product_image_string = it.getString("image_string")
            username = it.getString("username")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.single_product_detail, container, false)
        view.tv_title.text = tv_title
        view.tv_description.text = tv_description
        view.tv_price.text = tv_price
        Picasso.get().load(product_image_string).into(view.iv_image)
        view.toolbar_product_detail.title = tv_title
        view.toolbar_product_detail.navigationIcon = context?.getDrawable(R.drawable.abc_ic_ab_back_material)
        view.toolbar_product_detail.setNavigationOnClickListener({ returnToMenuFragment() })

        val btn = view.findViewById<Button>(R.id.buttonflat)

        btn?.setOnClickListener {
            if (userName == null || userName == ""){
                activity?.startActivityForResult(Intent(activity, LoginActivity::class.java),1)
            } else {
                username = userName
                val gsonBuilder = GsonBuilder().create()
                val app  = activity?.application as SocketApplication
                val socket = app.socket
                val json = gsonBuilder.toJson(Check(userName!!, arrayListOf(Product(tv_title ?: "", tv_description ?: "", tv_price?.toInt() ?: 0, product_image_string!!))))
                socket?.emit("productSelected", json)
                //returnToMenuFragment()
            }

        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        username = data!!.getExtras().getString("username_result")
        val gsonBuilder = GsonBuilder().create()
        val app  = activity?.application as SocketApplication
        val socket = app.socket
        val json = gsonBuilder.toJson(Check(username!!, arrayListOf(Product(tv_title ?: "", tv_description ?: "", tv_price?.toInt() ?: 0, product_image_string!!))))
        socket?.emit("productSelected", json)
    }

    private fun returnToMenuFragment() {
        val transaction = fragmentManager!!.beginTransaction()
        val fragment = fragmentManager!!.findFragmentByTag("MENU_FRAGMENT") as MenuFragment
        transaction.replace(R.id.fragment_container, fragment, "MENU_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    companion object {
        var userName: String? = null
        var scanCode: String? = null

        @JvmStatic
        fun newInstance(product: String, username : String) =
                ProductDetailFragment().apply {
                    arguments = Bundle().apply {
                       putString("title", product)
                        putString("username",username)
                    }
                }
    }


}