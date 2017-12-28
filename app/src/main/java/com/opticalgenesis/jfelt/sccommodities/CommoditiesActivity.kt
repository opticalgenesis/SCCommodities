package com.opticalgenesis.jfelt.sccommodities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opticalgenesis.jfelt.sccommodities.adapters.CommoditiesListAdapter
import com.opticalgenesis.jfelt.sccommodities.models.Commodity


class CommoditiesActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var port: String

    private var commodities = ArrayList<Commodity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)

        port = intent.getStringExtra("c")

        getCommodities()
    }

    private fun getCommodities() {
        var digitStr = "0"
        port = port.toLowerCase().replace(" ", "")
        when (port) {
            "grimhex" -> digitStr = "0"
            "levski" -> digitStr = "1"
            "portolisar" -> digitStr = "2"
        }
        val ref = FirebaseDatabase.getInstance().reference.child("systems").child("stanton").child("ports").child(digitStr).child("commodities")

        ref.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        commodities.clear()
                        for (map in p0.children) {
                            val comm = map.getValue(Commodity::class.java)!!
                            commodities.add(comm)
                        }
                        initRecycler()
                    }

                    override fun onCancelled(p0: DatabaseError?) = p0?.toException()?.printStackTrace()!!
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.commodity_list_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sort_name -> {
                sort("name")
                return true
            }
            R.id.sort_type -> {
                sort("type")
                return true
            }
            R.id.sort_price -> {
                sort("price")
                return true
            }
        }
        return false
    }

    private fun sort(method: String) {
        when (method) {
            "name" -> commodities = ArrayList(commodities.sortedWith(compareBy({it.name})))
            "type" -> commodities = ArrayList(commodities.sortedWith(compareBy({it.type}, {it.name})))
            "price" -> commodities = ArrayList(commodities.sortedWith(compareBy({it.price}, {it.name})))
        }
        initRecycler()
    }

    private fun initRecycler() {
        rv = findViewById(R.id.activity_commodity_rv)
        val llm = LinearLayoutManager(this)
        val decor = DividerItemDecoration(rv.context, llm.orientation)
        rv.addItemDecoration(decor)
        rv.layoutManager = llm
        rv.adapter = CommoditiesListAdapter(commodities, intent.getStringExtra("c"), this)
    }
}