package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.padam_exercise.padamdaily.models.Job
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentResumeBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ResumeFragment : Fragment() {

    private var _binding: FragmentResumeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResumeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jobRV = view.findViewById<RecyclerView>(R.id.rv_experiences)
        val jobArrayList: ArrayList<Job> = ArrayList()
        jobArrayList.add(
            Job(
                getString(R.string.job1_title),
                getString(R.string.job1_desc),
                getString(R.string.job1_missions),
                getString(R.string.job1_tech)
            )
        )
        jobArrayList.add(
            Job(
                getString(R.string.job2_title),
                getString(R.string.job2_desc),
                getString(R.string.job2_missions),
                getString(R.string.job2_tech)
            )
        )
        jobArrayList.add(
            Job(
                getString(R.string.job3_title),
                getString(R.string.job3_desc),
                getString(R.string.job3_missions),
                getString(R.string.job3_tech)
            )
        )
        jobArrayList.add(
            Job(
                getString(R.string.job4_title),
                getString(R.string.job4_desc),
                getString(R.string.job4_missions),
                getString(R.string.job4_tech)
            )
        )

        val jobAdapter = JobAdapter(requireContext(), jobArrayList)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        jobRV.layoutManager = linearLayoutManager
        jobRV.adapter = jobAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}