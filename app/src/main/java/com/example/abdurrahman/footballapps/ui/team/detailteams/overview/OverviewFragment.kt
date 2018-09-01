package com.example.abdurrahman.footballapps.ui.team.detailteams.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abdurrahman.footballapps.R
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment : Fragment() {

    private var descTeams: String = ""

    companion object {
        const val KEY_TEAMS = "KEY_DESC_TEAMS"

        fun newInstance(descTeams: String): OverviewFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAMS, descTeams)

            val overviewFragment = OverviewFragment()
            overviewFragment.arguments = bindData
            return overviewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overview,container, false)

        val bindData = arguments
        descTeams = bindData?.getString(KEY_TEAMS) ?: "descTeams"
        view.text_desc_overview.text = descTeams

        return view
    }
}