package com.bellacity.ui.fragment.previousPreviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.model.previousPreview.response.Grnt
import com.bellacity.databinding.FragmentPreviousPreviewsBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.previousPreviews.adapter.PreviousPreviewAdapter
import com.bellacity.utilities.DialogUtil

class PreviousPreviewsFragment : BaseFragment<FragmentPreviousPreviewsBinding>(), ILoading {

    private val viewModel: PreviousPreviewsViewModel by viewModels()
    private val previousPreviewAdapter: PreviousPreviewAdapter by lazy { PreviousPreviewAdapter(::clickOnItem) }

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
        viewModel.setData(this)
        viewModel.refresh()
        viewModel.pagedListLiveData.observe(viewLifecycleOwner, { response ->
            previousPreviewAdapter.submitList(response)
        })
    }

    override fun onLoading() {
        DialogUtil.showDialog(requireContext())
    }

    override fun onLoadingFinish() {
        DialogUtil.dismissDialog()
    }


    private fun clickOnItem(item: Grnt) {
        showSnackbar("${item.grntSerial}")
    }

}