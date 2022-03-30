package com.example.hellogit.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView

import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.R
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.ui.activity.DetailsActivity
import com.example.hellogit.util.ConnectionManager

class RepoAdapter(val context: Context,
                  var item: MutableList<Git>, var viewModel: GitView
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_single_row,parent,false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val currItem=item[position]
        holder.repoName.text=currItem.repo_name
        holder.description.text=currItem.description
        holder.share.setOnClickListener{
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Repo:${currItem.repo_name}::${"https://github.com/repos/${currItem.owner}/${currItem.repo_name}"}")
            intent.type="text/plain"
            context.startActivity(Intent.createChooser(intent,"Share To:"))
        }
        holder.itemView.setOnClickListener {
            if (ConnectionManager().checkConnectivity(context!!)) {

                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("repo_Id", currItem.id)
                intent.putExtra("repo_Name", currItem.repo_name)
                intent.putExtra("description", currItem.description)
                intent.putExtra("owner_Name", currItem.owner)


                context.startActivity(intent)
            }
            else
            {
                val alterDialog =
                    AlertDialog.Builder(context!!)
                alterDialog.setTitle("No Internet")
                alterDialog.setMessage("Internet Connection can't be establish!")
                alterDialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
                    context.startActivity(settingsIntent)
                }

                alterDialog.setNegativeButton("Exit") { text, listener ->
                    (context as Activity).finishAffinity()//closes all the instances of the app and the app closes completely
                }
                alterDialog.setCancelable(false)
                val alert: AlertDialog = alterDialog.create()
                alert.show()
                alert.setCanceledOnTouchOutside(true);
                alert.setCanceledOnTouchOutside(true);

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFFFFF"))
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FFFFFF"))
                alert.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#2D3650"))

            }
        }


    }

    override fun getItemCount(): Int {
        return item.size
    }
    class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val repoName: TextView =itemView.findViewById(R.id.repoName)
        val description: TextView =itemView.findViewById(R.id.description)
        val share: RelativeLayout =itemView.findViewById(R.id.share)
        val background: RelativeLayout =itemView.findViewById<RelativeLayout>(R.id.single_home)


    }
}
