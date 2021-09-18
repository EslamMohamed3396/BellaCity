package com.bellacity.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bellacity.ui.activity.mainActivity.MainActivity
import com.bellacity.ui.fragment.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {
    protected lateinit var binding: VDB
    protected val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VDB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateInit()
        initClicks()
        initViewModel()

    }

    protected abstract fun initClicks()

    protected abstract fun initViewModel()

    protected abstract fun onCreateInit()

    fun showSnackbar(message: String?) {
        Timber.d(message)
        val snackBar = Snackbar.make(binding.root, message!!, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    protected fun showNavBtn() {
        (activity as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE
    }

    protected fun hideNavBtn() {
        (activity as MainActivity).binding.bottomNavigation.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        binding.unbind()
    }
}