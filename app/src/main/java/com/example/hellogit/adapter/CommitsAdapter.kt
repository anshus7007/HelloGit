package com.example.hellogit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hellogit.GitViewModel.BranchViewModel
import com.example.hellogit.GitViewModel.CommitViewModel
import com.example.hellogit.R
import com.example.hellogit.db.db3.entity.Branch
import com.example.hellogit.db.db4.entity.CommitsEntity
import com.example.hellogit.ui.activity.CommitsActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CommitsAdapter(val context: Context,
                     var item: MutableList<CommitsEntity>, var viewModel: CommitViewModel
) : RecyclerView.Adapter<CommitsAdapter.CommitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commits_single_row,parent,false)
        return CommitViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val currItem=item[position]
        holder.author.text=currItem.author
        holder.CommitMessage.text=currItem.userName
        holder.Date.text=currItem.date
        holder.userName.text=currItem.userName
        Picasso.get().load(currItem.avatar).into(holder.profile_image)





    }

    override fun getItemCount(): Int {
        return item.size
    }
    class CommitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val author: TextView =itemView.findViewById(R.id.author)
        val Date: TextView =itemView.findViewById(R.id.Date)
        val CommitMessage: TextView =itemView.findViewById(R.id.CommitMessage)
        val profile_image: CircleImageView =itemView.findViewById(R.id.profile_image)
        val userName: TextView =itemView.findViewById(R.id.userName)



    }
}
