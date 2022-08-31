package com.falcon.usarcompanion.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.R
import com.falcon.usarcompanion.databinding.FragmentOverviewBinding
import com.falcon.usarcompanion.network.Subject

class OverviewFragment : Fragment()  {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // Giving the binding access to the OverviewViewModel
        //_binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.rvSubjects.layoutManager = LinearLayoutManager(requireContext())

        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            if (subjects == null) {
                return@observe
            }
            val oddSemesterSubjects = mutableListOf<Subject>()
            val evenSemesterSubjects = mutableListOf<Subject>()
            for (subject in subjects) {
                if (subject.semester == "odd") {
                    oddSemesterSubjects.add(subject)
                }
                else {
                    evenSemesterSubjects.add(subject)
                }
            }
            val adapter = SubjectAdapter(requireContext(), oddSemesterSubjects, evenSemesterSubjects, ::onSubjectClick)
            //val map = subjects.groupBy { if (it.semester == "odd") "o" else "e" }
            //val adapter = SubjectAdapter(requireContext(), map["o"] ?: listOf(), map["e"] ?: listOf())
            binding.rvSubjects.adapter = adapter
        }

        viewModel.isDataFetchSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvErrorMessage.isVisible = false
            } else {
                binding.tvErrorMessage.isVisible = true
            }
        }

        return binding.root
    }

    fun onSubjectClick(currentSubject: Subject) : Unit {
        val bundle = Bundle()
        bundle.putSerializable("CurrentSubject", currentSubject)
        findNavController().navigate(R.id.action_overviewFragment_to_mainActivity3, bundle)
    }


}


