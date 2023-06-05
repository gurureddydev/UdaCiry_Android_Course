package com.gurureddy.contactmanagerapp.guesstheword.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentScoreBinding
import com.gurureddy.contactmanagerapp.guesstheword.viewmodel.ScoreViewModel
import com.gurureddy.contactmanagerapp.guesstheword.viewmodel.ScoreViewModelFactory

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        viewModelFactory = arguments?.let { ScoreFragmentArgs.fromBundle(it).score }
            ?.let { ScoreViewModelFactory(it) }!!
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { event ->
            if (event) {
                viewModel.onPlayAgain()
                viewModel.onPlayAgainComplete()
            }
        })

        binding.tryAgainBtn.setOnClickListener { onPlayAgain() }
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionScoreDestinationToGameDestination())
    }
}