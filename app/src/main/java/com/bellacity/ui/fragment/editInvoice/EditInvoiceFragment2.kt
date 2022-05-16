package com.bellacity.ui.fragment.editInvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.bellacity.R
import com.bellacity.data.model.calculateDiscount.request.BodyCalculateDiscount
import com.bellacity.data.model.editInvoice.request.BodyEditInvoice
import com.bellacity.data.model.invoiceDetails.response.InvoiceItem
import com.bellacity.data.model.supplementItems.request.BodySupplementItems
import com.bellacity.data.model.supplementItems.response.Item
import com.bellacity.databinding.FragmentEditInvoice2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.InvoiceViewModel
import com.bellacity.ui.fragment.editInvoice.adapter.ItemsInvoiceDetailsAdapter
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

class EditInvoiceFragment2 : BaseFragment<FragmentEditInvoice2Binding>() {

    private val viewModel: SupplementItemsViewModel by viewModels()
    private val invoiceViewModel: InvoiceViewModel by viewModels()
    private val adapter: SupplementItemsAdapter by lazy { SupplementItemsAdapter(::onClickSupplementItems) }


    private var isOldPrice = false
    private var isProject = false


    private val itemsInvoiceDetailsAdapter: ItemsInvoiceDetailsAdapter by lazy {
        ItemsInvoiceDetailsAdapter(
            ::plusQuantity,
            ::minuesQuantity
        )
    }

    private val args by navArgs<EditInvoiceFragment2Args>()
    private val itemHashSet =
        mutableSetOf<InvoiceItem>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditInvoice2Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isOldPrice = isChecked
        }
        binding.nextBtn.setOnClickListener {
            Timber.d("${fillInvoicItems()}")
        }
        binding.nextBtn.setOnClickListener {
            if (itemHashSet.isNotEmpty()) {
                initEditInvoiceViewModel()
            } else {
                showSnackbar("من فضلك اختر الأصناف ")
            }
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

    }

    override fun onCreateInit() {
        bindData()
        searchSupplement()
        checkIsProject()
    }

    private fun checkIsProject() {
        isProject = when (args.bodyEditInvoiceArgs.viewAccessID) {
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

    private fun bindData() {
        val concatADapter = ConcatAdapter(itemsInvoiceDetailsAdapter)
        binding.recyclerView5.adapter = concatADapter
        itemHashSet.addAll(args.invoiceDetails.invoiceItems!!)
        itemsInvoiceDetailsAdapter.submitList(args.invoiceDetails.invoiceItems)

        binding.tvCalcDiscount.text =
            "اجمالي الفاتورة : ${args.invoiceDetails.totalAmountBeforeDiscount}  , " +
                    "الخصم % : ${args.invoiceDetails.discountPercentage} " +
                    "\n" +
                    "قيمة الخصم : ${args.invoiceDetails.discountValue}  , " +
                    "السعر بعد الخصم : ${args.invoiceDetails.totalAmountAfterDiscount}"


        binding.tvAddedBy.text = "تم الاضافة بواسطة : ${args.invoiceDetails.invoiceAddedByUserName}"

    }

    private fun plusQuantity(postion: Int, item: InvoiceItem, quantity: TextView) {
        val newQuantity = item.itemQuantity?.toInt()?.inc()
        quantity.text = "$newQuantity"
        itemHashSet.toList()[postion].itemQuantity = newQuantity?.toDouble()
        Timber.d("${itemHashSet}")

        itemsInvoiceDetailsAdapter.notifyDataSetChanged()
    }

    private fun minuesQuantity(postion: Int, item: InvoiceItem, quantity: TextView) {
        val newQuantity = item.itemQuantity?.dec()
        if (newQuantity == 0.0) {
            itemHashSet.remove(item)
            itemsInvoiceDetailsAdapter.submitList(itemHashSet.distinctBy {
                it.itemID
            })
        } else {
            quantity.text = "${newQuantity}"
            itemHashSet.toList()[postion].itemQuantity = newQuantity
        }


        itemsInvoiceDetailsAdapter.notifyDataSetChanged()

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
            args.bodyEditInvoiceArgs.stockID,
            item,
            isProject = isProject,
            isForGrnt = false
        )
    }

    private fun onClickSupplementItems(postion: Int, item: Item) {
        itemHashSet.add(
            InvoiceItem(
                itemsInvoiceDetailsAdapter.itemCount + 1,
                item.itemID,
                item.itemName,
                item.itemUnit,
                1.toDouble(),
                item.itemSellPrice,
                item.itemSellPrice
            )
        )
        itemsInvoiceDetailsAdapter.submitList(itemHashSet.distinctBy {
            it.itemID
        })
        showSnackbar("تم اضافة القطعة")
        binding.itemsNameTextInput.editText?.text?.clear()
        DialogUtil.dismissRecyclerDialog()
    }


    //endregion

    private fun fillInvoicItems(): List<com.bellacity.data.model.addInvoice.request.InvoiceItem> {
        val itemsList = ArrayList<com.bellacity.data.model.addInvoice.request.InvoiceItem>()

        itemHashSet.distinctBy {
            it.itemID
        }.forEach {
            itemsList.add(
                com.bellacity.data.model.addInvoice.request.InvoiceItem(
                    it.itemID,
                    it.itemQuantity?.toInt()
                )
            )
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
            args.bodyEditInvoiceArgs.viewAccessID,
            isOldPrice,
            fillInvoicItems()
        )
    }


    private fun initEditInvoiceViewModel() {
        invoiceViewModel.editInvoice(bodyEditInvoice())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                goToChooseFragment()
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

    private fun bodyEditInvoice(): BodyEditInvoice {
        return BodyEditInvoice(
            args.bodyEditInvoiceArgs.distributorID,
            args.bodyEditInvoiceArgs.salesAgentID,
            args.bodyEditInvoiceArgs.viewAccessID,
            args.bodyEditInvoiceArgs.invoiceID,
            args.bodyEditInvoiceArgs.stockID,
            args.bodyEditInvoiceArgs.driverID,
            args.bodyEditInvoiceArgs.deliveryAgentID,
            args.bodyEditInvoiceArgs.paymentTypeID,
            args.bodyEditInvoiceArgs.invoiceNote,
            isOldPrice,
            args.bodyEditInvoiceArgs.cashAccountID,
            fillInvoicItems()
        )
    }

    private fun goToChooseFragment() {
        if (findNavController().currentDestination?.id == R.id.editInvoiceFragment2) {
            findNavController().navigate(R.id.action_editInvoiceFragment2_to_chooseTypeFragment)

        }
    }

}