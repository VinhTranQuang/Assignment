package com.base.ui.component.breeds

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.base.R
import com.base.data.dto.breed.BreedItem
import com.base.data.dto.breed.Image
import com.base.data.local.LocalDataSource
import com.base.data.remote.BreedRepository
import com.base.ui.base.BaseViewModel
import com.base.utils.ImageUtils
import com.base.utils.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject
constructor(
    private val repository: BreedRepository,
    @ApplicationContext private val context: Context,
    private val networkConnectivity: NetworkConnectivity,
    private val localDataSource: LocalDataSource,
) : BaseViewModel() {

    val breedsLiveData: MutableLiveData<PagingData<BreedItem>> =
        MutableLiveData<PagingData<BreedItem>>()
    val breedsSearchLiveData: MutableLiveData<PagingData<BreedItem>> =
        MutableLiveData<PagingData<BreedItem>>()
    private val apiKey = "live_1UQvDFMdqIy9HimfEoT4IqcMXg66tqbmjEJKiYecmPuZEyiubjlh0PjI1vIGubR2"
    private var page = 0
    private var limit = 15

    suspend fun getBreeds() {
        viewModelScope.launch {
            repository.requestBreeds(page, limit, apiKey).cachedIn(viewModelScope)
                .catch {
                }.collect {
                    breedsLiveData.value = it
                }
        }
    }

    suspend fun searchBreeds(id: String) {
        viewModelScope.launch {
            if (networkConnectivity.isConnected()) {
                repository.requestSearchBreeds(id, page, limit, apiKey)
                    .debounce(200)
                    .catch {
                    }
                    .collect { breedItem ->
                        var itemBreed: BreedItem = breedItem
                        val referenceId = itemBreed.referenceId
                        repository.requestGetImage(referenceId, page, limit, apiKey)
                            .catch {
                            }
                            .collect {
                                val imageItem: Image = it
                                itemBreed.image.url = imageItem.url
                                val result: PagingData<BreedItem> = PagingData.empty()
//                                result.
                                breedsSearchLiveData.value = result
                            }
                    }
            } else {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = {
                        localDataSource.getBreedFromLocalWithId(id)
                    }
                ).flow.collect {
                    breedsSearchLiveData.value = it
                }

            }
        }
    }

    fun downloadBreed(itemBreed: BreedItem) {
        if (networkConnectivity.isConnected()) {
            viewModelScope.launch(Dispatchers.IO) {
                // convert url to bitmap and scale bitmap
                val bitmap = ImageUtils.getBitmap(itemBreed.image.url)
                // save bitmap to folder
                val imagePath = bitmap?.let {
                    ImageUtils.createDirectoryAndSaveFile(context, it, itemBreed.referenceId)
                }
                itemBreed.image.url = imagePath.toString()
                repository.saveBreedToLocal(itemBreed)
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, context.getString(R.string.download_success), Toast.LENGTH_LONG).show()
                }
            }
        } else{
            viewModelScope.launch(Dispatchers.Main) {
                Toast.makeText(context, context.getString(R.string.connection_on), Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
