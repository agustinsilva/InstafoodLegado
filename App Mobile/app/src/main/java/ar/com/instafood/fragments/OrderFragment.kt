package ar.com.instafood.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import ar.com.instafood.activities.CloseActivity
import ar.com.instafood.activities.R
import ar.com.instafood.fragments.order.PreferenceUtils
import ar.com.instafood.fragments.order.TimerState
import ar.com.instafood.models.Check
import ar.com.instafood.models.SingleOrder
import kotlinx.android.synthetic.main.fragment_order.view.*
import java.lang.StringBuilder


class OrderFragment : Fragment() {

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.STOPPED
    private lateinit var viewOrder : View
    private var secondsRemaining: Long = 0
    private var checks: java.util.ArrayList<Check>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.viewOrder = inflater.inflate(R.layout.fragment_order, container, false)
        val bundle = arguments
        if (bundle != null && bundle.getSerializable("checks") != null) {
            checks = bundle.getSerializable("checks") as java.util.ArrayList<Check>
        }
        initTimer()
        if (timerState != TimerState.RUNNING) {
            startTimer()
        }
        return this.viewOrder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initMessage()
        initLabel()
        initCloseButton()
        initOrderDetail()
        setAddMenu()
        initWaiterButton()
        initAccumulated()
        super.onResume()
        //initTimer()
    }

    override fun onPause() {
        super.onPause()
        PreferenceUtils.setTimerState(timerState, this.requireContext())
    }

    private fun initTimer(){
        timerState = PreferenceUtils.getTimerState(this.requireContext())

        setNewTimerLength()

        if (secondsRemaining <= 0)
            onTimerFinished()

        updateCountdownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.STOPPED

        viewOrder.bar_timer.progress = 0

        secondsRemaining = timerLengthSeconds

//        updateCountdownUI()
        val label = view?.findViewById<TextView>(R.id.lbl_will_arrive_in)
        label?.text =this.resources.getString(R.string.lbl_order_title)
    }

    private fun startTimer(){
        timerState = TimerState.RUNNING


        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PreferenceUtils.getTimerLength()
        timerLengthSeconds = (lengthInMinutes * 60L)
        viewOrder.bar_timer.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        viewOrder.lbl_timer?.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        viewOrder.bar_timer?.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun initToolbar() {
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.setNavigationIcon(R.drawable.ic_restaurant)
        toolbar?.title = this.resources.getString(R.string.lbl_order_title)
    }

    private fun initMessage() {
        val amount = view?.findViewById<TextView>(R.id.lbl_will_arrive_in)
        amount?.text = this.resources.getString(R.string.lbl_order_will_arrive_in)
    }

    private fun initLabel() {
        val amount = view?.findViewById<TextView>(R.id.lbl_last_order)
        amount?.text = this.resources.getString(R.string.lbl_order_last_order)
    }

    private fun initOrderDetail() {
        val amount = view?.findViewById<TextView>(R.id.lbl_order_detail)

        val builder = StringBuilder()
        builder.append(this.resources.getString(R.string.lbl_order_detail) + ":\n")

        for (singleOrder in fetchLastOrder()) {
            builder
                    .append("\t- ")
                    .append(singleOrder.quantity)
                    .append(" ")
                    .append(singleOrder.productName)
                    .append("\n")
        }

        amount?.text = builder.toString()
    }


    private fun setAddMenu(){
        val btn_add = view?.findViewById<Button>(R.id.btn_add)
        btn_add?.setOnClickListener { _ ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            var menuFragment = MenuFragment()
            transaction.replace(R.id.fragment_container, menuFragment).addToBackStack(null)
            transaction.commitAllowingStateLoss()
        }
    }

    private fun initCloseButton() {
        val closeButton = view?.findViewById<Button>(R.id.btn_close)
        closeButton?.setOnClickListener { _ ->

            val alertDialog = AlertDialog.Builder(this.requireContext()).create()
            alertDialog.setMessage(this.resources.getString(R.string.lbl_order_popup_question))
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, this.resources.getString(R.string.lbl_order_yes)) {
                dialog, which ->
                    val intent = Intent(activity, CloseActivity::class.java)
                    intent.putExtra("amount", getAccumulated().toString())
                    activity?.startActivityForResult(intent,1) }
            alertDialog.show()
        }
    }

    private fun initWaiterButton(){
        val closeButton = view?.findViewById<Button>(R.id.btn_waiter)
        closeButton?.setOnClickListener { _ ->
            val alertDialog = AlertDialog.Builder(this.requireContext()).create()
            alertDialog.setMessage("¿En verdad deseas llamar al mesero?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, this.resources.getString(R.string.lbl_order_yes))
            { dialog, which -> Toast.makeText(getActivity(), "Llamado a mesero realizado", Toast.LENGTH_LONG).show(); }
            alertDialog.show()
        }
    }

    fun fetchLastOrder() : List<SingleOrder> {
        if (checks != null) {
            return checks?.flatMap { it.products }?.map {
                var title : String = it.title
                if (it.title.length > 20) {
                    title = it.title.substring(0,10)
                }
                SingleOrder(title, 1)
            }!!
        } else {
            return emptyList()
        }
    }


    fun initAccumulated () {
        val label = view?.findViewById<TextView>(R.id.out_accumulated)
        label?.text = "$ " + getAccumulated()
    }

    fun getAccumulated() : Int? {
        if (checks != null) {
            return checks?.flatMap { it.products }?.sumBy { it.price }
        } else {
            return 0
        }
    }

}
