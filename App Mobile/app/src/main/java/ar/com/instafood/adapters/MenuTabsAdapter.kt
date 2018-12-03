package ar.com.instafood.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

open class MenuTabsAdapter(fm : FragmentManager, fragmentlist : ArrayList<Fragment>, titleList: ArrayList<String>) : FragmentStatePagerAdapter(fm) {
    var listFragment = arrayListOf<Fragment>()
    var listTitle = arrayListOf<String>()

    init {
        listFragment = fragmentlist
        listTitle = titleList
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return listFragment.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle.get(position)
    }

    open fun addfrag(fragment: Fragment, title: String){
        listFragment.add(fragment)
        listTitle.add(title)
    }
}