package com.example.sequencerecord.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sequencerecord.R
import com.example.sequencerecord.databinding.RecyclerviewTileBinding
import com.example.sequencerecord.db.ScoreEntry


class RecyclerAdapter(val listener : RowClickListener) : androidx.recyclerview.widget.ListAdapter<ScoreEntry ,RecyclerAdapter.ViewHolder>(DiffCallBack()){
    companion object{
      class DiffCallBack : DiffUtil.ItemCallback<ScoreEntry>() {
          override fun areItemsTheSame(oldItem: ScoreEntry, newItem: ScoreEntry): Boolean {
              return oldItem.id == newItem.id
          }

          override fun areContentsTheSame(oldItem: ScoreEntry, newItem: ScoreEntry): Boolean {
              return oldItem == newItem
          }


      }
    }
    lateinit var binding : RecyclerviewTileBinding


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
           val inflater = LayoutInflater.from(viewGroup.context)
        binding = DataBindingUtil.inflate(inflater,R.layout.recyclerview_tile,viewGroup,false)

        return ViewHolder(binding,listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.bind(getItem(i))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

class ViewHolder(val binding: RecyclerviewTileBinding,val listener: RowClickListener): RecyclerView.ViewHolder(binding.root){
     fun bind(scoreData: ScoreEntry?){
         binding.scoreEntry = scoreData
             binding.rvDelete.setOnClickListener {
             listener.onDeleteScoreClickListener(scoreData)
         }
         binding.rvEdit.setOnClickListener {
             listener.onEditClickListener(scoreData)

         }
            }


        }
interface RowClickListener{
    fun onDeleteScoreClickListener(score : ScoreEntry?)
    fun onEditClickListener(score: ScoreEntry?)
}


}
