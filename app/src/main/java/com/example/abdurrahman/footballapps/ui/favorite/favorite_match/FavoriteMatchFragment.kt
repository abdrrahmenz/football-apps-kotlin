package com.example.abdurrahman.footballapps.ui.favorite.favorite_match

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.database.database
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.model.Favorite
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteMatchFragment : Fragment(), AnkoComponent<Context> {

    private var favoritesMatch: MutableList<Events> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var listFavoriteMatch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favoritesMatch) {
            ctx.startActivity<DetailMatchActivity>("detail" to it)
        }

        listFavoriteMatch.adapter = adapter

        showFavorite()

        swipeRefresh.onRefresh {
            favoritesMatch.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<Events>())
            favoritesMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            background = ColorDrawable(Color.parseColor("#efeded"))

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listFavoriteMatch = recyclerView {
                        id = R.id.listFavoriteMatch
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }

    }
}