package com.example.sequencerecord.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sequencerecord.R
import com.example.sequencerecord.db.ScoreEntry


class RecyclerAdapter(val listener : RowClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var items = ArrayList<ScoreEntry?>()
    fun setListData(data: ArrayList<ScoreEntry?>){
        this.items = data
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
           val binding = LayoutInflater.from(viewGroup.context).inflate(R.layout.recyclerview_tile,viewGroup , false)

        return ViewHolder(binding,listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemView.setOnClickListener {
            listener.onEditClickListener(items[i])
        }
        viewHolder.bind(items[i])
    }

    override fun getItemCount(): Int {
        return items.size
    }

class ViewHolder(view: View,val listener: RowClickListener): RecyclerView.ViewHolder(view){

    var rvGreenScore : TextView = view.findViewById(R.id.rvGreenScore)
    var rvBlueScore : TextView = view.findViewById(R.id.rvBlueScore)
    var rvGameType : TextView = view.findViewById(R.id.rvGameType)
    var rvDate : TextView = view.findViewById(R.id.rvDate)
    val rvEdit : ImageButton = view.findViewById(R.id.rvEdit)
    val rvDelete : ImageButton = view.findViewById(R.id.rvDelete)


     fun bind(scoreData: ScoreEntry?){

        rvGreenScore.text  = scoreData?.green.toString()
        rvBlueScore.text = scoreData?.blue.toString()
        rvGameType.text = scoreData?.type
         rvDate.text = scoreData?.dateGame
         rvDelete.setOnClickListener {
             listener.onDeleteScoreClickListener(scoreData)
         }
         rvEdit.setOnClickListener {
             listener.onEditClickListener(scoreData)

         }
            }


        }
interface RowClickListener{
    fun onDeleteScoreClickListener(score : ScoreEntry?)
    fun onEditClickListener(score: ScoreEntry?)
}


}
