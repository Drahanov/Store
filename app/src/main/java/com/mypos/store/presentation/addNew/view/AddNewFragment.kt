package com.mypos.store.presentation.addNew.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mypos.store.databinding.FragmentAddNewBinding
import com.mypos.store.presentation.addNew.model.AddNewModel
import com.mypos.store.presentation.addNew.viewmodel.AddNewViewModel
import com.mypos.store.presentation.base.viewmodel.observeIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream


@AndroidEntryPoint
class AddNewFragment : BottomSheetDialogFragment() {

    private val viewModel: AddNewViewModel by viewModels()
    private var _binding: FragmentAddNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        binding.confirmButton.setOnClickListener {
            val title = binding.title.editText
            val price = binding.price.editText
            val shortDescription = binding.shortDescription.editText
            val fullDescription = binding.fullDescription.editText

            if (title?.text.isNullOrEmpty() || price?.text.isNullOrEmpty() || shortDescription?.text.isNullOrEmpty() || fullDescription?.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Fields cant be empty", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    viewModel.setEvent(
                        AddNewModel.AddNewUiEvent.AddNew(
                            title = binding.title.editText?.text.toString(),
                            price = binding.price.editText?.text.toString().toDouble(),
                            shortDescription = binding.shortDescription.editText?.text.toString(),
                            fullDescription = binding.fullDescription.editText?.text.toString()
                        )
                    )
                    Toast.makeText(requireContext(), "Successfully saved", Toast.LENGTH_SHORT).show()

                    clearFields()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }

        binding.loadImageButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.setType("image/*")
            startActivityForResult(photoPickerIntent, 200)
        }
        return view
    }

    private fun onHandleState(state: AddNewModel.AddNewUiState) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri = data?.data
                val imageStream: InputStream? =
                    imageUri?.let { requireActivity().contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val stream = ByteArrayOutputStream()
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                val byteArray = stream.toByteArray()
                val size = byteArray.size

                // if image size bigger than 1mb
                if (size > 1000000) {
                    binding.loadImageButton.text = "Image too big"
                } else {
                    viewModel.setEvent(AddNewModel.AddNewUiEvent.ImageLoaded(byteArray))
                    binding.loadImageButton.text = "Image loaded"
                }


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.title.editText?.text?.clear()
        binding.price.editText?.text?.clear()
        binding.fullDescription.editText?.text?.clear()
        binding.shortDescription.editText?.text?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}