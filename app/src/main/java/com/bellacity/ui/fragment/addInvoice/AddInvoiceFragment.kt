package com.bellacity.ui.fragment.addInvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bellacity.R
import com.bellacity.data.model.addInvoice.request.BodyAddInvoice
import com.bellacity.data.model.clientList.request.BodyClientList
import com.bellacity.data.model.clientList.response.Distributor
import com.bellacity.data.model.deliveryAgentList.request.BodyDeliveryAgentList
import com.bellacity.data.model.deliveryAgentList.response.DeliveryAgent
import com.bellacity.data.model.driverList.request.BodyDriverList
import com.bellacity.data.model.driverList.response.Driver
import com.bellacity.data.model.extraOptions.request.BodyExtraOptions
import com.bellacity.data.model.extraOptions.response.ExtraOption
import com.bellacity.data.model.storageList.request.BodyStoarageList
import com.bellacity.data.model.storageList.response.Storage
import com.bellacity.databinding.FragmentAddInvoiceBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.adapters.adapterDelvieryAgent.DeliveryAgentAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchAgentName.SearchAgentAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchDriver.SearchDriverNameAdapter
import com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchStorage.SearchStorageNameAdapter
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class AddInvoiceFragment : BaseFragment<FragmentAddInvoiceBinding>() {

    private val adapterSearchAgent: SearchAgentAdapter by lazy { SearchAgentAdapter(::actionClickSearchAgentNAme) }
    private val adapterSearchDelivery: DeliveryAgentAdapter by lazy { DeliveryAgentAdapter(::actionClickSearchDeliveryAgent) }
    private val adapterSearchDriver: SearchDriverNameAdapter by lazy { SearchDriverNameAdapter(::actionClickSearcDriverName) }
    private val adapterSearchStorage: SearchStorageNameAdapter by lazy { SearchStorageNameAdapter(::actionClickSearchStorageName) }
    private val viewModel: InvoiceViewModel by viewModels()
    private val args by navArgs<AddInvoiceFragmentArgs>()

    private var isDeliverAgentClicked = false
    private var isDriverNameClicked = false
    private var isStorageNameClicked = false

    private var distributorID: Int? = 0

    private var salesAgentID: Int? = 0

    private var invoiceTypeID: Int? = 0

    private var stockID: Int? = 0

    private var driverID: Int? = 0

    private var deliveryAgentID: Int? = 0

    private var paymentTypeID: Int? = null
    private var cashAccountID: Int? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddInvoiceBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.nextBtn.setOnClickListener {
            if (checkData()) {

            } else {
                showSnackbar("من فضلك ادخل جميع البيانات")
            }
        }
    }

    override fun initViewModel() {

    }

    override fun onCreateInit() {
        hideMainNavBtn()
        hideInvoiceNavBtn()
        searchAgentName()
        searchDeliveryName()
        searchDriverName()
        searchStorageName()
        initExtraOptinsViewModel()
        bindData()
    }

    private fun bindData() {
        binding.currentDate = Calendar.getInstance().time

    }

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
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (binding.salesAgentNameTextInput.editText?.text?.isEmpty() == true) {
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
                    adapterSearchDelivery.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isDeliverAgentClicked) {
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
                    adapterSearchDriver.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isDriverNameClicked) {
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
                    adapterSearchStorage.submitList(null)
                }
            }
        }.debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s: String? ->
                if (!isStorageNameClicked) {
                    initSearchStorageNameViewModel(s!!)
                }
            }
    }

    private fun initSearchStorageNameViewModel(item: String) {
        adapterSearchDriver.submitList(null)
        viewModel.storageList(bodyStorageList(item))
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                adapterSearchStorage.submitList(response.data.storageList)
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
        return BodyExtraOptions(viewAccessID = 55)
    }

    private fun fillSpinnerExtraOption(extraOptionsList: List<ExtraOption>?) {
        if (extraOptionsList?.isNotEmpty() == true) {
            binding.extraOptionsTextInput.editText?.hint = extraOptionsList.first().displayName

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
                        paymentTypeID = null
                    }
                    else -> {
                        paymentTypeID = extraOptionsList.first().options?.get(position)?.iD
                        cashAccountID = null
                    }

                }
            }
        }

    }
    //endregion


    private fun bodyAddInvoice(): BodyAddInvoice {
        return BodyAddInvoice(
            distributorID,
            salesAgentID,
            args.screeinId,
            stockID,
            driverID,
            deliveryAgentID,
            paymentTypeID,
            binding.notesTextInput.editText?.text.toString(),
            null,
            cashAccountID,
            null
        )
    }

    private fun checkData(): Boolean {
        return distributorID != 0
                && salesAgentID != 0
                && stockID != 0
                && driverID != 0
                && deliveryAgentID != 0
                && paymentTypeID != null
                && cashAccountID != null
                && binding.notesTextInput.editText?.text.toString().isNotEmpty()
    }

}