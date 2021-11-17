import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bellacity.R
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.databinding.ItemCobonListBinding


class CobonSpinnerAdapter(
    context: Context,
    resource: Int,
    val cobonList: List<Cobon>,
    val actionSelectedCobon: (postion: Int, item: Cobon) -> Unit
) :
    ArrayAdapter<Cobon?>(context, resource, cobonList) {

    private val listState: ArrayList<Cobon> = cobonList as ArrayList<Cobon>

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }


    private fun getCustomView(
        position: Int,
        parent: ViewGroup?
    ): View {

        val binding: ItemCobonListBinding = ItemCobonListBinding.inflate(
            LayoutInflater.from(parent?.context), parent, false
        )

        val itemCobon = listState[position]
        binding.cobon = itemCobon


        binding.cardView.setBackgroundResource(if (itemCobon.isSelected) R.color.red else R.color.white)
        binding.cardView.setOnClickListener {
            itemCobon.isSelected = !itemCobon.isSelected
            binding.cardView.setBackgroundResource(if (itemCobon.isSelected) R.color.red else R.color.white)
            actionSelectedCobon(position, itemCobon)
        }
        return binding.root
    }

}