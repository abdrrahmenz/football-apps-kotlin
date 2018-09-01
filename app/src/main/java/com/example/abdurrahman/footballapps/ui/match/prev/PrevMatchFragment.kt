package com.example.abdurrahman.footballapps.ui.match.prev

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
import android.widget.*
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.color.colorAccent
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailMatchActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class PrevMatchFragment : Fragment(), AnkoComponent<Context>, PrevMatchView {
    private var events: MutableList<Events> = mutableListOf()

    private lateinit var presenter: PrevMatchPresenter
    private lateinit var adapter: PrevMatchAdapter
    private lateinit var listPrevMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position){
                    0 -> {
                        leagueName = spinner.selectedItem.toString().replace("English Premier League", "4328")
                        presenter.getPrevMatchList(leagueName)
                    }
                    1 -> {
                        leagueName = spinner.selectedItem.toString().replace("English League Championship", "4329")
                        presenter.getPrevMatchList(leagueName)
                    }
                    2 -> {
                        leagueName = spinner.selectedItem.toString().replace("German Bundesliga", "4331")
                        presenter.getPrevMatchList(leagueName)
                    }
                    3 -> {
                        leagueName = spinner.selectedItem.toString().replace("Italian Serie A", "4332")
                        presenter.getPrevMatchList(leagueName)
                    }
                    4 -> {
                        leagueName = spinner.selectedItem.toString().replace("French Ligue 1", "4334")
                        presenter.getPrevMatchList(leagueName)
                    }
                    5 -> {
                        leagueName = spinner.selectedItem.toString().replace("Spanish La Liga", "4335")
                        presenter.getPrevMatchList(leagueName)
                    }
                }
            }
        }
        
        adapter = PrevMatchAdapter(events) {
            ctx.startActivity<DetailMatchActivity>("detail" to it)
            toast(it.teamHomeName.toString())
        }
        listPrevMatch.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = PrevMatchPresenter(this,request, gson)

        swipeRefresh.onRefresh {
            presenter.getPrevMatchList(leagueName)
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            background = ColorDrawable(Color.parseColor("#efeded"))
            
            spinner = spinner{
                id = R.id.spinner
            }
            
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listPrevMatch = recyclerView {
                        id = R.id.listPrevMatch
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showPrevMatch(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
