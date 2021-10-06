package com.bellacity.ui.fragment.editGrnt

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bellacity.R
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.databinding.FragmentEditGrnt4Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.Constant
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
    ) = FragmentEditGrnt4Binding.inflate(inflater, container, false)

    override fun initClicks() {

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

            sharedViewModel.grntDetails.removeObservers(viewLifecycleOwner)
        })

    }
//
//    private fun checkData(): Boolean {
//        return items.isNotEmpty()
//                && latLng != null
//    }
}