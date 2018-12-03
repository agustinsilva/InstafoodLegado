package ar.com.instafood.adapters

import android.support.asynclayoutinflater.R.id.title
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ar.com.instafood.models.Check
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ar.com.instafood.activities.R
import com.squareup.picasso.Picasso


open class DynamicCheckAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val FIRSTELEMENT = 2
    private val HEADER = 1
    companion object {
        @JvmStatic lateinit var firstListItem : Check
        @JvmStatic lateinit var secondListItem : ArrayList<Check>
        @JvmStatic lateinit var maping : HashMap<Int, String>
    }

    fun setFirstList(firstList: ArrayList<Check>) {
        secondListItem = firstList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is ViewHolder.HeaderElementViewHolder) {
                val vh = holder
                vh.bindViewHeaderList(position)
            } else if (holder is ViewHolder.SecondElementViewHolder) {
                val vh = holder
                vh.bindViewSecondList(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        var secondListSize = 0
        var headers = 0
        var sumItems = 0

        if (secondListItem == null) return 0

        if (secondListItem != null){
            for (element in secondListItem) {
                headers += 1
                sumItems += element.products.size
            }
        }
        return if (sumItems > 0)
            headers + sumItems  // first list header, first list size, second list header , second list size, footer
        else
            0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View

        if (viewType === HEADER) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.check_card_header, parent, false)
            return ViewHolder.HeaderElementViewHolder(v)

        } else {
            // SECOND_LIST_ITEM_VIEW
            v = LayoutInflater.from(parent.context).inflate(R.layout.check_card, parent, false)
            return ViewHolder.SecondElementViewHolder(v)
        }
    }

    open class ViewHolder(var itemViews : View) : RecyclerView.ViewHolder(itemViews) {

        var txtxUsername: TextView? = null
        var iv_icon_image: ImageView? = null
        var txtProduct: TextView? = null
        var txtCantidad: TextView? = null
        var txtTotal: TextView? = null

        init {
            this.txtxUsername = itemViews.findViewById(R.id.txtxUsername)
            this.iv_icon_image = itemViews.findViewById(R.id.iv_icon_image)
            this.txtProduct = itemViews.findViewById(R.id.txtProduct)
            this.txtCantidad = itemViews.findViewById(R.id.txtCantidad)
            this.txtTotal = itemViews.findViewById(R.id.txtTotal)
        }

        fun bindViewSecondList(pos : Int) {
            var _pos = pos - 1
            var firstListSize = maping.get(1)!!.toInt()
            if ( _pos >  firstListSize  ) {
                txtProduct!!.text = secondListItem[1].products.get(_pos - (firstListSize+1)).title
                txtTotal!!.text = "2"
                Picasso.get().load(secondListItem[1].products.get(_pos - (firstListSize+1)).resource).into(iv_icon_image)
            }
            else{
                txtProduct!!.text = secondListItem[0].products.get(_pos ).title
                txtTotal!!.text = secondListItem[0].products.get(_pos).price.toString()
                txtCantidad!!.text = "1"
                Picasso.get().load(secondListItem[0].products.get(_pos).resource).into(iv_icon_image)
            }
        }

        fun bindViewHeaderList(pos : Int) {
            var firstListSize = maping.get(1)!!.toInt()
            if ( pos >=  firstListSize  ) {
                txtxUsername!!.text = secondListItem[1].name
            }
            else{
                txtxUsername!!.text = secondListItem[0].name
            }
        }

        class HeaderElementViewHolder(itemView: View) : ViewHolder(itemView)

        class SecondElementViewHolder(itemView: View) : ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        var firstListSize = 0
        var secondListSize = 0
        var headers = 0
        var map: HashMap<Int, String> = hashMapOf()
        if (secondListItem == null)
            return super.getItemViewType(position)
        if (secondListItem != null){
            for (element in secondListItem) {

                headers += 1
                map.set(headers, element.products.size.toString())
            }
        }
        maping = map
        if (headers > 0 ) {
            return if (position == 0)
                HEADER
            else if ( position == map.get(1)!!.toInt() + 1 )
                HEADER
            else
                FIRSTELEMENT
        }

        return super.getItemViewType(position)
    }
}