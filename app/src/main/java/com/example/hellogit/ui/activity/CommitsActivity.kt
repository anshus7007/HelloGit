package com.example.hellogit.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.hellogit.GitRepository.CommitRepository
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.CommitViewModel
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModel.IssuesViewModel
import com.example.hellogit.GitViewModelFactory.CommitViewModelFactory
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.GitViewModelFactory.IssuesViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.adapter.CommitsAdapter
import com.example.hellogit.adapter.RepoAdapter
import com.example.hellogit.db.db1.dao.GitDB
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db4.CommitsDB
import com.example.hellogit.db.db4.entity.CommitsEntity
import com.example.hellogit.util.ConnectionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

class CommitsActivity : AppCompatActivity() {

    lateinit var commits_recycler:RecyclerView
    lateinit var commits_adapter:CommitsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)
        val intent=Intent()
        val sha=intent.getStringExtra("sha")
        val branchName=intent.getStringExtra("branchName")
        val repo_name=intent.getStringExtra("repo_name")
        val owner=intent.getStringExtra("owner")
        val repo_id=intent.getIntExtra("repo_id",0)
        val branch_id=intent.getIntExtra("branch_id",0)
        commits_recycler=findViewById(R.id.commits_recycler)

        findViewById<ImageView>(R.id.backToBranches).setOnClickListener {
            val intent=Intent(this,DetailsActivity::class.java)
            startActivity(intent)
            finish()
        }


        print("??????????////////////////////////$sha////////////////////////////////////////???????")
        findViewById<TextView>(R.id.Branch__Name).text=branchName
//

        val database4 = CommitsDB(this)
        val repository4 = CommitRepository(database4)
        val factory4 = CommitViewModelFactory(repository4)
        val viewmodel4 = ViewModelProvider(this, factory4).get(CommitViewModel::class.java)
        if (sha != null) {
            fetchCommits(repo_id,owner!!,repo_name!!,sha,branch_id, viewmodel4,factory4)
        }

        commits_adapter= CommitsAdapter(this, mutableListOf(),viewmodel4)
        val layoutManager= LinearLayoutManager(this)
        commits_recycler.layoutManager = layoutManager
        commits_recycler.adapter = commits_adapter
        commits_recycler.addItemDecoration(
            DividerItemDecoration(
                commits_recycler.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewmodel4.getAllCommits(repo_id,branch_id).observe(this!!, Observer {
            commits_adapter.item= it as MutableList<CommitsEntity>

            commits_adapter.notifyDataSetChanged()
        })






    }
    fun fetchCommits(repo_id:Int, owner: String, repo:String,sha:String,branch_id:Int, viewModel: CommitViewModel, viewModelsss: CommitViewModelFactory) {


        if (ConnectionManager().checkConnectivity(this as Context)) {

            try {

                val queue = Volley.newRequestQueue(this as Context)


                //val restaurantId:String=""

                val url = "https://api.github.com/repos/$owner/$repo/commits/$sha"

                val jsonObjectRequest = object : JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {
                        println("********************************************HelloResponseeeeee is $it")


                        for (i in 0 until it.length())
                        {
                            val json=it.getJSONObject(i)
                            val jsonInside=json.getJSONObject("commit")
                            val jsonInsideInside=jsonInside.getJSONObject("author")
                            val authorName=jsonInsideInside.getString("name")
                            val date=jsonInsideInside.getString("date")
                            val message=jsonInside.getString("message")
                            val jsonInside2=json.getJSONObject("author")
                            val username=jsonInside2.getString("login")
                            val avatar=jsonInside2.getString("avatar_url")

                            print("!!!!!!!!!!!!!!!$date!!$message!!$username!!!!!!!!!!!!!!!!!!")
                            val commitsEntity= CommitsEntity(repo_id,branch_id,authorName,date,message,username,avatar)
                            viewModel.insert(commitsEntity)

                        }




//                            progressBar.visibility = View.INVISIBLE
                    },
                    Response.ErrorListener {
                        println("Error12menu is " + it)

                        Toast.makeText(
                            this as Context,
                            "Some Error occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()

//                            progressBar.visibility = View.INVISIBLE
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()

                        headers["Content-type"] = "application/json"
//                        headers["x-rapidapi-key"] = "5d8f8b8f4emshd478b2356a3dbbap16f0edjsne3817dfa03ed"

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

    }}