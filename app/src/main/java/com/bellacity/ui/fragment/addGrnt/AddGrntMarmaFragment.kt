package com.bellacity.ui.fragment.addGrnt

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.base.grntItems.GrntItem
import com.bellacity.data.model.supplementItems.request.BodySupplementItems
import com.bellacity.data.model.supplementItems.response.Item
import com.bellacity.databinding.FragmentAddGrntMarmaBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.adapters.adapterBindItems.ItemsInvoiceAdapter
import com.bellacity.ui.fragment.supplementItems.SupplementItemsViewModel
import com.bellacity.ui.fragment.supplementItems.adapter.SupplementItemsAdapter
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


class AddGrntMarmaFragment : BaseFragment<FragmentAddGrntMarmaBinding>() {

    private val viewModel: SupplementItemsViewModel by viewModels()
    private val adapter: SupplementItemsAdapter by lazy { SupplementItemsAdapter(::onClickSupplementItems) }
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var latLng: LatLng? = null
    private var bodyAddGrnt: BodyAddGrnt? = null
    private val bodyGrntItem = ArrayList<GrntItem>()

    private val viewModelAdd: AddGrntViewModel by viewModels()

    private val itemHashSet = mutableSetOf<Item>()
    private val itemsInvoiceAdapter: ItemsInvoiceAdapter by lazy {
        ItemsInvoiceAdapter(
            ::plusQuantity,
            ::minuesQuantity
        )
    }


    val requestPermissionLocation =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION]!! ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION]!!
            ) {
                Timber.d("granted requestPermissionLocation")
                if (checkGPSEnabled()) {
                    Timber.d("getLastLocation")
                    getLastLocation()
                } else {
                    Timber.d("initAlertDialogForGPS")

                    initAlertDialogForGPS()
                }

            } else {
                Timber.d("denied requestPermissionLocation")
                showSnackbar("من فضلك فعل الاذن لتحديد الموقع")
            }
        }

    val openGps =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            Timber.d("openGps")
            if (checkGPSEnabled()) {
                Timber.d("Granted")
                getLastLocation()
            } else {
                Timber.d("denied")
                initAlertDialogForGPS()
            }
        }


    private fun checkPermissionLocation() {
        when {
            checkSelfPermission() -> {
                if (checkGPSEnabled()) {
                    getLastLocation()
                } else {
                    initAlertDialogForGPS()
                }

            }
            shouldShowRequestPermission() -> {
                requestPermissionLocation()
            }
            else -> {
                requestPermissionLocation()
            }
        }

    }


    private fun checkSelfPermission(): Boolean {
        return ContextCompat
            .checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermission(): Boolean {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun requestPermissionLocation() {
        requestPermissionLocation.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun checkGPSEnabled(): Boolean {
        val locationManager =
            (requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestGps() {
        val intent =
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

        openGps.launch(intent)
    }


    //region get location
    private fun initFusedLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

    }

    private fun getLastLocation() {
        if (isServicesOk()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mFusedLocationClient!!.lastLocation.addOnCompleteListener { task: Task<Location?> ->
                val location = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    setCurrentLatLong(location)
                }
            }.addOnFailureListener { e: Exception ->
            }

        }
    }

    //endregion

    //region check gps, ask  services and gps is enabled
    private fun isServicesOk(): Boolean {
        val googleApi = GoogleApiAvailability.getInstance()
        val result = googleApi.isGooglePlayServicesAvailable(requireContext())
        when {
            result == ConnectionResult.SUCCESS -> {
                return true
            }
            googleApi.isUserResolvableError(result) -> {
                val dialog =
                    googleApi.getErrorDialog(
                        this, result, Constant.ERROR_DIALOG_REQUEST
                    ) { task: DialogInterface? ->
                        Toast.makeText(
                            requireContext(),
                            requireContext().resources.getString(R.string.dialog_cancelled),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                dialog?.show()
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    requireContext().resources.getString(R.string.play_services_required),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return false
    }


    fun initAlertDialogForGPS() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(requireContext().resources.getString(R.string.permission))
            .setMessage(requireContext().resources.getString(R.string.gps_required))
            .setPositiveButton(
                requireContext().resources.getString(R.string.yes)
            ) { dialogInterface: DialogInterface?, i: Int ->
                requestGps()
            }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    //endregion

    //region request current loation

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            setCurrentLatLong(mLastLocation)

        }
    }


    private fun setCurrentLatLong(currentLatLong: Location) {
        val currentLocation = LatLng(currentLatLong.latitude, currentLatLong.longitude)
        latLng = currentLocation
    }


    //endregion


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddGrntMarmaBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.nextBtn.setOnClickListener {
            if (checkData()) {
                initaddGrntViewModel()
            } else {
                showSnackbar("من فضلك اكمل جميع البيانات")
            }
        }
    }

    override fun initViewModel() {
        getGrntSharedViewModel()
    }

    override fun onCreateInit() {
        searchSupplement()
        bindAdapter()
        initFusedLocation()
        checkPermissionLocation()
    }

    private fun bindAdapter() {
        binding.recyclerView.adapter = itemsInvoiceAdapter
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
            null,
            item,
            isProject = false,
            isForGrnt = true
        )
    }

    private fun onClickSupplementItems(postion: Int, item: Item) {
        item.itemQuantity = 1
        itemHashSet.add(item)
        itemsInvoiceAdapter.submitList(itemHashSet.distinctBy {
            it.itemID
        })
        showSnackbar("تم اضافة القطعة")
        binding.itemsNameTextInput.editText?.text?.clear()
        DialogUtil.dismissRecyclerDialog()
    }


    //endregion

    private fun checkData(): Boolean {
        return itemHashSet.isNotEmpty()
                && latLng != null
    }

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


    private fun fillGrntItems(): ArrayList<GrntItem> {
        val itemsList = ArrayList<GrntItem>()
        itemHashSet.distinctBy {
            it.itemID
        }.forEach {
            val grntItem = GrntItem(it.itemID, it.itemQuantity)
            itemsList.add(grntItem)
        }
        return itemsList
    }


    private fun initaddGrntViewModel() {
        viewModelAdd.addGrnt(bodyAddGrnt())
        viewModelAdd._addGrntMutableLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            DialogUtil.showGeneraCongrts(
                                requireContext(), { goToPreviewFragment() },
                                totalPoints = " مبروك لقد تم ارسال المعاينة بنجاح ولقد تم اضافة نقاط للفني برصيد ${response.data.totalPoints}"
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
        })
    }

    private fun bodyAddGrnt(): BodyAddGrnt {
        return BodyAddGrnt(
            bodyAddGrnt?.techID,
            bodyAddGrnt?.distributorID,
            bodyAddGrnt?.consumerName,
            bodyAddGrnt?.consumerPhone,
            bodyAddGrnt?.consumerAddress,
            fillGrntItems(),
            bodyAddGrnt?.grntItemSerials,
            bodyAddGrnt?.bookNo,
            bodyAddGrnt?.grntType,
            bodyAddGrnt?.grntCobonSerial,
            bodyAddGrnt?.grntItemsType,
            bodyAddGrnt?.grntMerchant,
            binding.giftTextInput.editText?.text.toString(),
            latLng?.latitude,
            latLng?.longitude
        )
    }


    private fun getGrntSharedViewModel() {
        sharedViewModel.addGrnt.observe(viewLifecycleOwner, { response ->
            bodyAddGrnt = response

        })

    }

    private fun goToPreviewFragment() {
        findNavController().navigate(R.id.action_addGrnt3Fragment_to_edit_button_navigation)
    }

}