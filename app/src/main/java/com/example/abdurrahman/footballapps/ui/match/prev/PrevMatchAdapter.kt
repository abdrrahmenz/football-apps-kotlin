package com.example.abdurrahman.footballapps.ui.match.prev

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.id.*
import com.example.abdurrahman.footballapps.model.Events
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

class PrevMatchAdapter(private val events: List<Events>, private val listener: (Events) -> Unit)
    : RecyclerView.Adapter<PrevMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevMatchViewHolder {
        return PrevMatchViewHolder(PrevMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: PrevMatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
}

class PrevMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                }
                gravity = center
                orientation = LinearLayout.VERTICAL
                background = ColorDrawable(Color.parseColor("#ffffff"))

                textView {
                    id = tvDateEvent
                    textSize = 16f
                    textColor = Color.GREEN
                }.lparams{
                    topMargin = dip(15)
                    gravity = Gravity.CENTER
                }

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent){
                        padding = dip(15)
                    }

                    textView {
                        id = tvTeamHomeName
                        textSize = 18f
                        ellipsize = TextUtils.TruncateAt.END
                        maxLines = 1
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.tvTeamHomeScore)
                    }

                    textView {
                        id = tvTeamHomeScore
                        textSize = 18f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.textViewVs)
                    }

                    textView {
                        id = textViewVs
                        textSize = 16f
                    }.lparams{
                        centerHorizontally()
                    }

                    textView {
                        id = tvTeamAwayScore
                        textSize = 18f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.textViewVs)
                    }

                    textView {
                        id = tvTeamAwayName
                        textSize = 18f
                        ellipsize = TextUtils.TruncateAt.END
                        maxLines = 1
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.tvTeamAwayScore)
                    }

                }
            }
        }
    }

}

class PrevMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvDate : TextView = view.find(tvDateEvent)
    private val tvVS : TextView = view.find(textViewVs)
    private val tvhomeScore : TextView = view.find(tvTeamHomeScore)
    private val tvawayScore : TextView = view.find(tvTeamAwayScore)
    private val tvhomeTeam : TextView = view.find(tvTeamHomeName)
    private val tvawayTeam : TextView = view.find(tvTeamAwayName)

    @SuppressLint("SetTextI18n")
    fun bindItem(event: Events, listener: (Events) -> Unit) {
        val dateFormatServer = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatCustom = SimpleDateFormat("E, dd MMM yyyy")
        val date = dateFormatServer.parse(event.dateEvent)
        val strDate = dateFormatCustom.format(date)
        tvDate.text = strDate
        tvhomeScore.text = event.teamHomeScore
        tvawayScore.text = event.teamAwayScore
        tvhomeTeam.text = event.teamHomeName
        tvawayTeam.text = event.teamAwayName
        tvVS.text = "vs"
        itemView.onClick { listener(event) }
    }
}
