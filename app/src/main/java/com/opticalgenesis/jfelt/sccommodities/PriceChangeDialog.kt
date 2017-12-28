package com.opticalgenesis.jfelt.sccommodities

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.opticalgenesis.jfelt.sccommodities.models.Commodity


class PriceChangeDialog : DialogFragment() {

    private var commodity: Commodity? = null
    private var stationName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        commodity = args?.getParcelable(Keys.DIALOG_PRICE_CHANGE_COMMODITY_KEY)
        stationName = args?.getString(Keys.DIALOG_PRICE_CHANGE_STATION_KEY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = EditText(activity)
        val b = AlertDialog.Builder(activity!!).apply {
            setTitle("Enter new price")
            setView(v)
            setPositiveButton("Confirm", {dialog, _ ->
                updatePrice(v.text.toString().toDouble())
                dialog.dismiss()
            })
            setNegativeButton("Cancel", {dialog, _ ->
                dialog.cancel()
            })
        }
        return b.create()
    }

    private fun updatePrice(newPrice: Double) {
        val changes = HashMap<String, Commodity>()
        val newCommodity = Commodity(commodity?.name, newPrice, commodity?.type)
        when (stationName?.toLowerCase()?.replace(" ", "")) {
            "grimhex" -> changes.put("/0/commodities/${newCommodity.name?.toLowerCase()}", newCommodity)
            "levski" -> changes.put("/1/commodities/${newCommodity.name?.toLowerCase()}", newCommodity)
            "olisar" -> changes.put("/2/commodities/${newCommodity.name?.toLowerCase()}", newCommodity)
        }
        FirebaseDatabase.getInstance().reference.child("systems").child("stanton").child("ports").updateChildren(changes.toMap()).addOnCompleteListener(
                {task ->
                    if (task.isComplete) {
                        Log.d("TAG", "Data update complete")
                    } else if (task.isSuccessful) {
                        Log.d("TAG", "Data update successful")
                    } else {
                        task.exception!!.printStackTrace()
                    }
                }
        )
    }
}