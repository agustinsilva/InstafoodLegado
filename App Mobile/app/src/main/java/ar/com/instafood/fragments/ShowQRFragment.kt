package ar.com.instafood.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ar.com.instafood.activities.R
import ar.com.instafood.fragments.menuFragments.ProductDetailFragment
import ar.com.instafood.models.QrUserRestaurant
import ar.com.instafood.models.Restaurant
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.show_qr.view.*

/**
 * Created by mnavarro on 23/11/2018.
 */
class ShowQRFragment : Fragment() {

    internal var bitmap: Bitmap? = null
    private lateinit var viewScan : View
    var restaurant : Restaurant? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val bundle = arguments
        restaurant = bundle!!.getSerializable("restaurant") as Restaurant
        var username = ProductDetailFragment.userName!!
        var qrData  = QrUserRestaurant(username,restaurant!!)
        viewScan = inflater.inflate(R.layout.show_qr, container, false)
        bitmap = textToImageEncode(QrUserRestaurant.serialize(qrData))
        viewScan.iv.setImageBitmap(bitmap)
        //convertToImage();
        goToOrder()

        return viewScan
    }

    private fun convertToImage(text: String){


    }

    @Throws(WriterException::class)
    private fun textToImageEncode(textToEncode: String): Bitmap? {
        var bitMatrix: BitMatrix
        var multiFormatWriter = MultiFormatWriter()
        var QRcodeWidth = 500
        try {
            bitMatrix = multiFormatWriter.encode(
                    textToEncode,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeWidth,null)

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        var bitMatrixWidth = bitMatrix.getWidth()

        var bitMatrixHeight = bitMatrix.getHeight()

        var pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            var offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }

        var bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun goToOrder(){
        viewScan.btn.setOnClickListener { _ ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            var orderFragment = MainFragment()
            transaction.replace(R.id.fragment_container, orderFragment).addToBackStack(null)
            transaction.commitAllowingStateLoss()
        }

    }


}