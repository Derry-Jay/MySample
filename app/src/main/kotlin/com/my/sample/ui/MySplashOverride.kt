package com.my.sample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class MySplashOverride : MyFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}