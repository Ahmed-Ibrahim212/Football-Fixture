package com.example.footballpackage.ui.competitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballpackage.data.remote.dto.Competition
import com.example.footballpackage.databinding.CompetitionListItemBinding

class CompetitionsAdapter(private var list: List<Competition>) :
    ListAdapter<Competition, CompetitionsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        val binding: CompetitionListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(competition: Competition) = with(itemView) {
            binding.apply {
                leagueName.text = competition.name
                itemView.setOnClickListener{
                    val directions = CompetitionsFragmentDirections.actionCompetitionsFragmentToFixturesFragment()
                    findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CompetitionListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

object DiffCallback : DiffUtil.ItemCallback<Competition>() {
    override fun areItemsTheSame(
        oldItem: Competition,
        newItem: Competition
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Competition,
        newItem: Competition
    ): Boolean {
        return oldItem == newItem
    }
}

