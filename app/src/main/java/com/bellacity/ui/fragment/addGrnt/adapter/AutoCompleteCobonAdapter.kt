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
import com.bellacity.data.model.cobon.response.Cobon
import java.util.*


class AutoCompleteCobonAdapter(
    private val c: Context,
    @LayoutRes private val layoutResource: Int,
    private val techList: List<Cobon>,
    val actionClick: (item: Cobon) -> Unit
) : ArrayAdapter<Cobon>(c, layoutResource, techList) {

    var filteredTechList: List<Cobon> = listOf()

    override fun getCount(): Int = filteredTechList.size

    override fun getItem(position: Int): Cobon = filteredTechList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val view = convertView ?: LayoutInflater.from(c)
            .inflate(R.layout.item_tech_list, parent, false)

        val nameView = view.findViewById(R.id.tv_tech_name) as TextView
        nameView.text = filteredTechList[position].coubonSerial.toString()

        nameView.setOnClickListener {
            actionClick(filteredTechList[position])
        }
        return view
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredTechList = filterResults.values as List<Cobon>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    techList
                else
                    techList.filter {
                        it.coubonSerial?.toString()?.lowercase(Locale.getDefault())!!
                            .contains(queryString)
                    }
                return filterResults
            }
        }
    }


}