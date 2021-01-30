package com.demo.demoapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.demoapplication.R
import com.demo.demoapplication.databinding.FragmentSearchBinding
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.model.Lf
import com.demo.demoapplication.repository.Status
import com.demo.demoapplication.room.AcronymEntity
import com.demo.demoapplication.util.CheckConnectivity
import com.demo.demoapplication.viewmodel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
Search Fragment, the only fragment in the app which is part of navigation graph and Main Activity

Written by W on 12/13/20
*/

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val searchFragmentViewModel: SearchFragmentViewModel by viewModels()
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    private val acronymsListAdapter = AcronymListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        val view: View = fragmentSearchBinding.getRoot()

        setupSearchView()
        setupObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup RecyclerView adapter
        fragmentSearchBinding.acronymList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = acronymsListAdapter
        }

    }

    private fun setupObservers() {
        searchFragmentViewModel.acronymLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    loadingPage()
                }
                Status.ERROR -> {
                    errorPage()
                }
                else -> {
                    resource.data?.let {
                        if (it.isEmpty())
                            emptyResultPage()
                        else
                            dataPage(it)
                    } ?: run {
                        emptyResultPage()
                    }
                }
            }
        })
    }

    private fun loadingPage() {
        fragmentSearchBinding.apply {
            progressBar.visibility = View.VISIBLE
            noResultsTextView.visibility = View.INVISIBLE
            acronymList.visibility = View.INVISIBLE
            errorTextView.visibility = View.INVISIBLE
            magnifierImageView.visibility = View.INVISIBLE

        }
    }

    private fun errorPage() {
        fragmentSearchBinding.apply {
            errorTextView.visibility = View.VISIBLE
            acronymList.visibility = View.INVISIBLE
            noResultsTextView.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            magnifierImageView.visibility = View.INVISIBLE
        }
    }

    private fun dataPage(acronym: List<AcronymEntity>) {
        fragmentSearchBinding.apply {
            acronymList.visibility = View.VISIBLE
            errorTextView.visibility = View.INVISIBLE
            noResultsTextView.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            magnifierImageView.visibility = View.INVISIBLE
        }

        acronymsListAdapter.updateAcronymList(acronym.first().acronymLongFormat[0].lfs as ArrayList<Lf>)
    }

    private fun emptyResultPage() {
        fragmentSearchBinding.apply {
            noResultsTextView.visibility = View.VISIBLE
            acronymList.visibility = View.INVISIBLE
            errorTextView.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            magnifierImageView.visibility = View.INVISIBLE

        }
    }

    private fun setupSearchView() {

        fragmentSearchBinding.searchView.apply {

            //Could not find this attribute in xml properties.
            isSubmitButtonEnabled = true

            /*
            OnClickListeners should be implemented using Databinding.
            However, since i am injecting ViewmMdel here, and
            ViewModel is initialized using 'by viewModels()'
            it can not simply be initialized in the xml layout
            */

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {

                    //Clear everything, when user deletes the query
                    if (newText.length == 0) {

                        fragmentSearchBinding.apply {
                            magnifierImageView.visibility = View.VISIBLE
                            errorTextView.visibility = View.INVISIBLE
                            acronymList.visibility = View.INVISIBLE
                            noResultsTextView.visibility = View.INVISIBLE
                            progressBar.visibility=View.INVISIBLE
                        }
                    }

                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    //Check internet connection before trying to fetch data
                    if (!CheckConnectivity(context).isConnected()) {
                        Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                        //Ask ViewModel to ask repository for data over network
                        searchFragmentViewModel.getAcronymsFromRepository(query)
                    }
                    return false

                }
            })
        }

    }

}