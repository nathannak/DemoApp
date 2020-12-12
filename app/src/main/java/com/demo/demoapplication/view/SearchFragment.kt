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
import com.demo.demoapplication.R
import com.demo.demoapplication.databinding.FragmentSearchBinding
import com.demo.demoapplication.util.CheckConnectivity
import com.demo.demoapplication.viewmodel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val searchFragmentViewModel : SearchFragmentViewModel by viewModels()

    private lateinit var fragmentSearchBinding :FragmentSearchBinding

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


    private fun setupObservers() {

        //TODO
        //searchFragmentViewModel.
    }

    private fun setupSearchView(){

        fragmentSearchBinding.searchView.apply {

            //Could not find this attribute in xml properties.
            isSubmitButtonEnabled=true

            //TODO use data binding
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

