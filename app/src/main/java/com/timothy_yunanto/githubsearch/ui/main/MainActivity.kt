package com.timothy_yunanto.githubsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.timothy_yunanto.githubsearch.adapter.UserAdapter
import com.timothy_yunanto.githubsearch.databinding.ActivityMainBinding
import com.timothy_yunanto.githubsearch.service.ApiResponse
import com.timothy_yunanto.githubsearch.service.model.response.User
import com.timothy_yunanto.githubsearch.viewmodel.UserViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private var data: List<User> = emptyList()
    private var onProcess: Boolean = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.application)
        )
            .get(UserViewModel::class.java)

        attachObserver()
        initView()

        viewModel.getListUsers()
    }

    private fun initView() {
        adapter = UserAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.hasFixedSize()

        adapter.setListener(object: UserAdapter.Listener {
            override fun onDataSetItemClick(view: View, item: User, position: Int) {
                TODO("Not yet implemented")
            }
        })

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == "") {
                    adapter.setDataset(data)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.toString().isNotEmpty()) {
                    val temp =
                        data.filter { it.login.uppercase().contains(query.toString().uppercase()) }
                    adapter.setDataset(temp)
                }
                return false
            }
        })
    }

    private fun attachObserver() {
        viewModel.isLoading.observe(this, Observer { it ->
            it?.let { this.onProcess = it; this.showLoadingDialog(it) }
        })

        viewModel.apiError.observe(this, Observer { it ->
            it?.let {

            }
        })

        viewModel.apiResponse.observe(this, Observer { it ->
            it?.let { this.processResponse(it) }
        })
    }

    private fun showLoadingDialog(show: Boolean) {
        if (show) {
            binding.progress.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
            binding.notFound.visibility = View.GONE
        } else {
            binding.progress.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
        }
    }

    private fun processResponse(res: ApiResponse) {
        data = res.data

        Log.e("DATA HEHE", res.toString())
        Log.e("DATA HEHE", "DISINI")

        if (data.isNullOrEmpty()) {
            binding.notFound.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        } else {
            binding.notFound.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE

            adapter.setDataset(data)
        }
    }
}
