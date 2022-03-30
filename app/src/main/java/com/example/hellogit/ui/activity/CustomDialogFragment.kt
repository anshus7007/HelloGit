package com.example.hellogit.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.db.db1.dao.GitDB
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.util.ConnectionManager
import org.json.JSONException

class CustomDialogFragment : AppCompatActivity() {
    lateinit var submit: Button
    lateinit var owner: EditText
    lateinit var RepoName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dialog_fragment)
        submit=findViewById(R.id.Add)
        owner=findViewById(R.id.owner)
        RepoName=findViewById(R.id.RepoName)
        findViewById<ImageView>(R.id.backToRepo).setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val database = GitDB(this)
        val repository = GitRepository(database)
        val factory = GitViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, factory).get(GitView::class.java)


        submit.setOnClickListener {

            val ownerString=owner.text.toString()
            val repoString=RepoName.text.toString()
            if(ownerString.isNotEmpty()&&repoString.isNotEmpty())
            {
                if (ConnectionManager().checkConnectivity(applicationContext as Context)) {

                    fetch(ownerString,repoString,viewmodel,factory)
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val alterDialog =
                        AlertDialog.Builder(this)
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
                    val alert: AlertDialog = alterDialog.create()
                    alert.show()
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFFFFF"))
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FFFFFF"))
                }


            }



        }
    }


    fun fetch( owner: String,  repo:String,  viewModel: GitView,  viewModelsss: GitViewModelFactory ) {


        if (ConnectionManager().checkConnectivity(this as Context)) {

            try {

                val queue = Volley.newRequestQueue(this as Context)



                val url = "https://api.github.com/repos/$owner/$repo"

                val jsonObjectRequest = object : JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {




                            val repoId = it.getInt("id")
                            val description = it.getString("description")

                            val repoName = it.getString("name")
                            val json = it.getJSONObject("owner")
                            val ownername = json.getString("login")
                            print("##############$repoName#######$ownername###############################")
                            val gitEntity = Git(repoId,repoName, ownername, description)
                            viewModel.insert(gitEntity)


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
