package com.example.hellogit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.adapter.RepoAdapter
import com.example.hellogit.db.GitDB
import com.example.hellogit.db.entity.Git
import androidx.recyclerview.widget.RecyclerView as RecyclerView1


class MainActivity : AppCompatActivity() {
    lateinit var addRepoIcon: ImageView
    lateinit var addRepo: Button
    lateinit var repo_recycler: RecyclerView1
    lateinit var repo_adapter: RepoAdapter
    lateinit var AddButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addRepoIcon=findViewById(R.id.addRepoIcon)
        addRepo=findViewById(R.id.AddRepo)
        repo_recycler=findViewById(R.id.repo_recycler)
        AddButton=findViewById(R.id.AddRepo)
        val database = GitDB(this)
        val repository = GitRepository(database)
        val factory = GitViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, factory).get(GitView::class.java)

        repo_adapter= RepoAdapter(this, mutableListOf(),viewmodel)
        val layoutManager= LinearLayoutManager(this)
        repo_recycler.layoutManager = layoutManager
        repo_recycler.adapter = repo_adapter
        repo_recycler.addItemDecoration(
            DividerItemDecoration(
                repo_recycler.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewmodel.getAllRepos().observe(this!!, Observer {
            repo_adapter.item= it as MutableList<Git>
            if(it.size>=1) {
                findViewById<TextView>(R.id.tv1).visibility = View.GONE
                findViewById<Button>(R.id.AddRepo).visibility= View.GONE

            }
            else
            {
                findViewById<TextView>(R.id.tv1).visibility = View.VISIBLE
                findViewById<Button>(R.id.AddRepo).visibility= View.VISIBLE
            }
//            repo_adapter.size=it.size
            repo_adapter.notifyDataSetChanged()
        })
        addRepoIcon.setOnClickListener {
            val intent=(Intent(this,CustomDialogFragment::class.java))
            startActivity(intent)
        }
        if(findViewById<Button>(R.id.AddRepo).visibility== View.VISIBLE)
        {
            AddButton.setOnClickListener{
                val intent=(Intent(this,CustomDialogFragment::class.java))
                startActivity(intent)
            }
        }

    }
}