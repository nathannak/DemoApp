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
import com.demo.demoapplication.model.Lf
import com.demo.demoapplication.util.CheckConnectivity
import com.demo.demoapplication.viewmodel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val searchFragmentViewModel : SearchFragmentViewModel by viewModels()
    private lateinit var fragmentSearchBinding :FragmentSearchBinding
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

        //setup adapter here
        fragmentSearchBinding.acronymList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = acronymsListAdapter
        }

    }

    private fun setupObservers() {

        searchFragmentViewModel.acronymLiveData.observe(viewLifecycleOwner, Observer { acronym ->
            acronym?.let {
                fragmentSearchBinding.apply {
                    acronymList.visibility=View.VISIBLE
                    errorTextView.visibility=View.INVISIBLE
                    noResultsTextView.visibility=View.INVISIBLE
                    progressBar.visibility=View.INVISIBLE
                    magnifierImageView.visibility=View.INVISIBLE
                }

                acronymsListAdapter.updateAcronymList(acronym.get(0).lfs as ArrayList<Lf>)
            }
        })

        searchFragmentViewModel.errorLoading.observe(viewLifecycleOwner, Observer{ error ->
            error?.let {
                if (it) {
                    fragmentSearchBinding.apply {
                        errorTextView.visibility = View.VISIBLE
                        acronymList.visibility = View.INVISIBLE
                        noResultsTextView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                        magnifierImageView.visibility=View.INVISIBLE
                    }
                }
            }
        })

        searchFragmentViewModel.noResults.observe(viewLifecycleOwner, Observer{ noRes ->
            noRes?.let{
                if(it) {
                    fragmentSearchBinding.apply {
                        noResultsTextView.visibility = View.VISIBLE
                        acronymList.visibility = View.INVISIBLE
                        errorTextView.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                        magnifierImageView.visibility=View.INVISIBLE

                    }
                }
            }
        })

        searchFragmentViewModel.isLoading.observe(viewLifecycleOwner, Observer{ isLoading ->
            isLoading?.let{
                if(it) {
                    fragmentSearchBinding.apply {
                        progressBar.visibility = View.VISIBLE
                        noResultsTextView.visibility = View.INVISIBLE
                        acronymList.visibility = View.INVISIBLE
                        errorTextView.visibility = View.INVISIBLE
                        magnifierImageView.visibility=View.INVISIBLE

                    }
                }
            }
        })

    }

    private fun setupSearchView(){

        fragmentSearchBinding.searchView.apply {

            //Could not find this attribute in xml properties.
            isSubmitButtonEnabled=true

            /*
            onclicklisteners should be implemented using Databinding
            however, since i am injecting viewmodel here, and
            viewmodel is initialized by viewmodels()
            it can not simply be initialized in the xml layout
            */

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {

                    //clear everything, in case user deletes the query
                    if(newText.length==0){

                        fragmentSearchBinding.apply {
                            magnifierImageView.visibility=View.VISIBLE
                            errorTextView.visibility=View.INVISIBLE
                            acronymList.visibility=View.INVISIBLE
                            noResultsTextView.visibility=View.INVISIBLE
                        }
                    }

                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    //check internet before trying to fetch data
                    if(!CheckConnectivity(context).isConnected()){
                        Toast.makeText(context,"Please connect to internet",Toast.LENGTH_SHORT).show()
                    }else {

                        //ask view-model to ask repository for data over network
                        searchFragmentViewModel.getAcronymsFromRepository(query)
                    }
                    return false

                }})
            }


        }

    }