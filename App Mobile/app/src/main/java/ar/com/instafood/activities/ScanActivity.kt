package ar.com.instafood.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ar.com.instafood.application.SocketApplication
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import ar.com.instafood.fragments.MenuFragment
import android.graphics.Bitmap
import android.widget.ImageView
import ar.com.instafood.activities.login.LoginActivity
import ar.com.instafood.fragments.menuFragments.ProductDetailFragment
import ar.com.instafood.fragments.menuFragments.ProductDetailFragment.Companion.scanCode
import ar.com.instafood.models.Check
import ar.com.instafood.models.QrUserRestaurant
import ar.com.instafood.models.Restaurant
import com.google.gson.GsonBuilder


/**
 * Created by mnavarro on 24/10/2018.
 */
class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    var qrData : QrUserRestaurant? = null
    var username : String? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
        //setContentView(R.layout.activity_scan)
        //iv = findViewById(R.id.iv)
        //convertToImage("Lo que sea.");
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
       super.onPause()
       mScannerView!!.stopCamera()           // Stop camera on pause
    }

     override fun handleResult(rawResult: Result) {

        Log.i("SCAN RESULT", rawResult.text)
         qrData = QrUserRestaurant.deserialize(rawResult.text)
         if (ProductDetailFragment.userName == null){
             this.startActivityForResult(Intent(this, LoginActivity::class.java),1)
         }
         else {
             returnMain(qrData!!)
         }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            username = data!!.getExtras().getSerializable("username_result") as String
            //emitResultToSocket(username!!)
            emitResultToSocket(qrData!!.username)
            returnMain(qrData!!)
        }
    }

    private fun emitResultToSocket(resultFromScanner: String){
        val app  = application as SocketApplication
        val socket = app.socket
        if(scanCode == null){
            scanCode = resultFromScanner
        }
        socket?.emit("addNewUser", scanCode)
    }




    private fun returnMain(qrData : QrUserRestaurant){
        /**
         * TODO: refactorizar ya que se le envia al socket previamente el texto como parametro en el
         * canal
         * */

        var intent_result = Intent()
        intent_result.putExtra("qrText", qrData.username)
        intent_result.putExtra("username",username)
        intent_result.putExtra("activity_id", 2)
        intent_result.putExtra("restaurant",qrData.restaurant)
        setResult(Activity.RESULT_OK, intent_result)
        finish()
    }

    override fun onBackPressed() {

        var intent_result = Intent()
        setResult(Activity.RESULT_CANCELED, intent_result)
        finish()
        //val transaction = supportFragmentManager.beginTransaction()
        //transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        //transaction.replace(R.id.fragment_container, menuFragment, menuFragment.tag).addToBackStack(null)
        //transaction.commitAllowingStateLoss()
    }

}