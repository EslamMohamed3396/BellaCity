package com.bellacity.ui.fragment.editInvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bellacity.R
import com.bellacity.data.model.addInvoice.request.InvoiceItem
import com.bellacity.data.model.clientList.request.BodyClientList
import com.bellacity.data.model.clientList.response.Distributor
import com.bellacity.data.model.deliveryAgentList.request.BodyDeliveryAgentList
import com.bellacity.data.model.deliveryAgentList.response.DeliveryAgent
import com.bellacity.data.model.driverList.request.BodyDriverList
import com.bellacity.data.model.driverList.response.Driver
import com.bellacity.data.model.editInvoice.request.BodyEditInvoice
import com.bellacity.data.model.extraOptions.request.BodyExtraOptions
import com.bellacity.data.model.extraOptions.response.ExtraOption
import com.bellacity.data.model.invoiceDetails.request.BodyInvoieDetails
import com.bellacity.data.model.invoiceDetails.response.Invoice
import com.bellacity.data.model.storageList.request.BodyStoarageList
import com.bellacity.data.model.storageList.response.Storage
import com.bellacity.databinding.FragmentEditInvoiceBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.InvoiceViewModel
import com.bellacity.ui.fragment.addInvoice.adapters.adapterDelvieryAgent.DeliveryAgentAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchAgentName.SearchAgentAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchDriver.SearchDriverNameAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchStorage.SearchStorageNameAdapter
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class EditInvoiceFragment : BaseFragment<FragmentEditInvoiceBinding>() {

    private val adapterSearchAgent: SearchAgentAdapter by lazy { SearchAgentAdapter(::actionClickSearchAgentNAme) }
    private val adapterSearchDelivery: DeliveryAgentAdapter by lazy { DeliveryAgentAdapter(::actionClickSearchDeliveryAgent) }
    private val adapterSearchDriver: SearchDriverNameAdapter by lazy { SearchDriverNameAdapter(::actionClickSearcDriverName) }
    private val adapterSearchStorage: SearchStorageNameAdapter by lazy { SearchStorageNameAdapter(::actionClickSearchStorageName) }

    private var isAgentNameFromBind = true
    private var isDeliverAgentFromBind = true
    private var isDriverNameFromBind = true
    private var isStorageNameFromBind = true

    private var isDeliverAgentClicked = false
    private var isDriverNameClicked = false
    private var isStorageNameClicked = false


    private val viewModel: InvoiceViewModel by viewModels()

    private val args by navArgs<EditInvoiceFragmentArgs>()

    private var distributorID: Int? = 0

    private var salesAgentID: Int? = 0

    private var invoiceTypeID: Int? = 0

    private var stockID: Int? = 0

    private var driverID: Int? = 0

    private var deliveryAgentID: Int? = 0

    private var paymentTypeID: Int? = null
    private var cashAccountID: Int? = null

    private var viewAccessID: Int? = 0

    private var invoiceItems: ArrayList<InvoiceItem>? = null

    private var invoice: Invoice? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditInvoiceBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.nextBtn.setOnClickListener {
            if (checkData()) {
                val action =
                    EditInvoiceFragmentDirections.actionEditInvoiceFragmentToEditInvoiceFragment2(
                        bodyEditInvoice(),
                        invoice!!
                    )
                findNavController().navigate(action)
            } else {
                showSnackbar("???? ???????? ???????? ???????? ????????????????")
            }
        }
    }

    override fun initViewModel() {
        initGetInvoiceDetailsViewModel()
    }

    override fun onCreateInit() {
        hideInvoiceNavBtn()
        hideMainNavBtn()
        searchAgentName()
        searchDeliveryName()
        searchDriverName()
        searchStorageName()
    }


    //region get invoice details
    private fun initGetInvoiceDetailsViewModel() {
        viewModel.getInvoiceDetails(bodyInvoiceDetails())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                bindData(response.data.invoice!!)
                            }
                            else -> {
                                findNavController().navigateUp()
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

    private fun bodyInvoiceDetails(): BodyInvoieDetails {
        return BodyInvoieDetails(Constant.SCREEN_ID, args.invoiceId)
    }

    private fun bindData(invoice: Invoice) {
        this.invoice = invoice
        binding.agentNameTextInput.editText?.setText(invoice.distributorName)
        binding.agentAddressTextInput.editText?.setText(invoice.distributorName)
        binding.dateTextInput.editText?.setText(invoice.invoiceAddDate)
        binding.deliveryNameTextInput.editText?.setText(invoice.deliveryAgentName)
        binding.salesAgentNameTextInput.editText?.setText(invoice.salesAgentName)
        binding.driverNameTextInput.editText?.setText(invoice.driverName)
        binding.storageNameTextInput.editText?.setText(invoice.stockName)
        binding.notesTextInput.editText?.setText(invoice.invoiceNote)

        distributorID = invoice.distributorID
        salesAgentID = invoice.salesAgentID

        invoiceTypeID = invoice.invoiceTypeID

        stockID = invoice.stockID

        driverID = invoice.driverID

        viewAccessID = invoice.invoiceTypeID
        deliveryAgentID = invoice.deliveryAgentID


        invoice.invoiceItems?.forEach {
            invoiceItems?.add(InvoiceItem(it.itemID, it.itemQuantity?.toInt()))
        }

        paymentTypeID = invoice.paymentTypeID
        cashAccountID = invoice.cashAccountID
        when (paymentTypeID) {
            0 -> {
                binding.extraOptionsTextInput.editText?.setText(invoice.cashAccountName)
            }
            else -> {
                binding.extraOptionsTextInput.editText?.setText(invoice.paymentTypeName)
            }
        }
        initExtraOptinsViewModel()


    }
    //endregion

    //region search agent name
    private fun searchAgentName() {
        Observable.create { emitter: ObservableEmitter<String?> ->
            binding.agentNameTextInput.editText?.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    emitter.onNext(text.toString())
                } else {
                    binding.agentAddressTextInput.editText?.text?.clear()
                    binding.salesAgentNameTextInput.editText?.text?.clear()
                    adapterSearchAgent.submitList(null)
                    isAgentNameFromBind = false
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (binding.salesAgentNameTextInput.editText?.text?.isEmpty() == true && !isAgentNameFromBind) {
                    initSearchAgentNameViewModel(s!!)
                }
            }
    }

    private fun initSearchAgentNameViewModel(item: String) {
        adapterSearchAgent.submitList(null)
        viewModel.clientList(bodyClientList(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                adapterSearchAgent.submitList(response.data.distributorList)
                                DialogUtil.showRecycler(requireContext(), adapterSearchAgent)

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

    private fun bodyClientList(name: String): BodyClientList {
        return BodyClientList(page = 0, distributorName = name)
    }

    private fun actionClickSearchAgentNAme(postion: Int, item: Distributor) {
        distributorID = item.distributorID
        salesAgentID = item.salesAgentID
        binding.agentNameTextInput.editText?.setText(item.distributorName)
        binding.agentAddressTextInput.editText?.setText("${item.distributorGovernorate}, ${item.distributorCity}")
        binding.salesAgentNameTextInput.editText?.setText(item.salesAgentName)
        DialogUtil.dismissRecyclerDialog()
    }


    //endregion

    //region search delivery name
    private fun searchDeliveryName() {
        Observable.create { emitter: ObservableEmitter<String?> ->
            binding.deliveryNameTextInput.editText?.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    emitter.onNext(text.toString())
                } else {
                    isDeliverAgentClicked = false
                    isDeliverAgentFromBind = false
                    adapterSearchDelivery.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isDeliverAgentClicked && !isDeliverAgentFromBind) {
                    initSearchDeliveryNameViewModel(s!!)
                }
            }
    }

    private fun initSearchDeliveryNameViewModel(item: String) {
        adapterSearchDelivery.submitList(null)
        viewModel.deliveryAgentList(bodyDeliveryAgent(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                adapterSearchDelivery.submitList(response.data.deliveryAgents)
                                DialogUtil.showRecycler(requireContext(), adapterSearchDelivery)

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

    private fun bodyDeliveryAgent(name: String): BodyDeliveryAgentList {
        return BodyDeliveryAgentList(deliveryAgentName = name)
    }

    private fun actionClickSearchDeliveryAgent(postion: Int, item: DeliveryAgent) {
        deliveryAgentID = item.deliveryAgentID
        isDeliverAgentClicked = true
        binding.deliveryNameTextInput.editText?.setText(item.deliveryAgentName)
        DialogUtil.dismissRecyclerDialog()
    }
    //endregion

    //region search driver name

    private fun searchDriverName() {
        Observable.create { emitter: ObservableEmitter<String?> ->
            binding.driverNameTextInput.editText?.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    emitter.onNext(text.toString())
                } else {
                    isDriverNameClicked = false
                    isDriverNameFromBind = false
                    adapterSearchDriver.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isDriverNameClicked && !isDriverNameFromBind) {
                    initSearchDriverNameViewModel(s!!)
                }
            }
    }

    private fun initSearchDriverNameViewModel(item: String) {
        adapterSearchDriver.submitList(null)
        viewModel.driverAgentList(bodyDriverList(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                adapterSearchDriver.submitList(response.data.drivers)
                                DialogUtil.showRecycler(requireContext(), adapterSearchDriver)

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

    private fun bodyDriverList(name: String): BodyDriverList {
        return BodyDriverList(driverName = name)
    }


    private fun actionClickSearcDriverName(postion: Int, item: Driver) {
        driverID = item.driverID
        isDriverNameClicked = true
        binding.driverNameTextInput.editText?.setText(item.driverName)
        DialogUtil.dismissRecyclerDialog()
    }

    //endregion

    //region search storage name

    private fun searchStorageName() {
        Observable.create { emitter: ObservableEmitter<String?> ->
            binding.storageNameTextInput.editText?.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    emitter.onNext(text.toString())
                } else {
                    isStorageNameClicked = false
                    isStorageNameFromBind = false
                    adapterSearchStorage.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isStorageNameClicked && !isStorageNameFromBind) {
                    initSearchStorageNameViewModel(s!!)
                }
            }
    }

    private fun initSearchStorageNameViewModel(item: String) {
        adapterSearchStorage.submitList(null)
        viewModel.storageList(bodyStorageList(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {

                                adapterSearchStorage.submitList(response.data.storageList)
                                DialogUtil.showRecycler(requireContext(), adapterSearchStorage)
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

    private fun bodyStorageList(name: String): BodyStoarageList {
        return BodyStoarageList(viewAccessID = 55, storageName = name)
    }


    private fun actionClickSearchStorageName(postion: Int, item: Storage) {
        stockID = item.storageID
        isStorageNameClicked = true
        binding.storageNameTextInput.editText?.setText(item.storageName)
        DialogUtil.dismissRecyclerDialog()
    }

    //endregion

    //region extra option spinner


    private fun initExtraOptinsViewModel() {
        viewModel.extraOptionList(bodyExtraOptionsList())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                fillSpinnerExtraOption(response.data.extraOptions)
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

    private fun bodyExtraOptionsList(): BodyExtraOptions {
        return BodyExtraOptions(Constant.SCREEN_ID)
    }

    private fun fillSpinnerExtraOption(extraOptionsList: List<ExtraOption>?) {
        if (extraOptionsList?.isNotEmpty() == true) {
            val extraOptions = ArrayList<String>()
            extraOptionsList.first().options?.forEach {
                extraOptions.add(it.value!!)
            }

            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_drop_down,
                    extraOptions
                )
            binding.autoExtraOptionsName.setAdapter(dataAdapter)

            binding.autoExtraOptionsName.setOnItemClickListener { _, _, position, _ ->
                when (extraOptionsList.first().displayName) {
                    "CashAccountID" -> {
                        cashAccountID = extraOptionsList.first().options?.get(position)?.iD
                    }
                    else -> {
                        paymentTypeID = extraOptionsList.first().options?.get(position)?.iD
                    }

                }
            }
        }

    }
    //endregion


    private fun checkData(): Boolean {
        return distributorID != 0
                && salesAgentID != 0
                && stockID != 0
                && driverID != 0
                && deliveryAgentID != 0
                && (paymentTypeID != null
                || cashAccountID != null)
                && binding.notesTextInput.editText?.text.toString().isNotEmpty()
    }

    private fun bodyEditInvoice(): BodyEditInvoice {
        return BodyEditInvoice(
            distributorID,
            salesAgentID,
            viewAccessID,
            args.invoiceId,
            stockID,
            driverID,
            deliveryAgentID,
            paymentTypeID,
            binding.notesTextInput.editText?.text.toString(),
            null,
            cashAccountID,
            invoiceItems
        )
    }


}