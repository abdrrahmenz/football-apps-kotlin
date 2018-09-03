package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Player
import com.example.abdurrahman.footballapps.ui.team.detailteams.players.detailplayers.DetailPlayersActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class PlayersFragment : Fragment(), AnkoComponent<Context>, PlayersView {

    private var idTeams: String = ""
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var listPlayer : RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: PlayersAdapter
    private lateinit var presenter: PlayerPresenter

    companion object {

        const val KEY_TEAMS = "KEY_ID_TEAMS"
        fun newInstance(descTeams: String): PlayersFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAMS, descTeams)

            val playersFragment = PlayersFragment()
            playersFragment.arguments = bindData
            return playersFragment
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bindData = arguments
        idTeams = bindData?.getString(KEY_TEAMS) ?: "idTeams"
        toast(idTeams)

        adapter = PlayersAdapter(players) {
            ctx.startActivity<DetailPlayersActivity>("player_detail" to it)
        }
        listPlayer.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(idTeams)

        swipeRefresh.onRefresh {
            presenter.getPlayerList(idTeams)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        id = R.id.listPlayer
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showPlayerList(player: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(player)
        toast(player.size.toString())
        adapter.notifyDataSetChanged()
    }
}