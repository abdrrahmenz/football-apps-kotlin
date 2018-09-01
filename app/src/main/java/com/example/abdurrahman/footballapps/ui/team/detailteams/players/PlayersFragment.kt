package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abdurrahman.footballapps.R
import org.jetbrains.anko.support.v4.toast

class PlayersFragment : Fragment() {

    private var idTeams: String = ""

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player,container, false)

        val bindData = arguments
        idTeams = bindData?.getString(KEY_TEAMS) ?: "idTeams"
        toast(idTeams)
//        view.text_desc_overview.text = idTeams
        return view
    }
}