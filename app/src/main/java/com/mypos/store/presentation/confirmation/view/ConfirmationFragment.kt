package com.mypos.store.presentation.confirmation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mypos.store.R
import com.mypos.store.databinding.FragmentConfirmationBinding
import com.mypos.store.presentation.confirmation.model.ConfirmationModel
import com.mypos.store.presentation.confirmation.viewmodel.ConfirmationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationFragment : BottomSheetDialogFragment() {

    private val viewModel: ConfirmationViewModel by viewModels()
    private var _binding: FragmentConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            viewModel.setEvent(ConfirmationModel.ConfirmationUiEvent.ConfirmOrder)
            Toast.makeText(requireContext(), "Ordered!", Toast.LENGTH_LONG).show()
            this.dismiss()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}