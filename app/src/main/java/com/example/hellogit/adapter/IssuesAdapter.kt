package com.example.hellogit.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.number.NumberFormatter.with
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.hellogit.GitViewModel.GitView
import com.example.hellogit.GitViewModel.IssuesViewModel
import com.example.hellogit.R
import com.example.hellogit.db.db2.entity.Issues
import com.example.hellogit.db.db1.dao.entity.Git
import com.example.hellogit.ui.activity.DetailsActivity
import com.example.hellogit.util.ConnectionManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.System.load

class IssuesAdapter(val context: Context,
                    var item: MutableList<Issues>, var viewModel: IssuesViewModel
) : RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issues_single_row,parent,false)
        return IssuesViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val currItem=item[position]
        holder.titleOfTheIssue.text=currItem.title.toString()
        holder.userName.text=currItem.user
        val picasso = Picasso.get()
        picasso.load(currItem.avatar).into(holder.Avtar)




    }

    override fun getItemCount(): Int {
        return item.size
    }
    class IssuesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val titleOfTheIssue: TextView =itemView.findViewById(R.id.titleOfTheIssue)
        val Avtar: CircleImageView =itemView.findViewById(R.id.Avtar)
        val userName: TextView =itemView.findViewById(R.id.userName)


    }
}
