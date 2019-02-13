package com.zimplifica.siteappandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.zimplifica.siteappandroid.Models.Bill
import com.zimplifica.siteappandroid.utils.ActivityAdapter
import com.zimplifica.siteappandroid.utils.OnItemClickListener
import java.util.*

class ActivityScreen : AppCompatActivity() {

    lateinit var listview : ListView
    lateinit var adapter : ActivityAdapter
    private var activityList = mutableListOf<Bill>()
    lateinit var tabLayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        val toolbar : Toolbar = findViewById(R.id.activity_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        listview = findViewById(R.id.activity_list_view)
        tabLayout = findViewById(R.id.activity_tabs)
        activityList = this.intent.getSerializableExtra("activityList") as MutableList<Bill>
        /*
        activityList.add( Bill(UUID.randomUUID(),"McDonalds La Tropicana",
            "Combo 3. Combo Agrandado. Papas Ext...","₡ 2.550,00","Francisco Córdoba Rojas",
            " 29/01/2019", "7:30pm"))
        activityList.add( Bill(UUID.randomUUID(),"Feria Del Agricultor",
            "","₡ 5.850,00","Francisco Córdoba Rojas",
            " 20/01/2019", "9:45pm"))
        activityList.add( Bill(UUID.randomUUID(),"Repuestos La Guacamaya",
            "","₡ 16.000,00","Francisco Córdoba Rojas",
            " 20/01/2019", "12:30pm"))*/
        adapter = ActivityAdapter(this)
        listview.adapter = adapter
        listViewProcess()
        tabLayout.addTab(tabLayout.newTab().setText("Últimos 7 días"),true)
        tabLayout.addTab(tabLayout.newTab().setText("Mes Actual"), false)
        tabLayout.addTab(tabLayout.newTab().setText("Filtrar por Fecha"), false)
        listview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if(adapter.getItemViewType(position)==0){
                val bill = adapter.getItem(position)
                Log.e("Activity", bill.enterprise)
                val intent = Intent(this, PaymentBill::class.java)
                intent.putExtra("bill", bill)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    fun listViewProcess(){
        val dateList = LinkedHashSet<String>()
        val iterator = activityList.iterator()
        while (iterator.hasNext()){
            val oldValue = iterator.next()
            dateList.add(oldValue.date)
            Log.e("dates", oldValue.date)
        }
        val iterator2 = dateList.iterator()
        while (iterator2.hasNext()){
            val oldValue = iterator2.next()
            val header = Bill(UUID.randomUUID(),"", "","","",oldValue, "")
            adapter.addHeader(header)
            val itModel = activityList.iterator()
            while (itModel.hasNext()){
                val value = itModel.next()
                if(value.date == oldValue){
                    adapter.addItem(value)
                    Log.e("Item", value.enterprise)
                }
            }
        }
    }
}
