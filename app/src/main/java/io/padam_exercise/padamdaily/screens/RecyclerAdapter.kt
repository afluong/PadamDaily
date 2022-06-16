package io.padam_exercise.padamdaily.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.JobcardBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {

    lateinit var binding: JobcardBinding

    private val title = arrayOf("Android developper","Android developper","Android developper")
    private val area = arrayOf("Lyon","Lyon","Lille")
    private val duration = arrayOf("2 years","1 years","1 years")
    private val logo = R.drawable.ic_launcher_background

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = JobcardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.jobcard, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            tvTitle.text = title[position]
            tvArea.text = area[position]
            tvDuration.text = duration[position]
            imageLogo.setImageResource(logo)
        }

    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(val binding: JobcardBinding) : RecyclerView.ViewHolder(binding.root) {

        var tvTitle: TextView
        var tvArea: TextView
        var tvDuration: TextView
        var imageLogo: ImageView

        init {
            tvTitle = binding.tvTitle
            tvArea = binding.tvArea
            tvDuration = binding.tvDuration
            imageLogo = binding.logo

        }
    }
}