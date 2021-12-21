package com.example.r2d

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class viewInventoryActivity : AppCompatActivity() {

    var mrecyclerview: RecyclerView? = null

    private var totalnoofitem: TextView? = null
    private var totalnoofsum: TextView? = null
    private var counttotalnoofitem = 0
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_inventory)
        totalnoofitem = findViewById(R.id.totalnoitem)
        totalnoofsum = findViewById(R.id.totalsum)



    }


}
