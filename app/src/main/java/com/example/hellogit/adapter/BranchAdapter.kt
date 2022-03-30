package com.example.hellogit.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModel.IssuesViewModel
import com.example.hellogit.R
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.ui.activity.CommitsActivity
import com.example.hellogit.ui.activity.DetailsActivity
import com.example.hellogit.util.ConnectionManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class BranchAdapter(val context: Context,
                    var item: MutableList<Branch>, var viewModel: BranchViewModel
) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.branch_single_row,parent,false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val currItem=item[position]
        holder.branchName.text=currItem.branchName
//        holder.itemView.setOnClickListener{
//
//            if (ConnectionManager().checkConnectivity(context!!)) {
//                print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&${currItem.branchName}&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
//
//                val intent= Intent(context,CommitsActivity::class.java)
//                intent.putExtra("sha",currItem.sha)
//                intent.putExtra("branchName",currItem.branchName)
//                intent.putExtra("repo_id",currItem.repo_id)
//                intent.putExtra("branch_id",currItem.id)
//                intent.putExtra("repo_name",currItem.repo_name)
//                intent.putExtra("owner",currItem.owner)
//
//
//                holder.itemView.context.startActivity(intent)
//            }
//            else
//            {
//                val alterDialog =
//                    AlertDialog.Builder(context!!)
//                alterDialog.setTitle("No Internet")
//                alterDialog.setMessage("Internet Connection can't be establish!")
//                alterDialog.setPositiveButton("Open Settings") { text, listener ->
//                    val settingsIntent = Intent(Settings.ACTION_SETTINGS)//open wifi settings
//                    context.startActivity(settingsIntent)
//                }
//
//                alterDialog.setNegativeButton("Exit") { text, listener ->
//                    (context as Activity).finishAffinity()//closes all the instances of the app and the app closes completely
//                }
//                alterDialog.setCancelable(false)
//                val alert: AlertDialog = alterDialog.create()
//                alert.show()
//                alert.setCanceledOnTouchOutside(true);
//                alert.setCanceledOnTouchOutside(true);
//
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFFFFF"))
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FFFFFF"))
//                alert.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#2D3650"))
//
//            }


//        }





    }

    override fun getItemCount(): Int {
        return item.size
    }
    class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val branchName: TextView =itemView.findViewById(R.id.BranchName)
        val rl1: RelativeLayout =itemView.findViewById(R.id.rl1)



    }
}
