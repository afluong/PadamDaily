package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentResumeBinding

class ResumeFragment : Fragment() {


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    lateinit var  binding : FragmentResumeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResumeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        binding.displayXp.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter() }
        binding.apply {
            displayXp.layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter()

            profilpicture.setImageResource(R.drawable.ic_launcher_background)
            tvSurname.text = "RICHARD"
            tvName.text = "Alex"
            tvTitle.text ="Android Developper"
            tvAge.text ="26 years old"
        }

    }


}