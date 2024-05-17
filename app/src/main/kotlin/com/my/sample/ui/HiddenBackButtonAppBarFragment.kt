package com.my.sample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class HiddenBackButtonAppBarFragment : MyFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ab = (requireActivity() as AppCompatActivity).supportActionBar
        ab?.setDisplayHomeAsUpEnabled(false)
        ab?.setDisplayShowHomeEnabled(false)
    }
}