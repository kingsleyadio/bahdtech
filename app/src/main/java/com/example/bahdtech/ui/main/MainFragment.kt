package com.example.bahdtech.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bahdtech.R
import kotlinx.android.synthetic.main.main_fragment.*
import ng.kingsley.android.recyclerview.adapter.AutomoreAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()
    private val userAdapter by lazy { UserAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            val automoreAdapter = AutomoreAdapter(userAdapter, false)
            automoreAdapter.setOnMoreListener { viewModel.loadMore() }
            adapter = automoreAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        swipeRefresh.apply {
            setOnRefreshListener { viewModel.refresh() }
            isRefreshing = true
        }

        viewModel.users.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = false
            when (it) {
                MainViewModel.LoadNotify.Refresh -> {
                    userAdapter.notifyDataSetChanged()
                    with(recyclerView.adapter as AutomoreAdapter) {
                        moreEnabled = true
                    }
                }
                is MainViewModel.LoadNotify.LoadMore -> {
                    userAdapter.notifyItemRangeInserted(it.startPosition, it.size)
                    with(recyclerView.adapter as AutomoreAdapter) {
                        setMoreCompleted()
                        moreEnabled = true
                    }
                }
            }
        }
    }
}
