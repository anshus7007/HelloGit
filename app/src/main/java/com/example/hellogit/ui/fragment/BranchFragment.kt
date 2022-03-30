package com.example.hellogit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModelFactory.BranchViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.adapter.BranchAdapter
import com.example.hellogit.adapter.IssuesAdapter
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.BranchDB
import com.example.hellogit.db.db3.entity.Branch



class BranchFragment : Fragment() {

    lateinit var branch_recycler: RecyclerView
    lateinit var branchAdapter: BranchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_branch, container, false)
        branch_recycler=view.findViewById(R.id.branch_recycler)


        val database2 = BranchDB(requireContext())
        val repository2 = BranchRepository(database2)
        val factory2 = BranchViewModelFactory(repository2)
        val viewmodel2 = ViewModelProvider(this, factory2).get(BranchViewModel::class.java)


        val bundle = arguments
        val repo_id = bundle!!.getInt("repo_id")


        branchAdapter= BranchAdapter(requireContext(), mutableListOf(),viewmodel2)
        val layoutManager= LinearLayoutManager(context)
        branch_recycler.layoutManager = layoutManager
        branch_recycler.adapter = branchAdapter
        branch_recycler.addItemDecoration(
            DividerItemDecoration(
                branch_recycler.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )


        viewmodel2.getAllBranches(repo_id).observe(requireActivity(), Observer {
            branchAdapter!!.item= it as MutableList<Branch>

            branchAdapter!!.notifyDataSetChanged()
        })

        return view
    }



}