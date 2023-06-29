package com.mypos.store.presentation.addNew.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mypos.store.R
import com.mypos.store.databinding.FragmentAddNewBinding
import com.mypos.store.presentation.addNew.model.AddNewModel
import com.mypos.store.presentation.addNew.viewmodel.AddNewViewModel
import com.mypos.store.presentation.base.viewmodel.observeIn
import com.mypos.store.presentation.refactor.viewmodel.RefactoredHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream


@AndroidEntryPoint
class AddNewFragment : BottomSheetDialogFragment() {

    private val viewModel: AddNewViewModel by viewModels()
    private val sharedViewModel: RefactoredHomeViewModel by activityViewModels()

    private var _binding: FragmentAddNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewBinding.inflate(inflater, container, false)
        val view = binding.root

        val id = arguments?.getInt("productIdEdit")
        if (id != null) {
            viewModel.setEvent(AddNewModel.AddNewUiEvent.StartEditMode(id))
        }

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)


        viewModel.sideEffect
            .onEach(::onHandleEffect)
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
                    binding.price.editText?.setError(null)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.price.editText?.setError("Invalid format")
                }

            }

        }

        binding.loadImageButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.setType("image/*")
            startActivityForResult(photoPickerIntent, 200)
        }

        binding.image.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                val image = state.image
                if (image == null) {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(200.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(200.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
        return view
    }

    private fun saved() {
        Toast.makeText(requireContext(), "Successfully saved", Toast.LENGTH_SHORT)
            .show()

        clearFields()
    }

    private fun onHandleEffect(effect: AddNewModel.AddNewUiSideEffect) {
        when (effect) {
            AddNewModel.AddNewUiSideEffect.Saved -> {
                saved()
            }
        }
    }

    private fun onHandleState(state: AddNewModel.AddNewUiState) {
        if (state.isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }

        if (state.isEditMode && state.article != null) {
            binding.title.editText?.setText(state.article.name)
            binding.price.editText?.setText(
                state.article.price.toString(),
            )
            binding.fullDescription.editText?.setText(state.article.fullDescription)
            binding.shortDescription.editText?.setText(state.article.shortDescription)
        }
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
                viewModel.setEvent(AddNewModel.AddNewUiEvent.ImageLoaded(selectedImage))
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