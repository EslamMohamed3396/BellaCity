package com.bellacity.ui.fragment.addInvoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.addInvoice.request.BodyAddInvoice
import com.bellacity.data.model.addInvoice.response.ResponseAddInvoice
import com.bellacity.data.model.calculateDiscount.request.BodyCalculateDiscount
import com.bellacity.data.model.calculateDiscount.response.ResponseCalculateDiscount
import com.bellacity.data.model.clientList.request.BodyClientList
import com.bellacity.data.model.clientList.response.ResponseClientList
import com.bellacity.data.model.deliveryAgentList.request.BodyDeliveryAgentList
import com.bellacity.data.model.deliveryAgentList.response.ResponseDeliveryAgentList
import com.bellacity.data.model.driverList.request.BodyDriverList
import com.bellacity.data.model.driverList.response.ResponseDriverList
import com.bellacity.data.model.extraOptions.request.BodyExtraOptions
import com.bellacity.data.model.extraOptions.response.ResponseExtraOptions
import com.bellacity.data.model.invoiceDetails.request.BodyInvoieDetails
import com.bellacity.data.model.invoiceDetails.response.ResponsInvoiceDetails
import com.bellacity.data.model.invoices.request.BodyInvoices
import com.bellacity.data.model.invoices.response.ResponseInvoices
import com.bellacity.data.model.storageList.request.BodyStoarageList
import com.bellacity.data.model.storageList.response.ResponseStorageList
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class InvoiceViewModel : BaseViewModel() {
    private var _clientListMutableLiveData =
        MutableLiveData<Resource<ResponseClientList>>()

    fun clientList(bodyClientList: BodyClientList): LiveData<Resource<ResponseClientList>> {
        return callApi(
            Client.getInstance()?.clientList(bodyClientList)!!,
            _clientListMutableLiveData
        )
    }

    private var _deliveryListMutableLiveData =
        MutableLiveData<Resource<ResponseDeliveryAgentList>>()

    fun deliveryAgentList(bodyDeliveryAgentList: BodyDeliveryAgentList): LiveData<Resource<ResponseDeliveryAgentList>> {
        return callApi(
            Client.getInstance()?.deliveryAgentList(bodyDeliveryAgentList)!!,
            _deliveryListMutableLiveData
        )
    }

    private var _driverListMutableLiveData =
        MutableLiveData<Resource<ResponseDriverList>>()

    fun driverAgentList(bodyDriverList: BodyDriverList): LiveData<Resource<ResponseDriverList>> {
        return callApi(
            Client.getInstance()?.driverAgentList(bodyDriverList)!!,
            _driverListMutableLiveData
        )
    }

    private var storageListMutableLiveData =
        MutableLiveData<Resource<ResponseStorageList>>()

    fun storageList(bodyStorageList: BodyStoarageList): LiveData<Resource<ResponseStorageList>> {
        return callApi(
            Client.getInstance()?.storageList(bodyStorageList)!!,
            storageListMutableLiveData
        )
    }


    private var extraOptionsListMutableLiveData =
        MutableLiveData<Resource<ResponseExtraOptions>>()

    fun extraOptionList(bodyExtraOptions: BodyExtraOptions): LiveData<Resource<ResponseExtraOptions>> {
        return callApi(
            Client.getInstance()?.extraOptionList(bodyExtraOptions)!!,
            extraOptionsListMutableLiveData
        )
    }


    private var calculateDiscountListMutableLiveData =
        MutableLiveData<Resource<ResponseCalculateDiscount>>()

    fun calculateDiscount(bodyCalculateDiscount: BodyCalculateDiscount): LiveData<Resource<ResponseCalculateDiscount>> {
        return callApi(
            Client.getInstance()?.calculateDiscount(bodyCalculateDiscount)!!,
            calculateDiscountListMutableLiveData
        )
    }

    private var addInvoiceMutableLiveData =
        MutableLiveData<Resource<ResponseAddInvoice>>()

    fun addInvoice(bodyAddInvoice: BodyAddInvoice): LiveData<Resource<ResponseAddInvoice>> {
        return callApi(
            Client.getInstance()?.addInvoice(bodyAddInvoice)!!,
            addInvoiceMutableLiveData
        )
    }

    private var invoicesMutableLiveData =
        MutableLiveData<Resource<ResponseInvoices>>()

    fun getInvoices(bodyInvoices: BodyInvoices): LiveData<Resource<ResponseInvoices>> {
        return callApi(
            Client.getInstance()?.getInvoices(bodyInvoices)!!,
            invoicesMutableLiveData
        )
    }


    private var invoiceDetailsMutableLiveData =
        MutableLiveData<Resource<ResponsInvoiceDetails>>()

    fun getInvoiceDetails(bodyInvoiceDetails: BodyInvoieDetails): LiveData<Resource<ResponsInvoiceDetails>> {
        return callApi(
            Client.getInstance()?.getInvoiceDetails(bodyInvoiceDetails)!!,
            invoiceDetailsMutableLiveData
        )
    }


}