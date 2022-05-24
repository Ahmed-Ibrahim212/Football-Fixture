package com.example.footballpackage.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.footballfixtures.data.mappers.TableDomainMapper
import com.example.footballpackage.data.remote.dto.Table
import com.example.footballpackage.databinding.FragmentTableBinding
import com.example.footballpackage.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
    private val tableViewModel: TableViewModel by viewModels()
    private lateinit var tablesRVAdapter: TablesRVAdapter
    private var competitionId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        competitionId = intent?.getIntExtra("competitionId", 0)

        tableViewModel.getCompetitionTables(competitionId)
        tableViewModel.getTableListFromDatabase(competitionId)


        tablesRVAdapter = TablesRVAdapter()
        observeTableFromDatabase()
        observeTableResponse()

//        // implement swipe to refresh
//        binding.competitionFragmentSwipeRefreshLayout.setOnRefreshListener {
//            tableViewModel.getCompetitionTables(competitionId)
//            binding.competitionFragmentSwipeRefreshLayout.isRefreshing = false
//        }
    }


    private fun observeTableResponse() {
        tableViewModel.competitionsTable.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    val tableResponse = it.value.standings?.get(0)?.table

                    tableViewModel.saveCompetitionTable((
                            TableDomainMapper(tableResponse,competitionId)).tableDomain)
                }

                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    Snackbar.make(binding.tablesRv, it.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()                }

                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeTableFromDatabase() {
        tableViewModel.savedTable.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    val tablesList: List<Table> = it.value
                    val tablesRv = binding.tablesRv
                    tablesRv.adapter = tablesRVAdapter
                    tablesRVAdapter.submitList(tablesList)
                }

                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    Snackbar.make(binding.tablesRv, it.error, Snackbar.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }
}