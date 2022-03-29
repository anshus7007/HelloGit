package com.example.hellogit.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.db.GitDB
import com.example.hellogit.db.entity.Git
import com.example.hellogit.ui.fragment.BranchFragment
import com.example.hellogit.ui.fragment.IssuesFragment
import com.google.android.material.tabs.TabLayout

class DetailsActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var frameLayout: FrameLayout? = null
    var fragment: Fragment? = null
    var fragmentManager: FragmentManager? = null
    var fragmentTransaction: FragmentTransaction? = null
    lateinit var repo_description:TextView
    lateinit var deleteRepo:ImageView
    lateinit var openBrowser:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        repo_description=findViewById(R.id.repo_description)
        deleteRepo=findViewById(R.id.Delete)
        openBrowser=findViewById(R.id.OpenBrowser)

        val database = GitDB(this)
        val repository = GitRepository(database)
        val factory = GitViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, factory).get(GitView::class.java)


        val intent = intent
        val curr_id = intent.getIntExtra("repo_Id",0)
        val description = intent.getStringExtra("description")
        val repo_name = intent.getStringExtra("repo_Name")

        val owner_Name = intent.getStringExtra("owner_Name")
        val git_repo= Git(curr_id!!,repo_name!!,owner_Name!!,description!!)
        repo_description.text=description
        fragment = BranchFragment()
        deleteRepo.setOnClickListener {
            viewmodel.delete(git_repo)
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        openBrowser.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/$owner_Name/$repo_name")
            startActivity(openURL)
        }
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.replace(R.id.frameLayout, fragment!!)
        fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction!!.commit()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // creating cases for fragment
                when (tab.position) {
                    0 -> fragment = BranchFragment()
                    1 -> fragment = IssuesFragment()

                }
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frameLayout, fragment!!)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }
}