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
import com.demo.demoapplication.model.AcronymItem
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

        //setup adapter
        fragmentSearchBinding.acronymList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = acronymsListAdapter
        }

    }

    private fun setupObservers() {

        //TODO
        searchFragmentViewModel.acronymLiveData.observe(viewLifecycleOwner, Observer { acronym ->
            acronym?.let {
                fragmentSearchBinding.acronymList.visibility=View.VISIBLE
                fragmentSearchBinding.errorTextView.visibility=View.INVISIBLE
                fragmentSearchBinding.noResultsTextView.visibility=View.INVISIBLE

                acronymsListAdapter.updateDogList(acronym.get(0).lfs as ArrayList<Lf>)
            }
        })

        searchFragmentViewModel.errorLoading.observe(viewLifecycleOwner, Observer{ error ->
            error?.let {
                if (it) {
                    fragmentSearchBinding.errorTextView.visibility = View.VISIBLE
                    fragmentSearchBinding.acronymList.visibility = View.INVISIBLE
                    fragmentSearchBinding.noResultsTextView.visibility=View.INVISIBLE
                }
            }
        })

        searchFragmentViewModel.noResults.observe(viewLifecycleOwner, Observer{ noRes ->
            noRes?.let{
                if(it){
                    fragmentSearchBinding.noResultsTextView.visibility=View.VISIBLE
                    fragmentSearchBinding.acronymList.visibility=View.INVISIBLE
                    fragmentSearchBinding.errorTextView.visibility = View.INVISIBLE
                }
            }
        })

    }

    private fun setupSearchView(){

        fragmentSearchBinding.searchView.apply {

            //Could not find this attribute in xml properties.
            isSubmitButtonEnabled=true

            //onclick listeners should be implemented with Dstabinding
            //but since i am injecting viewmodel here and
            //it is is initlized by viewmodels()
            //it can not simply be used and initilized
            //in the xml layout
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    //check internet before trying to fetch data
                    if(!CheckConnectivity(context).isConnected()){
                        Toast.makeText(context,"Please connect to internet",Toast.LENGTH_LONG).show()
                    }else {

                        //ask view-model to ask repository for data over network
                        searchFragmentViewModel.getAcronymsFromRepository(query)
                    }
                    return false

                }})
            }
        }

    }