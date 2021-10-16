package com.bellacity.ui.fragment.editGrnt

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.detailsGrnt.response.GrntItem
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.items.response.GrntItems
import com.bellacity.databinding.FragmentEditGrnt4Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.AddGrntViewModel
import com.bellacity.ui.fragment.addGrnt.adapter.itemsAdapter.GrntItemsAdapter
import com.bellacity.ui.fragment.editGrnt.adapter.itemBeforeAdapter.GrntItemsBeforeAdapter
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import timber.log.Timber


class EditGrnt4Fragment : BaseFragment<FragmentEditGrnt4Binding>() {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var latLng: LatLng? = null

    private var grntDetails: Grnt? = null
    private var bodyEditGrnt: BodyEditGrnt? = null


    private var itemsBefore = ArrayList<GrntItem>()


    private var itemsHashSet = HashSet<GrntItems>()
    private var items = ArrayList<GrntItems>()
    private val grntItemsBeforeAdapter: GrntItemsBeforeAdapter by lazy {
        GrntItemsBeforeAdapter(
            ::plusBeforeQuantity,
            ::minuesBeforeQuantity
        )
    }
    private val viewModel: AddGrntViewModel by viewModels()
    private val viewModelEdit: GrntEditViewModel by viewModels()

    private val grntItemsAdapter: GrntItemsAdapter by lazy {
        GrntItemsAdapter(
            ::plusQuantity,
            ::minuesQuantity
        )
    }
    private val bodyGrntItem = ArrayList<com.bellacity.data.model.base.grntItems.GrntItem>()

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


    private fun initAlertDialogForGPS() {
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
        if (!isMockLocationOn(currentLatLong)) {
            val currentLocation = LatLng(currentLatLong.latitude, currentLatLong.longitude)
            latLng = currentLocation
        }


    }


    //endregion


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditGrnt4Binding.inflate(inflater, container, false)

    override fun initClicks() {

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.nextBtn.setOnClickListener {
            initEditViewModel()
        }
    }

    override fun initViewModel() {
        getGrntSharedViewModel()
    }

    override fun onCreateInit() {
        hideNavBtn()
        initFusedLocation()
        checkPermissionLocation()
    }

    private fun getGrntSharedViewModel() {
        sharedViewModel.editGrnt.observe(viewLifecycleOwner, { response ->
            bodyEditGrnt = response
            sharedViewModel.editGrnt.removeObservers(viewLifecycleOwner)
        })
        sharedViewModel.grntDetails.observe(viewLifecycleOwner, { response ->
            grntDetails = response
            bindData()
            setSelectedItemBefore()
            initItemsViewModel()
            sharedViewModel.grntDetails.removeObservers(viewLifecycleOwner)
        })

    }


    private fun plusBeforeQuantity(postion: Int, item: GrntItem, quantity: TextView) {
        val newQuantity = item.itemQuantity?.inc()
        Timber.d("" + newQuantity)
        quantity.text = "$newQuantity"
        itemsBefore[postion].itemQuantity = newQuantity
        grntItemsBeforeAdapter.notifyDataSetChanged()
    }

    private fun minuesBeforeQuantity(postion: Int, item: GrntItem, quantity: TextView) {
        val newQuantity = item.itemQuantity?.dec()
        Timber.d("" + newQuantity)
        if (newQuantity == 0) {
            itemsBefore.remove(item)
            //   itemsBeforeHashSet.remove(item)
        } else {
            quantity.text = "$newQuantity"
            itemsBefore[postion].itemQuantity = newQuantity
        }
        grntItemsBeforeAdapter.notifyDataSetChanged()
    }


    private fun setSelectedItemBefore() {
        itemsBefore.clear()
        grntDetails?.grntItems?.forEach {
            itemsBefore.add(it)
        }
        submitDataRecycler()
    }


    private fun bindData() {
        binding.grntItemsBeforeAdapter = grntItemsBeforeAdapter
        binding.grntItemsAdapter = grntItemsAdapter
    }

    private fun submitDataRecycler() {
        grntItemsBeforeAdapter.submitList(itemsBefore)
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

            Timber.d("$items")
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
        quantity.text = "$newQuantity"
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
            quantity.text = "$newQuantity"
            items[postion].itemQuantity = newQuantity
        }
        grntItemsAdapter.notifyDataSetChanged()
    }


    private fun fillGrntItems(): ArrayList<com.bellacity.data.model.base.grntItems.GrntItem> {
        bodyGrntItem.clear()
        items.forEach {
            val grntItem =
                com.bellacity.data.model.base.grntItems.GrntItem(it.itemID, it.itemQuantity)
            bodyGrntItem.add(grntItem)
        }
        itemsBefore.forEach {
            val grntItem =
                com.bellacity.data.model.base.grntItems.GrntItem(it.itemID, it.itemQuantity)
            bodyGrntItem.add(grntItem)
        }

        return bodyGrntItem
    }


    private fun initEditViewModel() {
        viewModelEdit.editGrnt(bodyEditGrnt()).observe(viewLifecycleOwner,
            { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                DialogUtil.showGeneraCongrts(
                                    requireContext(), { goToPreviewFragment() },
                                    totalPoints = " مبروك لقد تم تعديل المعاينة بنجاح ولقد تم اضافة نقاط للفني برصيد ${response.data.totalPoints}"
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

    private fun goToPreviewFragment() {
        findNavController().navigate(R.id.action_editGrnt4Fragment_to_edit_button_navigation)
    }

    private fun bodyEditGrnt(): BodyEditGrnt {
        return BodyEditGrnt(
            bodyEditGrnt?.grntID,
            bodyEditGrnt?.techID,
            bodyEditGrnt?.distributorID,
            bodyEditGrnt?.consumerName,
            bodyEditGrnt?.consumerPhone,
            bodyEditGrnt?.consumerAddress,
            fillGrntItems(),
            bodyEditGrnt?.grntItemSerials,
            bodyEditGrnt?.bookNo,
            bodyEditGrnt?.grntType,
            bodyEditGrnt?.grntCobonSerial,
            bodyEditGrnt?.grntItemsType,
            bodyEditGrnt?.grntMerchant,
            binding.giftTextInput.editText?.text.toString(),
            getLocation()?.latitude,
            getLocation()?.longitude
        )
    }

    private fun getLocation(): LatLng? {

        val location: LatLng? = if (binding.chLocation.isChecked) {
            latLng
        } else {
            val latLng =
                LatLng(
                    grntDetails?.grntLat?.toDouble() ?: 0.0,
                    grntDetails?.grntLng?.toDouble() ?: 0.0
                )
            latLng
        }
        return location
    }


    //region detected mock location


    private fun isMockLocationOn(location: Location): Boolean {
        return location.isFromMockProvider
    }

    private fun getListOfFakeLocationApps(context: Context): List<String?> {
        val runningApps = getRunningApps(context)
        val fakeApps: MutableList<String?> = ArrayList()
        for (app in runningApps) {
            if (!isSystemPackage(context, app) && hasAppPermission(
                    context,
                    app,
                    "android.permission.ACCESS_MOCK_LOCATION"
                )
            ) {
                fakeApps.add(getApplicationName(context, app))
            }
        }
        return fakeApps
    }

    private fun getRunningApps(context: Context): List<String> {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps: HashSet<String> = HashSet()
        try {
            val runAppsList = activityManager.runningAppProcesses
            for (processInfo in runAppsList) {
                runningApps.addAll(processInfo.pkgList)
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        try {
            //can throw securityException at api<18 (maybe need "android.permission.GET_TASKS")
            val runningTasks = activityManager.getRunningTasks(1000)
            for (taskInfo in runningTasks) {
                runningApps.add(taskInfo.topActivity!!.packageName)
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        try {
            val runningServices = activityManager.getRunningServices(1000)
            for (serviceInfo in runningServices) {
                runningApps.add(serviceInfo.service.packageName)
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ArrayList(runningApps)
    }

    private fun isSystemPackage(context: Context, app: String?): Boolean {
        val packageManager = context.packageManager
        try {
            val pkgInfo = packageManager.getPackageInfo(app!!, 0)
            return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false
    }

    private fun hasAppPermission(context: Context, app: String?, permission: String): Boolean {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        try {
            packageInfo = packageManager.getPackageInfo(app!!, PackageManager.GET_PERMISSIONS)
            if (packageInfo.requestedPermissions != null) {
                for (requestedPermission in packageInfo.requestedPermissions) {
                    if (requestedPermission == permission) {
                        return true
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false
    }

    private fun getApplicationName(context: Context, packageName: String?): String? {
        var appName = packageName
        val packageManager = context.packageManager
        try {
            appName = packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                    packageName!!,
                    PackageManager.GET_META_DATA
                )
            ).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return appName
    }

    //endregion
}