package com.opticalgenesis.jfelt.sccommodities.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import com.opticalgenesis.jfelt.sccommodities.Keys
import com.opticalgenesis.jfelt.sccommodities.PortsActivity

import com.opticalgenesis.jfelt.sccommodities.R
import com.opticalgenesis.jfelt.sccommodities.models.System


class SystemsListAdapter internal constructor(private val systems: List<System>, private val c: Context) : RecyclerView.Adapter<SystemsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(parent!!)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.title?.text = systems[position].name
        // TODO MOVE TO IMAGE HOSTING TO ALLOW FOR MULTIPLE SYSTEMS
        holder?.iv?.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.stanton_bg))
        val portNumber = "${systems[position].ports?.size} ports"
        holder?.number?.text = portNumber
        // TODO SET BACKGROUND OF IMAGEVIEW TO DOMINANT DARK_MUTED COLOUR USING PALETTE_SUPPORT
        holder?.cv?.setOnClickListener({onSystemSelected(systems[position].name!!)})
    }

    private fun onSystemSelected(system: String) {
        c.startActivity(Intent(c, PortsActivity::class.java).putExtra(Keys.SYSTEM_SELECTED_KEY, system))
    }

    override fun getItemCount(): Int = systems.size

    class ViewHolder internal constructor(vg: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(vg.context).inflate(R.layout.system_card_display_item, vg, false)) {
        var cv = itemView.findViewById<CardView>(R.id.system_item_card)
        var iv = itemView.findViewById<ImageView>(R.id.system_item_hero_image)
        var title = itemView.findViewById<TextView>(R.id.system_item_name)
        var number = itemView.findViewById<TextView>(R.id.system_item_number)
    }
}