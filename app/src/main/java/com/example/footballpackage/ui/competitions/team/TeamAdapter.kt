package com.example.footballpackage.ui.competitions.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.footballpackage.data.remote.dto.Team
import com.example.footballpackage.R
import com.example.footballpackage.databinding.TeamFragmentListBinding

class TeamAdapter(private var list:  List<Team>) :
    ListAdapter<Team, TeamAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(val binding: TeamFragmentListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(team.crestUrl)
                    .placeholder(R.drawable.ic_baseline_soccer)
                    .into(imgTeamLogo)

                tvTeamName.text = team.name
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TeamFragmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

object DiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(
        oldItem: Team,
        newItem: Team
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Team,
        newItem: Team
    ): Boolean {
        return oldItem == newItem
    }
}