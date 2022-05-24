package com.example.footballpackage.ui.competitions.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballpackage.data.mappers.TeamDomainMapper
import com.example.footballpackage.data.remote.dto.Team
import com.example.footballfixtures.presentation.ui.competitions.team.TeamViewModel
import com.example.footballpackage.utils.Resource
import com.example.footballpackage.databinding.FragmentTeamBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!
    private val teamViewModel: TeamViewModel by viewModels()
    lateinit var teamAdapter: TeamAdapter
    private var competitionId: Int? = 0
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.teamsRecyclerView
        val intent = activity?.intent
        competitionId = intent?.getIntExtra("competitionId", 0)
        teamViewModel.getCompetitions(competitionId)
        teamViewModel.getTeamListFromDatabase(competitionId)

        observeSavedTeams()
        observeTeams()

        // implement swipe to refresh
        binding.competitionFragmentSwipeRefreshLayout.setOnRefreshListener {
            teamViewModel.getCompetitions(competitionId)
            binding.competitionFragmentSwipeRefreshLayout.isRefreshing = false
        }
    }

    // save to room database
    private fun observeTeams() {
        teamViewModel.team.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val teamsList: List<Team>? = it.value.teams

                   teamViewModel.saveTeam((TeamDomainMapper(teamsList,competitionId)).teamDomain)
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.teamsRecyclerView, it.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE               }
            }
        }
    }

    // read from room database
    private fun observeSavedTeams() {
        teamViewModel.savedTeam.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val teamsList: List<Team> = it.value
                    setUpRecyclerView(it.value)
                    val teamsRv = binding.teamsRecyclerView
                    teamsRv.adapter = teamAdapter
                    teamAdapter.submitList(teamsList)
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.teamsRecyclerView, it.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE               }
            }
        }
    }

//    //setting up the recyclerview with the adapterclass
    fun setUpRecyclerView(list: List<Team>) { //setting the recyclerView to gridlayout
    recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    teamAdapter = TeamAdapter(list)
    recyclerView.adapter = TeamAdapter(list)
}

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}