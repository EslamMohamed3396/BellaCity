package com.bellacity.ui.fragment.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.databinding.FragmentSplashBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.Constant
import com.bellacity.utilities.PreferencesUtils

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private var mAnimationLogo: Animation? = null
    private var runnable: Runnable? = null
    private var handler: Handler? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {

    }

    override fun onCreateInit() {
        checkLogin()
        hideNavBtn()
    }


    private fun animation() {
        mAnimationLogo =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_from_bottom)
        binding?.logo?.animation = mAnimationLogo

    }

    private fun checkLogin() {
        animation()
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (PreferencesUtils(requireContext()).getInstance()
                    ?.getBoolean(Constant.IS_USER_LOGIN)!!
            ) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

            }

        }
        handler?.postDelayed(runnable!!, 3000)
    }

    override fun onPause() {
        super.onPause()
        mAnimationLogo?.cancel()
        handler?.removeCallbacks(runnable!!)
    }
}