package com.gurureddy.contactmanagerapp.guesstheword.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentTitle2Binding


class TitleFragment : Fragment() {
    private lateinit var binding:FragmentTitle2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_title2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playBtn.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleDestinationToGameDestination())
        }
    }

}