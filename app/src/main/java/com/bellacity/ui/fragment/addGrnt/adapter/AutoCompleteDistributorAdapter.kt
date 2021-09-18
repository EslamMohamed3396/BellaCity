package com.bellacity.ui.fragment.addGrnt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.bellacity.R
import com.bellacity.data.model.distributor.response.Distributor
import java.util.*


class AutoCompleteDistributorAdapter(
    private val c: Context,
    @LayoutRes private val layoutResource: Int,
    private val distributor: List<Distributor>,
    val actionClick: (item: Distributor) -> Unit
) : ArrayAdapter<Distributor>(c, layoutResource, distributor) {

    var filteredDistributorList: List<Distributor> = listOf()

    override fun getCount(): Int = filteredDistributorList.size

    override fun getItem(position: Int): Distributor = filteredDistributorList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val view = convertView ?: LayoutInflater.from(c)
            .inflate(R.layout.item_tech_list, parent, false)

        val nameView = view.findViewById(R.id.tv_tech_name) as TextView
        nameView.text = filteredDistributorList[position].distributorName

        nameView.setOnClickListener {
            actionClick(filteredDistributorList[position])
        }
        return view
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredDistributorList = filterResults.values as List<Distributor>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    distributor
                else
                    distributor.filter {
                        it.distributorName?.lowercase(Locale.getDefault())!!.contains(queryString)
                    }
                return filterResults
            }
        }
    }


}