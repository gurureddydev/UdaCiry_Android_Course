package com.gurureddy.contactmanagerapp.guesstheword.fragments

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentGame2Binding
import com.gurureddy.contactmanagerapp.guesstheword.viewmodel.GameFragmentViewModel


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGame2Binding
    private lateinit var viewModel: GameFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game2, container, false)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.eventGameFinished()
            }
        })

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != GameFragmentViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
    }

    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0

        val action =
            GameFragmentDirections.actionGameDestinationToScoreDestination(
                currentScore
            )

        findNavController().navigate(action)
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}
