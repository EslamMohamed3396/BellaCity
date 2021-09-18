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
import com.bellacity.data.model.tech.response.Tech
import java.util.*


class AutoCompleteTechAdapter(
    private val c: Context,
    @LayoutRes private val layoutResource: Int,
    private val techList: List<Tech>,
    val actionClick: (item: Tech) -> Unit
) : ArrayAdapter<Tech>(c, layoutResource, techList) {

    var filteredTechList: List<Tech> = listOf()

    override fun getCount(): Int = filteredTechList.size

    override fun getItem(position: Int): Tech = filteredTechList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val view = convertView ?: LayoutInflater.from(c)
            .inflate(R.layout.item_tech_list, parent, false)

        val nameView = view.findViewById(R.id.tv_tech_name) as TextView
        nameView.text = filteredTechList[position].techName

        nameView.setOnClickListener {
            actionClick(filteredTechList[position])
        }
        return view
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredTechList = filterResults.values as List<Tech>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    techList
                else
                    techList.filter {
                        it.techName?.lowercase(Locale.getDefault())!!.contains(queryString)
                    }
                return filterResults
            }
        }
    }


}