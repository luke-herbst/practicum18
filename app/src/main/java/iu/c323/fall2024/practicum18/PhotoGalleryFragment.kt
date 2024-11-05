package iu.c323.fall2024.practicum18

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import iu.c323.fall2024.practicum18.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch
import network.Flicker
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

const val TAG = "PhotoGalleryFragment"
class PhotoGalleryFragment: Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = checkNotNull(_binding){
        "Cannot access binding because it is null. IS the view visible."
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = PhotoRepository().fetchPhotos()
                Log.d(TAG, "response received: $response")
            }
            catch(exception: Exception){
                Log.e(TAG, "Failed to fetch gallery items", exception)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}