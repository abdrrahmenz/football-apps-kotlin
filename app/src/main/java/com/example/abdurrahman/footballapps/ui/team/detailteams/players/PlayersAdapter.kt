package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayersAdapter(private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return player.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }
}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.player_image
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                    ellipsize = TextUtils.TruncateAt.END
                    maxLines = 1
                }.lparams {
                    width = dip(150)
                    margin = dip(15)
                }

                textView {
                    id = R.id.player_position
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }
            }
        }
    }
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val playerImage: ImageView = view.find(R.id.player_image)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPosition: TextView = view.find(R.id.player_position)

    fun bindItem(player: Player, listener: (Player) -> Unit){
        Picasso.get().load(player.strCutOut).into(playerImage)
        playerName.text = player.strPlayer
        playerPosition.text = player.strPosition
        itemView.onClick {
            listener(player)
        }
    }
}
