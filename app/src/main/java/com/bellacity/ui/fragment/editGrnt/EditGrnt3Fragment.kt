package com.bellacity.ui.fragment.editGrnt

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bellacity.R
import com.bellacity.data.model.checkSerial.request.BodyCheckSerial
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.serialFromImage.request.BodySerialFromImage
import com.bellacity.databinding.FragmentEditGrnt3Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.AddGrntViewModel
import com.bellacity.ui.fragment.addGrnt.adapter.vaildSerialAdapter.ValidSerialAdapter
import com.bellacity.ui.fragment.editGrnt.adapter.serialBeforeAdapter.SerialBeforeAdapter
import com.bellacity.utilities.CheckValidData
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.ImageUtil
import com.bellacity.utilities.Resource
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType
import timber.log.Timber


class EditGrnt3Fragment : BaseFragment<FragmentEditGrnt3Binding>() {


    private var grntDetails: Grnt? = null
    private var bodyEditGrnt: BodyEditGrnt? = null
    private val serialBeforeAdapter: SerialBeforeAdapter by lazy { SerialBeforeAdapter(::clickOnDeleteSerial) }

    private val selectedSerialList = ArrayList<String>()
    private var serialBeforeList = ArrayList<String>()
    private var serialValidList = ArrayList<String>()
    private var imageBitmap: String? = null

    private val viewModel: AddGrntViewModel by viewModels()

    private val validSerialAdapter: ValidSerialAdapter by lazy { ValidSerialAdapter(::deleteCheckedSerial) }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditGrnt3Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.checkBtn.setOnClickListener {
            if (checkSerial()) {
                initCheckSerialViewModel(binding.serialTextInput.editText?.text.toString())
            }
        }
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.imTakePhoto.setOnClickListener {
            pickImage()
        }

        binding.nextBtn.setOnClickListener {
            Timber.d("$selectedSerialList")
            goToEditGrant4()
        }

    }

    override fun initViewModel() {
        getGrntSharedViewModel()
    }

    override fun onCreateInit() {
        visableCheckSerialButton()
        hideMainNavBtn()

    }


    private fun bodyEditGrnt(): BodyEditGrnt {
        return BodyEditGrnt(
            bodyEditGrnt?.grntID,
            bodyEditGrnt?.techID,
            bodyEditGrnt?.distributorID,
            bodyEditGrnt?.consumerName,
            bodyEditGrnt?.consumerPhone,
            bodyEditGrnt?.consumerAddress,
            null,
            null,
            selectedSerialList,
            bodyEditGrnt?.bookNo,
            bodyEditGrnt?.grntType,
            bodyEditGrnt?.grntCobonSerial,
            bodyEditGrnt?.grntItemsType,
            bodyEditGrnt?.grntMerchant,
            grntDetails?.grntGift,
            grntDetails?.grntLat?.toDouble(),
            grntDetails?.grntLng?.toDouble()
        )
    }


    private fun goToEditGrant4() {
        sharedViewModel.saveEditGrnt(bodyEditGrnt())
        sharedViewModel.saveGrntDetails(grntDetails!!)
        if (bodyEditGrnt().grntType == 1) {
            findNavController().navigate(R.id.action_editGrnt3Fragment_to_editGrntMarmaFragment)
        } else {
            findNavController().navigate(R.id.action_editGrnt3Fragment_to_editGrnt4Fragment)

        }
    }

    private fun bindData() {
        binding.serialBeforeAdapter = serialBeforeAdapter
        binding.validSerialAdapter = validSerialAdapter

    }


    private fun getGrntSharedViewModel() {
        sharedViewModel.editGrnt.observe(viewLifecycleOwner, { response ->
            bodyEditGrnt = response
            sharedViewModel.editGrnt.removeObservers(viewLifecycleOwner)
        })
        sharedViewModel.grntDetails.observe(viewLifecycleOwner, { response ->
            grntDetails = response
            bindData()
            setSelectedSerial()
            bindSelectedSerialRecycler()
            sharedViewModel.grntDetails.removeObservers(viewLifecycleOwner)
        })

    }


    private fun setSelectedSerial() {
        selectedSerialList.clear()
        grntDetails?.grntPartSerials?.forEach {
            selectedSerialList.add(it)
        }
    }


    private fun confirmDeleteCobon(postion: Int, item: String) {
        MaterialDialog(requireContext()).show {
            title(text = " تأكيد")
            message(text = " هل انت متأكد انك تريد حذف السريال رقم $item")
            positiveButton(R.string.yes) { dialog ->
                selectedSerialList.remove(item)
                serialBeforeList.removeAt(postion)
                serialBeforeAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            negativeButton(R.string.cancel) { dialog ->
                dialog.dismiss()
            }
        }
    }


    private fun clickOnDeleteSerial(postion: Int, item: String) {
        confirmDeleteCobon(postion, item)
    }


    private fun bindSelectedSerialRecycler() {
        serialBeforeList = grntDetails?.grntPartSerials as ArrayList<String>
        if (!grntDetails?.grntPartSerials.isNullOrEmpty()) {
            serialBeforeAdapter.submitList(serialBeforeList)
        }
    }


    private fun pickImage() {
        val setup = PickSetup()
            .setPickTypes(EPickType.CAMERA)
            .setSystemDialog(false)

        PickImageDialog.build(setup)
            .setOnPickResult {
                if (it.error == null) {
                    imageBitmap = ImageUtil.encodeImage(it.bitmap)!!
                    initSerialFromImageViewModel(imageBitmap!!)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "حدث خطأ من فضلك حاول مرة اخري",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }.setOnPickCancel {
            }.show(childFragmentManager)
    }

    private fun initSerialFromImageViewModel(image: String) {
        Timber.d("initSerialFromImageViewModel")
        viewModel.textFromImage(bodySerialFromImage(image))
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                viewModel.textFromImage(bodySerialFromImage(image))
                                    .removeObservers(viewLifecycleOwner)
                                initCheckSerialViewModel(response.data.imageText!!)
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

    private fun bodySerialFromImage(image: String): BodySerialFromImage {
        return BodySerialFromImage(image)
    }

    private fun initCheckSerialViewModel(serial: String) {
        Timber.d("initCheckSerialViewModel")
        viewModel.checkSerial(bodyCheckSerial(serial))
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                viewModel.checkSerial(bodyCheckSerial(serial))
                                    .removeObservers(viewLifecycleOwner)
                                selectedSerialList.add(serial.trim())
                                serialValidList.add(serial.trim())
                                validSerialAdapter.submitList(serialValidList)
                                validSerialAdapter.notifyDataSetChanged()
                                binding.serialTextInput.editText?.text?.clear()
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

    private fun bodyCheckSerial(serial: String): BodyCheckSerial {
        return BodyCheckSerial(serial)
    }

    private fun deleteCheckedSerial(postion: Int, item: String) {
        selectedSerialList.remove(item)
        serialValidList.removeAt(postion)
        validSerialAdapter.notifyDataSetChanged()
    }

    private fun visableCheckSerialButton() {
        binding.serialTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.checkBtn.visibility = View.INVISIBLE
                } else {
                    binding.checkBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    private fun checkSerial(): Boolean {
        return CheckValidData.checkSerial(binding.serialTextInput)
    }

}