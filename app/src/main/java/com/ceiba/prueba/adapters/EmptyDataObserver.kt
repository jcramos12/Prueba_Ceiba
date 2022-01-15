package com.ceiba.prueba.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(private var recyclerView: RecyclerView, private var emptyView: View): RecyclerView.AdapterDataObserver() {

    init {
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        val emptyViewVisible = recyclerView.adapter?.itemCount == 0
        emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
        recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

}
