package com.example.hellogit.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModel.IssuesViewModel
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.GitViewModelFactory.IssuesViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.adapter.IssuesAdapter
import com.example.hellogit.adapter.RepoAdapter
import com.example.hellogit.db.db2.IssuesDB
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.util.ConnectionManager
import org.json.JSONException


class IssuesFragment : Fragment() {

    lateinit var issues_recycler:RecyclerView
    lateinit var issuesAdapter:IssuesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_issues, container, false)
        issues_recycler=view.findViewById(R.id.issues_recycler)


        val database1 = IssuesDB(requireContext())
        val repository1 = IssuesRepository(database1)
        val factory1 = IssuesViewModelFactory(repository1)
        val viewmodel1 = ViewModelProvider(this, factory1).get(IssuesViewModel::class.java)


        val bundle = arguments
        val repo_id = bundle!!.getInt("repo_id")


        issuesAdapter= IssuesAdapter(requireContext(), mutableListOf(),viewmodel1)
        val layoutManager= LinearLayoutManager(context)
        issues_recycler.layoutManager = layoutManager
        issues_recycler.adapter = issuesAdapter
        issues_recycler.addItemDecoration(
            DividerItemDecoration(
                issues_recycler.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )


        viewmodel1.getAllIssues(repo_id).observe(requireActivity(), Observer {
            issuesAdapter!!.item= it as MutableList<Issues>

            issuesAdapter!!.notifyDataSetChanged()
        })

        return  view
    }



}