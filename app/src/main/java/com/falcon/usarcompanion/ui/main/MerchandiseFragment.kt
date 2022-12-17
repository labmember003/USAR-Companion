package com.falcon.usarcompanion.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.usarcompanion.MyAdapter
import com.falcon.usarcompanion.databinding.FragmentMerchandiseBinding
import com.falcon.usarcompanion.network.Merge
import com.google.firebase.database.*
import org.json.JSONObject

class MerchandiseFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentMerchandiseBinding? = null
    private val binding get() = _binding!!
    private lateinit var  merchRecyclerView: RecyclerView
    private lateinit var  merchandiseList: ArrayList<Merge>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMerchandiseBinding.inflate(inflater, container, false)
        merchRecyclerView = binding.merchandiseList
        merchRecyclerView.layoutManager = LinearLayoutManager(context)
        merchRecyclerView.setHasFixedSize(true)
        merchandiseList = arrayListOf<Merge>()
        getUserData()

        return binding.root
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("merchList")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (merchSnapshot in snapshot.children) {
                        val catName = merchSnapshot.getValue(Merge::class.java)
                            catName?.let {
                                merchandiseList.add(it)
                        }
                    }
                    merchRecyclerView.adapter = MyAdapter(merchandiseList)
                    binding.imagePendingAnimation.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        database.child("merchList").get()
    }
}