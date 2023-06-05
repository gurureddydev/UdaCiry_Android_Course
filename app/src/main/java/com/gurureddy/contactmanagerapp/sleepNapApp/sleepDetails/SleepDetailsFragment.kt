package com.gurureddy.contactmanagerapp.sleepNapApp.sleepDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentSleepDetailsBinding
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepDatabase


class SleepDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_details, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = arguments?.let { SleepDetailsFragmentArgs.fromBundle(it) }

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory =
            arguments?.sleepNight?.let { SleepDetailViewModelFactory(it, dataSource) }

        // Get a reference to the ViewModel associated with this fragment.
        val sleepDetailViewModel =
            viewModelFactory?.let {
                ViewModelProvider(
                    this, it
                )[SleepQualityDetailsViewModel::class.java]
            }

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.sleepDetailViewModel = sleepDetailViewModel

        binding.lifecycleOwner = this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sleepDetailViewModel?.navigateToSleepTracker?.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepDetailsFragmentDirections.actionSleepDetailsFragmentToSleepTrackerFragment()
                )
                sleepDetailViewModel.doneNavigation()
            }
        }

        return binding.root
    }
}

