package iu.c323.fall2024.practicum18

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import iu.c323.fall2024.practicum18.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import network.Flicker
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

private const val TAG = "PhotoGalleryFragment"
class PhotoGalleryFragment: Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = checkNotNull(_binding){
        "Cannot access binding because it is null. IS the view visible."
    }

    private val photoGalleryViewModel: PhotoGalleryViewModel by viewModels()

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
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.fragment_photo_gallery)
        val searchItem: MenuItem = binding.toolbar.menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "QueryTextSubmit: $query")
                photoGalleryViewModel.setQuery(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChanged: $newText")
                return false
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    photoGalleryViewModel.galleryItems.collect{ items ->
                        Log.d(TAG, "response received: $items")
                        binding.photoGrid.adapter = PhotoListAdapter(items)
                    }
                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener(){
            when(it.itemId){
                R.id.menu_item_clear ->{
                    photoGalleryViewModel.setQuery("")
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}