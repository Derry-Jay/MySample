package com.my.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.my.sample.R
import com.my.sample.data.pwdRegex
import com.my.sample.databinding.LoginFragmentBinding
import com.my.sample.extensions.isEmpty
import com.my.sample.extensions.navCon
//import com.my.sample.extensions.isValidEmail
import com.my.sample.extensions.showSnackBar
import com.my.sample.extensions.trimmed
import com.my.sample.ui.TextFormField
import com.my.sample.ui.HiddenBackButtonAppBarFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

class LoginFragment : HiddenBackButtonAppBarFragment() {
    private var _binding: LoginFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fieldPhone by lazy {
        TextFormField(
            scope = lifecycleScope,
            textInputLayout = binding.textviewFirst,
            editText = binding.editTextPhone2,
            validation = {
                when {
                    it?.trimmed.isEmpty -> "Phone Number is required."
                    it?.toIntOrNull() == null -> "Phone Number should contain only digits"
                    else -> null
                }
            }
        )
    }

//    private val fieldMail by lazy {
//        FormFieldText(
//            scope = lifecycleScope,
//            textInputLayout = binding.textviewFirst,
//            editText = binding.editTextPhone2,
//            validation = {
//                when {
//                    it?.trimmed.isEmpty -> "Email is required."
//                    !(it?.isValidEmail ?: false)-> "Enter a valid Email"
//                    else -> null
//                }
//            }
//        )
//    }

    private val fieldPassword by lazy {
        TextFormField(
            scope = lifecycleScope,
            textInputLayout = binding.text,
            editText = binding.editTextTextPassword,
            validation = {
                when {
                    it?.trimmed.isEmpty -> "Password cannot be empty"
                    !pwdRegex.matches(
                        it?.trimmed ?: ""
                    ) -> "Invalid Password"

                    else -> null
                }
            }
        )
    }

//    private val fields by lazy {
//        listOf(
//            fieldPhone,
////        fieldMail,
//            fieldPassword
//        )
//    }

    private fun onSubmit() =
        lifecycleScope.launch {
//            println(product.description)
//            println(Log.d("Data ", data.toString()))
//            println("Gps Catch Exception : $e")
            println("Hey")
            println(fieldPhone.value)
            println(fieldPassword.value)
            println("Hey")
            if (fieldPhone.validate() && fieldPassword.validate()) {
                view?.navCon?.navigate(R.id.action_LoginFragment_to_DashboardFragment)
            } else {
//                println(binding.editTextPhone2.text)
//                println(binding.editTextTextPassword.text)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = requireActivity() as AppCompatActivity
        act.supportActionBar?.setTitle(R.string.login)

        binding.fab.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    println("nl")
                }

                // Get new FCM registration token
                val token = task.result ?: ""
                println("Device Token: $token")

//                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
            it?.showSnackBar("Sign Up Module Unimplemented", Snackbar.LENGTH_LONG, "Action", null, null)
        }

        binding.login.clicks().onEach {
            onSubmit()
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}