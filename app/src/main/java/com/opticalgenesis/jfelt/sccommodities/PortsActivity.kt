package com.opticalgenesis.jfelt.sccommodities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opticalgenesis.jfelt.sccommodities.adapters.PortsListAdapter
import com.opticalgenesis.jfelt.sccommodities.models.Station


class PortsActivity : AppCompatActivity() {

    private lateinit var systemSelected: String
    private lateinit var rv: RecyclerView

    val ports = ArrayList<Station>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_port)
        systemSelected = intent.getStringExtra(Keys.SYSTEM_SELECTED_KEY).toLowerCase().replace(" ", "")

        initPorts()
    }

    private fun initPorts() {
        FirebaseDatabase.getInstance().reference.child("systems").child(systemSelected).child("ports").addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot?) {
                        p0?.children?.forEach { ports.add(it.getValue(Station::class.java)!!); Log.d("TAG", "There are now ${ports.size} ports") }
                        initRecycler()
                    }

                    override fun onCancelled(p0: DatabaseError?) = p0?.toException()?.printStackTrace()!!
                }
        )
    }

    private fun initRecycler() {
        rv = findViewById(R.id.activity_port_rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = PortsListAdapter(ports, this)
    }
}