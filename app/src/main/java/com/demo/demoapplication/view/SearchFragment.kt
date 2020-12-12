package com.demo.demoapplication.view

import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.demo.demoapplication.R
import com.demo.demoapplication.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var fragmentSearchBinding :FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        val view: View = fragmentSearchBinding.getRoot()

        fragmentSearchBinding.searchView.isSubmitButtonEnabled=true
        fragmentSearchBinding.searchView.queryHint="Acronym"
        fragmentSearchBinding.searchView.isIconifiedByDefault=false

        return view

//        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}