package com.my.sample.ui

import android.widget.EditText
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import reactivecircus.flowbinding.android.widget.textChanges

class TextFormField(
    scope: CoroutineScope,
    private val editText: EditText,
    private val textInputLayout: TextInputLayout,
    private val validation: suspend (String?) -> String? = { null },
) : FormField<String>() {

    private var isEnabled: Boolean
        get() = textInputLayout.isEnabled
        set(value) {
            textInputLayout.isEnabled = value
        }

    private var isVisible: Boolean
        get() = textInputLayout.isVisible
        set(value) {
            textInputLayout.isVisible = value
        }

    var value: String?
        get() = stateInternal.value
        set(value) {
            println("-----------------")
            println(value)
            println("_________________")
            editText.setText(value)
            stateInternal.value = value
        }

    init {
        editText.textChanges().onEach { text ->
            clearError()
            stateInternal.update { text.toString() }
        }.launchIn(scope)
    }

    override fun clearError() {
        if (textInputLayout.error != null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }

    override fun clearFocus() {
        editText.clearFocus()
    }

    override fun disable() {
        isEnabled = false
    }

    override fun enable() {
        isEnabled = true
    }

    override suspend fun validate(focusIfError: Boolean): Boolean {
        if (!isVisible) {
            return true
        }
        val errorValue = try {
            validation(stateInternal.value)
        } catch (error: Throwable) {
            error.message
        }
        val result = errorValue == null
        if (result) {
            clearError()
        } else {
            textInputLayout.error = errorValue
            if (focusIfError) {
                editText.requestFocus()
            }
        }
        isValidInternal.update { result }
        return result
    }
}