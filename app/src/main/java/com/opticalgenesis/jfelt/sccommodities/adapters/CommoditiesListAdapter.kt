package com.opticalgenesis.jfelt.sccommodities.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.opticalgenesis.jfelt.sccommodities.Keys
import com.opticalgenesis.jfelt.sccommodities.PriceChangeDialog
import com.opticalgenesis.jfelt.sccommodities.R
import com.opticalgenesis.jfelt.sccommodities.models.Commodity
import com.opticalgenesis.jfelt.sccommodities.models.Station


class CommoditiesListAdapter internal constructor(private val commodities: ArrayList<Commodity>, private val port: String,
                                                  c: Context) :
        RecyclerView.Adapter<CommoditiesListAdapter.ViewHolder>() {

    init {
        for (entry in commodities) {
            Log.d("TAG", entry.name)
        }
    }

    private val caller = c as AppCompatActivity

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val commodityDetails = "Type: ${commodities[position].type}\nPrice: ${commodities[position].price} aUEC"
        holder?.name?.text = commodities[position].name
        holder?.details?.text = commodityDetails
        holder?.bg?.setOnClickListener { updatePriceForItem(commodities[position]) }
    }

    // TODO THIS WILL NOT SCALE WHEN NEW SYSTEMS ARE ADDED == BEWARE
    private fun updatePriceForItem(commodity: Commodity) {
        val dialog = PriceChangeDialog()
        val bundle = Bundle()
        bundle.putParcelable(Keys.DIALOG_PRICE_CHANGE_COMMODITY_KEY, commodity)
        bundle.putString(Keys.DIALOG_PRICE_CHANGE_STATION_KEY, port)
        dialog.arguments = bundle
        dialog.show(caller.supportFragmentManager, "jiksd")
    }

    override fun getItemCount() = commodities.size

    class ViewHolder internal constructor(vg: ViewGroup?) : RecyclerView.ViewHolder(LayoutInflater.from(vg?.context).inflate(R.layout.commodity_item, vg, false)) {
        val bg = itemView.findViewById<LinearLayout>(R.id.commodity_item_background)
        val name = itemView.findViewById<TextView>(R.id.commodity_item_name)
        val details = itemView.findViewById<TextView>(R.id.commodity_item_details)
    }
}