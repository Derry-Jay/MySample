package com.my.sample.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.my.sample.R
import com.my.sample.databinding.SplashFragmentBinding
import com.my.sample.extensions.factorial
import com.my.sample.extensions.navCon
import com.my.sample.ui.MySplashOverride

class SplashFragment : MySplashOverride() {
    private var _binding: SplashFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val delayInS = getString(R.string.splash_screen_delay).toIntOrNull() ?: 3

        println(5.factorial)

        binding.textviewFirst.setOnClickListener {
            view.navCon.navigate(R.id.action_SplashFragment_to_LoginFragment)
        }

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                view.navCon.navigate(R.id.action_SplashFragment_to_LoginFragment)
            }, (delayInS * 1000).toLong())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}