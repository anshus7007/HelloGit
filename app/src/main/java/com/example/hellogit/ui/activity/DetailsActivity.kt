package com.example.hellogit.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.hellogit.GitRepository.BranchRepository
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitRepository.IssuesRepository
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModel.IssuesViewModel
import com.example.hellogit.GitViewModelFactory.BranchViewModelFactory
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.GitViewModelFactory.IssuesViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.db.db1.dao.GitDB
import com.example.hellogit.db.db2.IssuesDB
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.BranchDB
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.ui.fragment.BranchFragment
import com.example.hellogit.ui.fragment.IssuesFragment
import com.example.hellogit.util.ConnectionManager
import com.google.android.material.tabs.TabLayout
import org.json.JSONException

class DetailsActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var frameLayout: FrameLayout? = null
    var fragment: Fragment? = null
    var fragmentManager: FragmentManager? = null
//    lateinit var issuesAdapter:IssuesAdapter
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


        findViewById<ImageView>(R.id.backToMain).setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        val database = GitDB(this)
        val repository = GitRepository(database)
        val factory = GitViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, factory).get(GitView::class.java)


        val database1 = IssuesDB(this)
        val repository1 = IssuesRepository(database1)
        val factory1 = IssuesViewModelFactory(repository1)
        val viewmodel1 = ViewModelProvider(this, factory1).get(IssuesViewModel::class.java)


        val database2 = BranchDB(this)
        val repository2 = BranchRepository(database2)
        val factory2 = BranchViewModelFactory(repository2)
        val viewmodel2 = ViewModelProvider(this, factory2).get(BranchViewModel::class.java)


        val intent = intent
        val curr_id = intent.getIntExtra("repo_Id",0)
        val description = intent.getStringExtra("description")
        val repo_name = intent.getStringExtra("repo_Name")
        val owner_Name = intent.getStringExtra("owner_Name")


        fetchIssues(curr_id!!,owner_Name!!,repo_name!!,viewmodel1,factory1)
        fetchBranch(curr_id,owner_Name,repo_name,viewmodel2,factory2)

        val git_repo= Git(curr_id!!,repo_name!!,owner_Name!!,description!!)

        repo_description.text=description
        val bundle=Bundle()
        bundle.putInt("repo_id",curr_id)
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
        fragment!!.arguments=bundle
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

                fragment!!.arguments=bundle
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
    fun fetchIssues(repo_id:Int,owner: String, repo:String, viewModel: IssuesViewModel, viewModelsss: IssuesViewModelFactory) {


        if (ConnectionManager().checkConnectivity(this as Context)) {

            try {

                val queue = Volley.newRequestQueue(this as Context)



                val url = "https://api.github.com/repos/$owner/$repo/issues?state=open"

                val jsonObjectRequest = object : JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {


                        for (i in 0 until it.length())
                        {
                            val json=it.getJSONObject(i)
                            val title=json.getString("title")
                            val coomits_id=json.getInt("id")

                            val jsonInside=json.getJSONObject("user")
                            val user=jsonInside.getString("login")
                            val avtar=jsonInside.getString("avatar_url")
                            val issuesEntity= Issues(coomits_id,repo_id,title,user,avtar)
                            viewModel.insert(issuesEntity)

                        }




                    },
                    Response.ErrorListener {

                        Toast.makeText(
                            this as Context,
                            "Some Error occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()

                        headers["Content-type"] = "application/json"

                        return headers
                    }
                }

                queue.add(jsonObjectRequest)

            } catch (e: JSONException) {
                Toast.makeText(
                    this as Context,
                    "Some Unexpected error occured!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {

            val alterDialog = androidx.appcompat.app.AlertDialog.Builder(this as Context)
            alterDialog.setTitle("No Internet")
            alterDialog.setMessage("Internet Connection can't be establish!")
            alterDialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
                startActivity(settingsIntent)
            }

            alterDialog.setNegativeButton("Exit") { text, listener ->
                finishAffinity()//closes all the instances of the app and the app closes completely
            }
            alterDialog.setCancelable(false)

            alterDialog.create()
            alterDialog.show()
        }

    }
    fun fetchBranch(repo_id:Int,owner: String, repo:String, viewModel: BranchViewModel, viewModelsss: BranchViewModelFactory) {


        if (ConnectionManager().checkConnectivity(this as Context)) {

            try {

                val queue = Volley.newRequestQueue(this as Context)



                val url = "https://api.github.com/repos/$owner/$repo/branches"

                val jsonObjectRequest = object : JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {


                        for (i in 0 until it.length())
                        {
                            val json=it.getJSONObject(i)
                            val branchName=json.getString("name")

                            val jsonInside=json.getJSONObject("commit")
                            val sha=jsonInside.getString("sha")
                            val branchEntity= Branch(repo_id,owner,repo,branchName,sha)
                            viewModel.insert(branchEntity)

                        }




                    },
                    Response.ErrorListener {

                        Toast.makeText(
                            this as Context,
                            "Some Error occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()

                        headers["Content-type"] = "application/json"

                        return headers
                    }
                }

                queue.add(jsonObjectRequest)

            } catch (e: JSONException) {
                Toast.makeText(
                    this as Context,
                    "Some Unexpected error occured!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {

            val alterDialog = androidx.appcompat.app.AlertDialog.Builder(this as Context)
            alterDialog.setTitle("No Internet")
            alterDialog.setMessage("Internet Connection can't be establish!")
            alterDialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
                startActivity(settingsIntent)
            }

            alterDialog.setNegativeButton("Exit") { text, listener ->
                finishAffinity()//closes all the instances of the app and the app closes completely
            }
            alterDialog.setCancelable(false)

            alterDialog.create()
            alterDialog.show()
        }

    }


}