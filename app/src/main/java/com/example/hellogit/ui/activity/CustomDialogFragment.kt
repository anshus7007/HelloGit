package com.example.hellogit.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hellogit.GitRepository.GitRepository
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModelFactory.GitViewModelFactory
import com.example.hellogit.R
import com.example.hellogit.db.GitDB
import com.example.hellogit.db.entity.Git
import com.example.hellogit.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class CustomDialogFragment : AppCompatActivity() {
    lateinit var cancel: Button
    lateinit var submit: Button
    lateinit var owner: EditText
    lateinit var RepoName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dialog_fragment)
        submit=findViewById(R.id.Add)
        owner=findViewById(R.id.owner)
        RepoName=findViewById(R.id.RepoName)
        val database = GitDB(this)
        val repository = GitRepository(database)
        val factory = GitViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, factory).get(GitView::class.java)


        submit.setOnClickListener {
            print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Helllo^^^^^^^^^^^^^^^^^")

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
//                val intent=Intent(this,MainActivity::class.java)
//                startActivity(intent)
//                finish()

            }



        }
    }
//    @SuppressLint("StaticFieldLeak")
//    inner class GitTask(val owner: String, val repo:String, val viewModel: GitView, val viewModelsss: GitViewModelFactory) : AsyncTask<String, Void, String>() {
//        override fun onPreExecute() {
//
//            super.onPreExecute()
//
//
//        }
//        override fun doInBackground(vararg p0: String?): String? {
//
//            var response: String?
//
//            var APPID = "e55a6c82e17579c3f5a238e30a32977f"
//            println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Anshu%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
//
//            if (ConnectionManager().checkConnectivity(applicationContext as Context)) {
//
//                try {
//
//                    response =
//                        URL("https://api.github.com/repos/$owner/$repo").readText(
//                            Charsets.UTF_8
//                        )
//                    print("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%$response%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
//                } catch (e: Exception) {
//                    response = null
//                }
//                return response
//
//            } else {
//                val alterDialog =
//                    androidx.appcompat.app.AlertDialog.Builder(applicationContext as Context)
//                alterDialog.setTitle("No Internet")
//                alterDialog.setMessage("Internet Connection can't be establish!")
//                alterDialog.setPositiveButton("Open Settings") { text, listener ->
//                    val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
//                    startActivity(settingsIntent)
//                }
//
//                alterDialog.setNegativeButton("Exit") { text, listener ->
//                    finishAffinity()//closes all the instances of the app and the app closes completely
//                }
//                alterDialog.setCancelable(false)
//
//                alterDialog.create()
//                alterDialog.show()
//            }
//            return null
//        }
//
//
//        override fun onPostExecute(result: String?) {
//
//            super.onPostExecute(result)
//            if (ConnectionManager().checkConnectivity(applicationContext as Context)) {
//
//
//                try {
//
//                    print("@@@@@@@@@@@@@@@@@@$result@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//                    val jsonObj = JSONObject(result!!)
//
//
//                    val repoId = jsonObj.getInt("id")
//                    val description = jsonObj.getString("description")
//
//                    val repoName = jsonObj.getString("name")
//                    val json=jsonObj.getJSONObject("owner")
//                    val ownername=json.getString("login")
//
//
//
//
//                    print("##############$repoName#######$ownername###############################")
//                    val gitEntity = Git(repoId,repoName, ownername, description)
//                    viewModel.insert(gitEntity)
//
//
//
//
//
//
//                } catch (e: Exception) {
//
//                }
//            } else {
//                val alterDialog =
//                    androidx.appcompat.app.AlertDialog.Builder(applicationContext as Context)
//                alterDialog.setTitle("No Internet")
//                alterDialog.setMessage("Internet Connection can't be establish!")
//                alterDialog.setPositiveButton("Open Settings") { text, listener ->
//                    val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
//                    startActivity(settingsIntent)
//                }
//
//                alterDialog.setNegativeButton("Exit") { text, listener ->
//                    finishAffinity()//closes all the instances of the app and the app closes completely
//                }
//                alterDialog.setCancelable(false)
//
//                alterDialog.create()
//                alterDialog.show()
//            }
//
//
//        }
//    }

    fun fetch( owner: String,  repo:String,  viewModel: GitView,  viewModelsss: GitViewModelFactory ) {


        if (ConnectionManager().checkConnectivity(this as Context)) {

            try {

                val queue = Volley.newRequestQueue(this as Context)


                //val restaurantId:String=""

                val url = "https://api.github.com/repos/$owner/$repo"

                val jsonObjectRequest = object : JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener {
                        println("********************************************Response12menu is $it")




                            val repoId = it.getInt("id")
                            val description = it.getString("description")

                            val repoName = it.getString("name")
                            val json = it.getJSONObject("owner")
                            val ownername = json.getString("login")
                            print("##############$repoName#######$ownername###############################")
                            val gitEntity = Git(repoId,repoName, ownername, description)
                            viewModel.insert(gitEntity)


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

    }


}
