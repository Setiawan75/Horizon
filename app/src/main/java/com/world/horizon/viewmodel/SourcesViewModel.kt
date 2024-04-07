package com.world.horizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.world.horizon.model.Sources
import com.world.horizon.network.APIClient
import com.world.horizon.network.Resource
import com.world.horizon.shared.extensions.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SourcesViewModel: ViewModel() {
    private val _listSources = MutableLiveData<Resource<ArrayList<Sources>>>()
    val listSources: LiveData<Resource<ArrayList<Sources>>> = _listSources
    var loadingPage = false
    fun getListSources(path: String, page: String, search: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingPage = true
                try {
                    val response = APIClient.endpoint.getListSources(if (search.isNotEmpty()) Constants.API.CATEGORY+path+
                            Constants.API.SEARCH+search+
                            Constants.API.PAGE+page+Constants.API.API_KEY
                            else Constants.API.CATEGORY+path+
                            Constants.API.PAGE+page+Constants.API.API_KEY)

                    if (response.isSuccessful) {
                        _listSources.postValue(
                            Resource.Success(
                                response.body()?.sources ?: arrayListOf()
                            )
                        )
                        loadingPage = false
                    } else {
                        _listSources.postValue(Resource.Failure(response.message()))
                        loadingPage = false
                    }

                } catch (e: Exception) {
                    // Handle exceptions here if needed
                    _listSources.postValue(Resource.Failure(e.message ?: "An error occurred"))
                    loadingPage = false
                }
            }
        }
    }
}