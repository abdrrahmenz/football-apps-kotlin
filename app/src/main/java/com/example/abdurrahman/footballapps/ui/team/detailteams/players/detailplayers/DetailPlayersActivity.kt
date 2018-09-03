package com.example.abdurrahman.footballapps.ui.team.detailteams.players.detailplayers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayersActivity : AppCompatActivity() {

    private lateinit var players: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        players = intent.getParcelableExtra("player_detail")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Player"

        Picasso.get().load(players.strBackGround).placeholder(R.drawable.load).error(R.drawable.error).into(imgBgPlayer)
        textHeight.text = players.strHeight
        textWeight.text = players.strWeight
        textDescPlayer.text = players.strDescription
        textPositionPlayer.text = players.strPosition
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}