package com.wahyu.hiringapps2.dashboard.project

interface ProjectsContract {

    interface View {

        fun addListProject(list: List<ProjectsModel>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {

        fun bindToView(view: View)
        fun unBind()
        fun callProjectApi()
    }
}