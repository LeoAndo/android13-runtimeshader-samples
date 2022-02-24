package com.example.m3basicsample

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.m3basicsample.R
import com.example.m3basicsample.databinding.FragmentSecondBinding
import com.example.m3basicsample.extentions.hideKeyboard
import com.example.m3basicsample.viewmodels.SecondViewModel

internal class SecondFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<SecondViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)

        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            binding.button.isEnabled = !TextUtils.isEmpty(text)
            val isErrorEnabled = TextUtils.isEmpty(text)
            binding.editEmailLayout.isErrorEnabled = isErrorEnabled
            if (isErrorEnabled) {
                binding.editEmailLayout.error = "入力してください！！"
            }
        }
        binding.editEmail.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                requireActivity().hideKeyboard()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
        binding.button.setOnClickListener {
            Toast.makeText(requireContext(), "clicked!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}