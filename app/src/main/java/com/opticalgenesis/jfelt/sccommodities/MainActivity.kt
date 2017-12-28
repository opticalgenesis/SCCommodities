package com.opticalgenesis.jfelt.sccommodities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opticalgenesis.jfelt.sccommodities.adapters.SystemsListAdapter
import com.opticalgenesis.jfelt.sccommodities.models.System

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSystems()
    }

    private fun initRecycler(systems: ArrayList<System>) {
        rv = findViewById(R.id.activity_main_rv)
        rv.layoutManager = LinearLayoutManager(this)
        Log.d("TAG", "There are ${systems.size} systems")
        rv.adapter = SystemsListAdapter(systems, this)
    }

    private fun getSystems() {
        val systems = ArrayList<System>()
        FirebaseDatabase.getInstance().reference.child("systems").addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        p0.children.forEach { systems.add(it.getValue(System::class.java)!!) }
                        initRecycler(systems)
                    }

                    override fun onCancelled(p0: DatabaseError?) = p0?.toException()?.printStackTrace()!!
                }
        )
    }
}
