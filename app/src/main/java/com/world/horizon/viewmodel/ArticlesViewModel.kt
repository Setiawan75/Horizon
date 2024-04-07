package com.world.horizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.world.horizon.model.Article
import com.world.horizon.network.APIClient
import com.world.horizon.network.Resource
import com.world.horizon.shared.extensions.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesViewModel: ViewModel() {
    private val _listArticles = MutableLiveData<Resource<ArrayList<Article>>>()
    val listArticles: LiveData<Resource<ArrayList<Article>>> = _listArticles
    var loadingPage = false
    fun getListArticles(path: String, page: String, search: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingPage = true
                val clearPath = path.replace("https://", "").replace("www.", "")
                try {
                    val response = APIClient.endpoint.getListArticles(if (search.isNotEmpty()) Constants.API.DOMAIN+clearPath+
                            Constants.API.SEARCH+search+
                            Constants.API.PAGE+page+ Constants.API.API_KEY
                    else Constants.API.DOMAIN+clearPath+
                            Constants.API.PAGE+page+ Constants.API.API_KEY)

                    if (response.isSuccessful) {
                        _listArticles.postValue(
                            Resource.Success(
                                response.body()?.articles ?: arrayListOf()
                            )
                        )
                        loadingPage = false
                    } else {
                        _listArticles.postValue(Resource.Failure(response.message()))
                        loadingPage = false
                    }

                } catch (e: Exception) {
                    // Handle exceptions here if needed
                    _listArticles.postValue(Resource.Failure(e.message ?: "An error occurred"))
                    loadingPage = false
                }
            }
        }
    }
}