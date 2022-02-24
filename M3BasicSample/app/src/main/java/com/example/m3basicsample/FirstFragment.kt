package com.example.m3basicsample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.m3basicsample.databinding.FragmentFirstBinding
import com.example.m3basicsample.models.User
import com.example.m3basicsample.viewmodels.FirstViewModel

internal class FirstFragment : Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<FirstViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToThirdFragment(
                    User("Yamada", 20)
                )
            )
        }
        viewModel.foo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}