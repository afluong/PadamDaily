package io.padam_exercise.padamdaily.screens

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import io.padam_exercise.padamdaily.models.Job
import android.view.View
import android.view.ViewGroup
import padam_exercise.padamdaily.R
import android.widget.TextView

class JobAdapter(private val context: Context, jobArrayList: ArrayList<Job>) :
    RecyclerView.Adapter<JobAdapter.ViewHolder>() {
    private val jobArrayList: ArrayList<Job>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.cardview_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // to set data to textview of each card layout
        val job: Job = jobArrayList[position]
        holder.jobTitleTV.text = job.getJobTitle()
        holder.jobDescTV.text = job.getJobDesc()
        holder.jobMissionsTV.text = job.getJobMissions()
        holder.jobTechTV.text = job.getJobTech()
    }

    // Number of card items in recycler view
    override fun getItemCount(): Int {
        return jobArrayList.size
    }

    // View holder class for initializing of text views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobTitleTV: TextView
        val jobDescTV: TextView
        val jobMissionsTV: TextView
        val jobTechTV: TextView

        init {
            jobTitleTV = itemView.findViewById(R.id.tv_job_title)
            jobDescTV = itemView.findViewById(R.id.tv_job_desc)
            jobMissionsTV = itemView.findViewById(R.id.tv_job_missions)
            jobTechTV = itemView.findViewById(R.id.tv_job_tech)
        }
    }

    // Constructor
    init {
        this.jobArrayList = jobArrayList
    }
}