package com.example.m3basicsample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.m3basicsample.databinding.FragmentThirdBinding
import com.example.m3basicsample.viewmodels.ThirdViewModel

internal class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args by navArgs<ThirdFragmentArgs>()
    private val viewModel by viewModels<ThirdViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentThirdBinding.bind(view)

        args.user?.let {
            binding.textview.text = it.name + " : " + it.age
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}