package com.wahyu.hiringapps2.dashboard.project

import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProjectsPresenter(private val coroutineScope: CoroutineScope, private val service: ProjectsApiService?) :
    ProjectsContract.Presenter {

    private var view: ProjectsContract.View? = null
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun bindToView(view: ProjectsContract.View) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    override fun callProjectApi() {
        coroutineScope.launch {
            view?.showProgressBar()
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getAllProjectData(sharedPref.getInt(Key.PREF_USER_ID))
                } catch (e: Throwable) {
                    when (e) {
                        is HttpException -> {
                            val isError = true
                            view?.showNoProjectView()
                        }
                        else -> e.printStackTrace()
                    }
                }
            }
            if (response is ProjectsResponse) {
                val list = response.data?.map {
                    ProjectsModel(it.id, it.userId, it.name, it.description, it.price, it.duration, it.projectImage)
                } ?: listOf()
                view?.addListProject(list)
                view?.hideNoProjectView()
            }
            view?.hideProgressBar()
        }
    }
}