package com.bellacity.ui.fragment.addGrnt

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.items.response.GrntItems
import com.bellacity.databinding.FragmentAddGrnt3Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.adapter.itemsAdapter.GrntItemsAdapter
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import timber.log.Timber


class AddGrnt3Fragment : BaseFragment<FragmentAddGrnt3Binding>() {
    private val viewModel: AddGrntViewModel by viewModels()
    private var itemsHashSet = HashSet<GrntItems>()
    private var items = ArrayList<GrntItems>()
    private val grntItemsAdapter: GrntItemsAdapter by lazy {
        GrntItemsAdapter(
            ::plusQuantity,
            ::minuesQuantity
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddGrnt3Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
            Timber.d(
                "${
                    items.distinctBy {
                        it.itemID
                    }
                }")
        }
    }

    override fun initViewModel() {
        initItemsViewModel()
    }

    override fun onCreateInit() {
        bindData()
        hideNavBtn()
    }

    private fun initItemsViewModel() {
        viewModel.grntItems()
        viewModel._grntItemsMutableLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillSpinnerItems(response.data.grntItemsList!!)
                        }
                        else -> {
                            showSnackbar(response.data?.message)
                        }
                    }
                }
                is Resource.Error -> {
                    DialogUtil.dismissDialog()
                }
                is Resource.Loading -> {
                    DialogUtil.showDialog(requireContext())
                }
            }
        })
    }

    private fun fillSpinnerItems(grntItemsList: List<GrntItems>) {
        val grntItems = ArrayList<String>()
        grntItemsList.forEach {
            grntItems.add(it.itemName!!)
        }
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                R.layout.item_drop_down,
                grntItems
            )
        binding.autoItems.setAdapter(dataAdapter)
        binding.autoItems.setOnItemClickListener { parent, view, position, id ->

            items = addGrntITems(grntItemsList, position).distinctBy {
                it.itemID
            } as ArrayList<GrntItems>
            Timber.d(
                "$items"
            )
            grntItemsAdapter.submitList(items)
            grntItemsAdapter.notifyDataSetChanged()
        }
    }

    private fun addGrntITems(grntItemsList: List<GrntItems>, position: Int): List<GrntItems> {

        var grntItems: GrntItems? = null

        grntItems = GrntItems(
            grntItemsList[position].itemID,
            grntItemsList[position].itemName,
            1
        )
        itemsHashSet.add(grntItems)

        return itemsHashSet.toMutableList()
    }

    private fun plusQuantity(postion: Int, item: GrntItems, quantity: TextView) {
        val newQuantity = item.itemQuantity?.inc()
        Timber.d("" + newQuantity)
        quantity.text = "${newQuantity}"
        items[postion].itemQuantity = newQuantity
        grntItemsAdapter.notifyDataSetChanged()
    }

    private fun minuesQuantity(postion: Int, item: GrntItems, quantity: TextView) {
        val newQuantity = item.itemQuantity?.dec()
        Timber.d("" + newQuantity)
        if (newQuantity == 0) {
            items.removeAt(postion)
            itemsHashSet.remove(item)
        } else {
            quantity.text = "${newQuantity}"
            items[postion].itemQuantity = newQuantity
        }
        grntItemsAdapter.notifyDataSetChanged()
    }

    private fun bindData() {
        binding.grntItemsAdapter = grntItemsAdapter
    }

}