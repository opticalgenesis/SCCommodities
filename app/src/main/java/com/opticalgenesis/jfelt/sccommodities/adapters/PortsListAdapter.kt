package com.opticalgenesis.jfelt.sccommodities.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.opticalgenesis.jfelt.sccommodities.CommoditiesActivity
import com.opticalgenesis.jfelt.sccommodities.Keys
import com.opticalgenesis.jfelt.sccommodities.R
import com.opticalgenesis.jfelt.sccommodities.models.Commodity
import com.opticalgenesis.jfelt.sccommodities.models.Station


class PortsListAdapter internal constructor(private var ports: ArrayList<Station>, private var c: Context)
    : RecyclerView.Adapter<PortsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        when (position) {
            0 -> holder?.iv?.setImageResource(R.mipmap.ic_launcher)
            1 -> holder?.iv?.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.levski))
            2 -> holder?.iv?.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.po))
            else -> holder?.iv?.setImageResource(R.mipmap.ic_launcher)
        }

        holder?.portName?.text = ports[position].name
        val commodityNumber = "${ports[position].commodities?.size} commodities available"
        holder?.portCommodityNumber?.text = commodityNumber
        holder?.bg?.setOnClickListener({onPortSelected(ports[position])})
    }

    private fun onPortSelected(port: Station) {
        val commoditiesList = ArrayList<Commodity>()
        port.commodities?.entries?.forEach { commoditiesList.add(it.value) }
        c.startActivity(Intent(c, CommoditiesActivity::class.java).apply {
            putExtra("c", port.name)
        })
    }

    override fun getItemCount() = ports.size
    class ViewHolder internal constructor(vg: ViewGroup?) : RecyclerView.ViewHolder(LayoutInflater.from(vg?.context).inflate(R.layout.station_card_display_item, vg, false)) {
        val bg = itemView.findViewById<CardView>(R.id.station_card_background)
        val iv = itemView.findViewById<ImageView>(R.id.station_card_image)
        val portName = itemView.findViewById<TextView>(R.id.station_card_name)
        val portCommodityNumber = itemView.findViewById<TextView>(R.id.station_card_commodity_number)
    }
}