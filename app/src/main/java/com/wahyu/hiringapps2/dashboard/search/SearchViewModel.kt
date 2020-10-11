package com.wahyu.hiringapps2.dashboard.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isSearchLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<SearchModel>>()

    private lateinit var service: SearchApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSearchService(service: SearchApiService) {
        this.service = service
    }

    fun callSearchApi(searchId: Int, search: String) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    when (searchId) {
                        0 -> service.getProfileJobSeekerByNameRequest(search)
                        1 -> service.getProfileJobSeekerBySkillRequest(search)
                        2 -> service.getProfileJobSeekerByLocationRequest(search)
                        3 -> service.getProfileJobSeekerByJobTitleRequest(search)
                        4 -> service.getProfileJobSeekerByStatusJobRequest(search)
                        else -> {}
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Job() + Dispatchers.Main) {
                        isSearchLiveData.value = false
                    }
                }
            }
            if (response is SearchResponse) {
                val list = response.data?.map {
                    SearchModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                        it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                } ?: listOf()
                listLiveData.value = list

                withContext(Job() + Dispatchers.Main) {
                    isSearchLiveData.value = response.success
                }
            }
            isLoadingLiveData.value = false
        }
    }

}