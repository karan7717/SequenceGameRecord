package com.example.sequencerecord


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.sequencerecord.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater,container,false)
        binding.start.setOnClickListener {
          it.findNavController().navigate(R.id.action_startFragment2_to_scoreFragment)

        }

        return binding.root
    }


    }
