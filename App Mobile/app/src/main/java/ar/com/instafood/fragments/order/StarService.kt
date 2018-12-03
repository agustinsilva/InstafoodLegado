package ar.com.instafood.fragments.order

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ar.com.instafood.activities.R

class StarService {


    fun init(view: View, context: Context) {
        val labels = mutableMapOf<String, String?>()
        labels["1"] = context.resources.getString(R.string.lbl_order_score_1)
        labels["2"] = context.resources.getString(R.string.lbl_order_score_2)
        labels["3"] = context.resources.getString(R.string.lbl_order_score_3)
        labels["4"] = context.resources.getString(R.string.lbl_order_score_4)
        labels["5"] = context.resources.getString(R.string.lbl_order_score_5)

        val goldStars : MutableList<ImageView> = mutableListOf()
        goldStars.add(view.findViewById<View>(R.id.img_gold_star_1) as ImageView)
        goldStars.add(view.findViewById<View>(R.id.img_gold_star_2) as ImageView)
        goldStars.add(view.findViewById<View>(R.id.img_gold_star_3) as ImageView)
        goldStars.add(view.findViewById<View>(R.id.img_gold_star_4) as ImageView)
        goldStars.add(view.findViewById<View>(R.id.img_gold_star_5) as ImageView)

        for (star in goldStars) {
            star.setOnClickListener { _ ->
                if (!PreferenceUtils.getScoreLocked(context)) {
                    goldStars.forEach { it.visibility = View.INVISIBLE }
                    starsToTurnOn(goldStars, star).forEach { it.visibility = View.VISIBLE }
                    view.findViewById<TextView>(R.id.lbl_close_score_description).text = labels.get(star.contentDescription)
                }
            }
        }

        val greyStars : MutableList<ImageView> = mutableListOf()
        greyStars.add(view.findViewById<View>(R.id.img_grey_star_1) as ImageView)
        greyStars.add(view.findViewById<View>(R.id.img_grey_star_2) as ImageView)
        greyStars.add(view.findViewById<View>(R.id.img_grey_star_3) as ImageView)
        greyStars.add(view.findViewById<View>(R.id.img_grey_star_4) as ImageView)
        greyStars.add(view.findViewById<View>(R.id.img_grey_star_5) as ImageView)

        for (star in greyStars) {
            star.setOnClickListener { _ ->
                if (!PreferenceUtils.getScoreLocked(context)) {
                    goldStars.forEach { it.visibility = View.INVISIBLE }
                    starsToTurnOn(goldStars, star).forEach { it.visibility = View.VISIBLE }
                    view.findViewById<TextView>(R.id.lbl_close_score_description).text = labels.get(star.contentDescription)
                }
            }
        }

    }

    private fun starsToTurnOn(goldStars :MutableList<ImageView>, selectedStar: ImageView): List<ImageView> {
        val starsToTurnOn = ArrayList<ImageView>()
        val selectedStarIndex = Integer.valueOf(selectedStar.contentDescription.toString())
        for (i in 0 until selectedStarIndex) {
            starsToTurnOn.add(goldStars[i])
        }
        return starsToTurnOn
    }

}