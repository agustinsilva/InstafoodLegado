package ar.com.instafood.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ar.com.instafood.activities.R
import ar.com.instafood.activities.R.id.btn_confirmation
import ar.com.instafood.adapters.CheckAdapter
import ar.com.instafood.adapters.DynamicCheckAdapter
import ar.com.instafood.application.SocketApplication
import ar.com.instafood.models.Check
import ar.com.instafood.models.getSampleCheck
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_check.view.*
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 *
 */
class CheckFragment : Fragment() {

    private var checks: ArrayList<Check>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_check, container, false)
        val app  = activity?.application as SocketApplication

        var array: ArrayList<Check> = arrayListOf()
        app.socket?.emit("getProducts")
        app.socket?.on("products", { args -> run {
            activity?.runOnUiThread(
                    {
                    if (args[0] != null ) {
                        val jsonElement = JsonParser().parse((args[0] as JSONArray).toString())
                        checks = Gson().fromJson(jsonElement, Array<Check>::class.java).toCollection(ArrayList())
                        var mDynamicListAdapter = DynamicCheckAdapter()
                        mDynamicListAdapter.setFirstList(checks!!)
                        view.cardList.setHasFixedSize(true)
                        view.cardList.layoutManager = LinearLayoutManager(context)
                        view.cardList.adapter = mDynamicListAdapter
                    }
                    }
            )

        }})
        // Initialize the list
        //mDynamicListAdapter.setSecondList(checks!!.get(1))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setConfirmation()
    }

    private fun setConfirmation(){
        val btn_confirmation = view?.findViewById<Button>(R.id.btn_confirmation)
        btn_confirmation?.setOnClickListener { _ ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            var orderFragment = OrderFragment()
            var args = Bundle()
            args.putSerializable("checks",checks)
            orderFragment.setArguments(args)
            transaction.replace(R.id.fragment_container, orderFragment).addToBackStack(null)
            transaction.commitAllowingStateLoss()
        }
    }
    private fun initialize() {
    }


}
