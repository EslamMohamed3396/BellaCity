package com.bellacity.ui.fragment.addInvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bellacity.R
import com.bellacity.data.model.addInvoice.request.BodyAddInvoice
import com.bellacity.data.model.addInvoice.request.InvoiceItem
import com.bellacity.data.model.calculateDiscount.request.BodyCalculateDiscount
import com.bellacity.data.model.supplementItems.request.BodySupplementItems
import com.bellacity.data.model.supplementItems.response.Item
import com.bellacity.databinding.FragmentAddInvoice2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.adapters.adapterBindItems.ItemsInvoiceAdapter
import com.bellacity.ui.fragment.supplementItems.SupplementItemsViewModel
import com.bellacity.ui.fragment.supplementItems.adapter.SupplementItemsAdapter
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AddInvoiceFragment2 : BaseFragment<FragmentAddInvoice2Binding>() {
    private val viewModel: SupplementItemsViewModel by viewModels()
    private val invoiceViewModel: InvoiceViewModel by viewModels()
    private val adapter: SupplementItemsAdapter by lazy { SupplementItemsAdapter(::onClickSupplementItems) }

    private val args by navArgs<AddInvoiceFragment2Args>()
    private val itemHashSet = mutableSetOf<Item>()
    private val itemsInvoiceAdapter: ItemsInvoiceAdapter by lazy {
        ItemsInvoiceAdapter(
            ::plusQuantity,
            ::minuesQuantity
        )
    }

    private var isOldPrice = false
    private var isProject = false
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddInvoice2Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.nextBtn.setOnClickListener {
            if (itemHashSet.isNotEmpty()) {
                initAddInvoiceViewModel()
            } else {
                showSnackbar("من فضلك اختر الأصناف ")
            }

        }
        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isOldPrice = isChecked
        }
        binding.calculateBtn.setOnClickListener {
            if (itemHashSet.isNotEmpty()) {
                initCalcDiscounttViewModel()
            } else {
                showSnackbar("من فضلك اختر الأصناف ")
            }
        }
    }

    override fun initViewModel() {
        searchSupplement()
    }

    override fun onCreateInit() {
        checkIsProject()
        bindAdapter()
    }

    private fun bindAdapter() {
        binding.recyclerView5.adapter = itemsInvoiceAdapter
    }

    private fun checkIsProject() {
        isProject = when (args.bodyAddInvoice.invoiceTypeID) {
            Constant.MABE3AT_MASHRO3AT_BOLY,
            Constant.MABE3AT_MASHRO3AT_SARF,
            Constant.MABE3AT_MASHRO3AT_RAMADY,
            Constant.MABE3AT_MASHRO3AT_KATA3Y -> {
                true
            }
            else -> {
                false
            }
        }
    }

    //region search item name
    private fun searchSupplement() {
        Observable.create { emitter: ObservableEmitter<String?> ->
            binding.itemsNameTextInput.editText?.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    emitter.onNext(text.toString())
                } else {
                    adapter.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                initSupplementViewModel(s!!)
            }
    }

    private fun initSupplementViewModel(item: String) {
        adapter.submitList(null)
        viewModel.searchSupplementItems(bodySupplementItems(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                adapter.submitList(response.data.itemList)
                                DialogUtil.showRecycler(requireContext(), adapter)

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
            }

    }

    private fun bodySupplementItems(item: String): BodySupplementItems {
        return BodySupplementItems(
            args.bodyAddInvoice.stockID,
            item,
            isProject = isProject,
            isForGrnt = false
        )
    }

    private fun onClickSupplementItems(postion: Int, item: Item) {
        item.itemQuantity = 1
        itemHashSet.add(item)
        Timber.d("${itemHashSet}")
        itemsInvoiceAdapter.submitList(itemHashSet.distinctBy {
            it.itemID
        })
        showSnackbar("تم اضافة القطعة")
        binding.itemsNameTextInput.editText?.text?.clear()
        DialogUtil.dismissRecyclerDialog()
    }


    //endregion


    private fun plusQuantity(postion: Int, item: Item, quantity: TextView) {
        val newQuantity = item.itemQuantity?.inc()
        Timber.d("" + newQuantity)
        quantity.text = "${newQuantity}"
        itemHashSet.toList()[postion].itemQuantity = newQuantity
        itemsInvoiceAdapter.notifyDataSetChanged()
    }

    private fun minuesQuantity(postion: Int, item: Item, quantity: TextView) {
        val newQuantity = item.itemQuantity?.dec()
        Timber.d("" + newQuantity)
        if (newQuantity == 0) {
            itemHashSet.remove(item)
            itemsInvoiceAdapter.submitList(itemHashSet.distinctBy {
                it.itemID
            })
        } else {
            quantity.text = "${newQuantity}"
            itemHashSet.toList()[postion].itemQuantity = newQuantity
        }
        itemsInvoiceAdapter.notifyDataSetChanged()

    }


    private fun fillInvoicItems(): List<InvoiceItem> {
        val itemsList = ArrayList<InvoiceItem>()
        itemHashSet.distinctBy {
            it.itemID
        }.forEach {
            itemsList.add(InvoiceItem(it.itemID, it.itemQuantity))
        }
        return itemsList
    }

    private fun initCalcDiscounttViewModel() {
        invoiceViewModel.calculateDiscount(bodyCalcDiscount())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                binding.tvCalcDiscount.text =
                                    "اجمالي الفاتورة : ${response.data.totalAmountBeforeDiscount}  , " +
                                            "الخصم % : ${response.data.discountPercentage} " +
                                            "\n" +
                                            "قيمة الخصم : ${response.data.discountValue}  , " +
                                            "السعر بعد الخصم : ${response.data.totalAmountAfterDiscount}"
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
            }

    }

    private fun bodyCalcDiscount(): BodyCalculateDiscount {
        return BodyCalculateDiscount(
            args.bodyAddInvoice.invoiceTypeID,
            isOldPrice,
            fillInvoicItems()
        )
    }


    private fun initAddInvoiceViewModel() {
        invoiceViewModel.addInvoice(bodyAddInvoice())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                DialogUtil.showGeneraCongrts(
                                    requireContext(), { goToChooseFragment() },
                                    totalPoints = " تم اضافة الفاتورة بنجاح ورقم الفاتورة هو :  ${response.data.invoiceID}"
                                )
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
            }

    }


    private fun bodyAddInvoice(): BodyAddInvoice {
        return BodyAddInvoice(
            args.bodyAddInvoice.distributorID,
            args.bodyAddInvoice.salesAgentID,
            args.bodyAddInvoice.invoiceTypeID,
            args.bodyAddInvoice.stockID,
            args.bodyAddInvoice.driverID,
            args.bodyAddInvoice.deliveryAgentID,
            args.bodyAddInvoice.paymentTypeID,
            args.bodyAddInvoice.invoiceNote,
            isOldPrice,
            args.bodyAddInvoice.cashAccountID,
            fillInvoicItems()
        )
    }

    private fun goToChooseFragment() {
        findNavController().navigate(R.id.action_addInvoiceFragment2_to_chooseTypeFragment)
    }

}