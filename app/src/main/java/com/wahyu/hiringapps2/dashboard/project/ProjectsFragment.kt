package com.wahyu.hiringapps2.dashboard.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.project.addProject.AddProjectActivity
import com.wahyu.hiringapps2.databinding.FragmentProjectsBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class ProjectsFragment : Fragment(), ProjectsContract.View {

    private lateinit var binding: FragmentProjectsBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreferencesUtil
    private  var presenter: ProjectsPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = ApiClient.getApiClient(this.requireContext())?.create(ProjectsApiService::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects, container, false)
        sharedPref = SharedPreferencesUtil(this.requireContext())

        binding.recycleView.adapter = ProjectsAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        presenter = ProjectsPresenter(coroutineScope, service)

        binding.btnAddProject.setOnClickListener { startActivity(Intent(this.requireContext(), AddProjectActivity::class.java)) }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
        presenter?.callProjectApi()
        presenter?.setSharedPref(sharedPref)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unBind()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        presenter = null
    }

    override fun addListProject(list: List<ProjectsModel>) {
        (binding.recycleView.adapter as ProjectsAdapter).addList(list)
    }

    override fun showProgressBar() { binding.progressBar.visibility = View.VISIBLE }

    override fun hideProgressBar() { binding.progressBar.visibility = View.GONE }
    override fun showNoProjectView() { binding.linearLayoutNoResult.visibility = View.VISIBLE }

    override fun hideNoProjectView() { binding.linearLayoutNoResult.visibility = View.GONE }
}