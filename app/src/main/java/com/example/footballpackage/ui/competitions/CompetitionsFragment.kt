package com.example.footballpackage.ui.competitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.footballpackage.utils.Resource
import com.example.footballpackage.data.remote.dto.Competition
import com.example.footballpackage.databinding.FragmentCompetitionListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionsFragment : Fragment() {
    private var _binding : FragmentCompetitionListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CompetitionsViewModel by viewModels()
    lateinit var competitionsAdapter: CompetitionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompetitionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCompetitionsListFromDatabase()
        viewModel.getCompetitions()


        saveCompetitionsToDatabase()
        getSavedCompetitionsFromDatabase()

        // implement swipe to refresh
        binding.competitionFragmentSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getCompetitions()
            binding.competitionFragmentSwipeRefreshLayout.isRefreshing = false
        }
    }


    private fun saveCompetitionsToDatabase() {
        //observe livedata to get list of competitions
        viewModel.competitions.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.footballProgressBar.visibility = View.GONE
                    viewModel.saveCompetitionToDatabase(it.value.competitions)
                }

                is Resource.Error -> {
                    binding.footballProgressBar.visibility = View.GONE
                    Snackbar.make(binding.footballListRecyclerView, it.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                is Resource.Loading -> {
                    binding.footballProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getSavedCompetitionsFromDatabase() {
        viewModel.savedCompetitions.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.footballProgressBar.visibility = View.GONE
                    val competitionsList: List<Competition> = it.value
                    val competitionRv = binding.footballListRecyclerView
                    competitionsAdapter = CompetitionsAdapter(competitionsList).apply {
                        this.submitList(competitionsList)
                    }
                    competitionRv.adapter = competitionsAdapter
                }

                is Resource.Error -> {
                    binding.footballProgressBar.visibility = View.GONE
                    Snackbar.make(binding.footballListRecyclerView, it.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                is Resource.Loading -> {
                    binding.footballProgressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}