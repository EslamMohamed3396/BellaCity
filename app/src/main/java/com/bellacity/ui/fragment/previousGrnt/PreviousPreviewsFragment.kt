package com.bellacity.ui.fragment.previousGrnt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bellacity.R
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.model.deleteGrnt.request.BodyDeleteGrnt
import com.bellacity.data.model.previewsGrnt.response.GrntSimple
import com.bellacity.databinding.FragmentPreviousPreviewsBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.previousGrnt.adapter.PreviewsGrntAdapter
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource

class PreviousPreviewsFragment : BaseFragment<FragmentPreviousPreviewsBinding>(), ILoading {

    private val viewModel: PreviousPreviewsViewModel by viewModels()
    private val previousPreviewAdapter: PreviewsGrntAdapter by lazy {
        PreviewsGrntAdapter(
            ::clickOnItem,
            ::clickDeleteGrnt
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPreviousPreviewsBinding.inflate(inflater, container, false)

    override fun initClicks() {
    }

    override fun initViewModel() {
        initPreviousPreviewViewModel()
    }

    override fun onCreateInit() {
        bindData()
        showNavBtn()
    }


    private fun bindData() {
        binding.adapter = previousPreviewAdapter
    }

    private fun initPreviousPreviewViewModel() {
        previousPreviewAdapter.submitList(null)
        viewModel.getGrnt()
        viewModel._getrntMutableLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            if (response.data.grntSimpleList?.isNullOrEmpty()!!) {

                                binding.tvEmpty.visibility = View.VISIBLE
                            } else {
                                previousPreviewAdapter.submitList(response.data.grntSimpleList)
                                binding.tvEmpty.visibility = View.GONE
                            }
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

    override fun onLoading() {
        DialogUtil.showDialog(requireContext())
    }

    override fun onLoadingFinish() {
        DialogUtil.dismissDialog()
    }


    private fun clickOnItem(postion: Int, item: GrntSimple) {
        sharedViewModel.saveSerialGrntId(item.grntSerial!!)
        findNavController().navigate(R.id.action_edit_button_navigation_to_detailsGrntFragment)
    }


    private fun clickDeleteGrnt(postion: Int, item: GrntSimple) {
        confirmDeleteGrnt(postion, item)
    }


    private fun initDeleteGrntViewModel(grntId: Int) {
        viewModel.deleteGrnt(bodyDeleteGrnt(grntId)).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            showSnackbar("تم حذف المعاينة بنجاح")
                            initPreviousPreviewViewModel()
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

    private fun bodyDeleteGrnt(grntId: Int): BodyDeleteGrnt {
        return BodyDeleteGrnt(grntId)
    }


    private fun confirmDeleteGrnt(postion: Int, item: GrntSimple) {
        MaterialDialog(requireContext()).show {
            title(text = " تأكيد")
            message(text = " هل انت متأكد انك تريد حذف المعاينة رقم ${item.grntSerial}")
            positiveButton(R.string.yes) { dialog ->
                initDeleteGrntViewModel(item.grntSerial!!)
                dialog.dismiss()
            }
            negativeButton(R.string.cancel) { dialog ->
                dialog.dismiss()
            }
        }
    }


}