package com.gurureddy.contactmanagerapp.sleepNapApp.sleepQuality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentSleepQualityBinding
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepDatabase

class SleepQualityFragment : Fragment() {
    private lateinit var binding: FragmentSleepQualityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val arguments = arguments?.let { SleepQualityFragmentArgs.fromBundle(it) }
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory =
            arguments?.let { SleepQualityViewModelFactory(it.sleepNight, dataSource) }
        val sleepQualityViewModel =
            viewModelFactory?.let { ViewModelProvider(this, it) }
                ?.get(SleepQualityViewModel::class.java)
        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel?.navigateToSleepTracker?.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment()
                sleepQualityViewModel.doneNavigating()
            }
        })
    }
}