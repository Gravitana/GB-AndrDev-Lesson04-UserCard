package ru.gravitana.gb_andrdev_lesson04_usercard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.checkbox.MaterialCheckBox
import ru.gravitana.gb_andrdev_lesson04_usercard.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val inputNameMaxLength = 30

    private var inputNameLength = 0
    private var inputPhoneLength = 0
    private var inputGender = ""
    private var noticeSwitchIsChecked = true
    private var noticeCheckBoxes = emptyArray<MaterialCheckBox>()
    private lateinit var saveButton: Button

    private fun checkSaveButtonEnabled() {
        val inputNameLengthIsCorrect = inputNameLength in 1..inputNameMaxLength
        val inputPhoneLengthIsCorrect = inputPhoneLength > 0
        val inputGenderIsCorrect = inputGender != ""
        val noticeSelectedIsCorrect = !noticeSwitchIsChecked || (noticeSwitchIsChecked && checkNoticeSelected())

        saveButton.isEnabled = inputNameLengthIsCorrect
                && inputPhoneLengthIsCorrect
                && inputGenderIsCorrect
                && noticeSelectedIsCorrect
    }

    private fun checkNoticeSelected(): Boolean {
        for (checkBox in noticeCheckBoxes) {
            if (checkBox.isChecked) {
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveButton = binding.saveButton

        noticeCheckBoxes += binding.noticeCheck1
        noticeCheckBoxes += binding.noticeCheck2

        binding.noticeSwitch.isChecked = noticeSwitchIsChecked
        binding.name.counterMaxLength = inputNameMaxLength


        binding.inputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                inputNameLength = binding.inputName.text?.length ?: 0
                checkSaveButtonEnabled()
            }
        })

        binding.inputPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                inputPhoneLength = binding.inputName.text?.length ?: 0
                checkSaveButtonEnabled()
            }
        })
        
        binding.gender.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                inputGender = text.toString()
            }
            checkSaveButtonEnabled()
        }

        binding.noticeSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.noticeCheck1.isEnabled = isChecked
            binding.noticeCheck2.isEnabled = isChecked
            noticeSwitchIsChecked = isChecked

            checkNoticeSelected()
            checkSaveButtonEnabled()
        }

        binding.noticeCheck1.setOnClickListener{
             checkSaveButtonEnabled()
        }

         binding.noticeCheck2.setOnClickListener{
             checkSaveButtonEnabled()
        }

        val progress = Random.nextInt(101)
        binding.scoresProgress.progress = progress
        binding.scoresValues.text = getString(R.string.scores_values, progress)

        binding.saveButton.setOnClickListener {
            val message = R.string.save_message
            Toast.makeText(
                this@MainActivity, message,
                Toast.LENGTH_SHORT
            ).show()
        }
        
    }
}