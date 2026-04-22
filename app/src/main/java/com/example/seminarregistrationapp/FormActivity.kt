package com.example.seminarregistrationapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class FormActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerSeminar: Spinner
    private lateinit var cbAgreement: CheckBox
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        rgGender = findViewById(R.id.rgGender)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        cbAgreement = findViewById(R.id.cbAgreement)
        btnSubmit = findViewById(R.id.btnSubmit)

        setupSpinner()
        setupRealTimeValidation()

        btnSubmit.setOnClickListener {
            if (validateAll()) {
                showConfirmationDialog()
            } else {
                Toast.makeText(this, "Mohon lengkapi data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.seminar_list,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeminar.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        tilName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        tilEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        tilPhone.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhone(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateName(name: String): Boolean {
        return if (name.isEmpty()) {
            tilName.error = getString(R.string.error_empty)
            false
        } else {
            tilName.error = null
            true
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                tilEmail.error = getString(R.string.error_empty)
                false
            }
            !email.contains("@") -> {
                tilEmail.error = getString(R.string.error_email)
                false
            }
            else -> {
                tilEmail.error = null
                true
            }
        }
    }

    private fun validatePhone(phone: String): Boolean {
        return when {
            phone.isEmpty() -> {
                tilPhone.error = getString(R.string.error_empty)
                false
            }
            !phone.startsWith("08") || phone.length < 10 || phone.length > 13 -> {
                tilPhone.error = getString(R.string.error_phone)
                false
            }
            else -> {
                tilPhone.error = null
                true
            }
        }
    }

    private fun validateAll(): Boolean {
        val isNameValid = validateName(tilName.editText?.text.toString())
        val isEmailValid = validateEmail(tilEmail.editText?.text.toString())
        val isPhoneValid = validatePhone(tilPhone.editText?.text.toString())
        val isGenderSelected = rgGender.checkedRadioButtonId != -1
        val isAgreed = cbAgreement.isChecked

        if (!isGenderSelected) Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
        if (!isAgreed) Toast.makeText(this, "Anda harus menyetujui pernyataan", Toast.LENGTH_SHORT).show()

        return isNameValid && isEmailValid && isPhoneValid && isGenderSelected && isAgreed
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                navigateToResult()
            }
            .setNegativeButton(R.string.dialog_no, null)
            .show()
    }

    private fun navigateToResult() {
        val selectedGenderId = rgGender.checkedRadioButtonId
        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()
        val seminar = spinnerSeminar.selectedItem.toString()

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("EXTRA_NAME", tilName.editText?.text.toString())
            putExtra("EXTRA_EMAIL", tilEmail.editText?.text.toString())
            putExtra("EXTRA_PHONE", tilPhone.editText?.text.toString())
            putExtra("EXTRA_GENDER", gender)
            putExtra("EXTRA_SEMINAR", seminar)
        }
        startActivity(intent)
        finish()
    }
}
