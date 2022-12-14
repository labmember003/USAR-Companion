package com.falcon.usarcompanion.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.usarcompanion.MyAdapter
import com.falcon.usarcompanion.databinding.FragmentMerchandiseBinding
import com.falcon.usarcompanion.network.Merge
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MerchandiseFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentMerchandiseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMerchandiseBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("categories")
        //var data = database.child("categories").child("0").child("merchList")
        //Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show()
        //binding.test.text = data.toString()

        //println(data.toString())
//        val a = data.database.
//        println(a)

        val merge1 = Merge("google.com", "Aot themed", "$200")
        val merge2 = Merge("google.com", "Aot themed", "$200")
        val merge3 = Merge("google.com", "Aot themed", "$200")
        val merge4 = Merge("google.com", "Aot themed", "$200")
        val merges = listOf(merge1, merge2, merge3, merge4)
        binding.merchandiseList.adapter = MyAdapter(requireContext(), merges)
        binding.merchandiseList.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}