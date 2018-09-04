package com.example.abdurrahman.footballapps.ui.match

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.ui.match.searchmatch.SearchMatchActivity
import com.example.abdurrahman.footballapps.ui.match.next.NextMatchFragment
import com.example.abdurrahman.footballapps.ui.match.prev.PrevMatchFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.viewPager
import java.util.ArrayList

class MatchFragment : Fragment(), AnkoComponent<Context>{

    private lateinit var myTabLayout : TabLayout
    private lateinit var myViewPager : ViewPager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        val mPagerAdapter = MatchPagerAdapter(childFragmentManager)
        mPagerAdapter.addFrag(NextMatchFragment(), "Next")
        mPagerAdapter.addFrag(PrevMatchFragment(), "Past")

        // Set up the ViewPager with the sections adapter.
        myViewPager.adapter = mPagerAdapter

        myTabLayout.setupWithViewPager(myViewPager)
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        coordinatorLayout {
            lparams(matchParent, matchParent)

            appBarLayout {
                lparams(matchParent, wrapContent){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        elevation = 0f
                    }
                }


                myTabLayout = themedTabLayout(R.style.ThemeOverlay_AppCompat_Dark) {
                    id = R.id.tabMatch
                    lparams(matchParent, wrapContent){
                    }
                }
            }

            myViewPager = viewPager {
                id = R.id.viewpager
            }.lparams(matchParent, matchParent)
            (myViewPager.layoutParams as CoordinatorLayout.LayoutParams).behavior = AppBarLayout.ScrollingViewBehavior()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        menu?.findItem(R.id.search_team)?.isVisible = false
        inflater?.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.search_match -> {
                startActivity<SearchMatchActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    internal inner class MatchPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
