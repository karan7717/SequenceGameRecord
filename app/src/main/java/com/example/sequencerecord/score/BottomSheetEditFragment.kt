package com.example.sequencerecord.score

import android.app.Application
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sequencerecord.R
import com.example.sequencerecord.databinding.FragmentBottomSheetEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val BLUE_BOTTOMSHEET = "blueBottomSheet"
private const val GREEN_BOTTOMSHEET  = "greenBottomSheet"
private const val TYPE_BOTTOM_SHEET = "typeBottomSheet"
class BottomSheetEditFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSheetEditBinding
    val scoreSharedPref = Application().getSharedPreferences(
        "score_pref", Context.MODE_PRIVATE
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_bottom_sheet_edit, container, false)
        binding.blueBottomSheet.setText(scoreSharedPref.getInt(BLUE_BOTTOMSHEET,0))
        binding.greenBottomSheet.setText(scoreSharedPref.getInt(GREEN_BOTTOMSHEET,0))
        if (scoreSharedPref.getString(TYPE_BOTTOM_SHEET,"J").equals("J")){
            binding.bottomSheetSpinner.setSelection(0)
        }else{
            binding.bottomSheetSpinner.setSelection(1)
            val test = scoreSharedPref.getInt(GREEN_BOTTOMSHEET,0)
            println(test)
        }


        return binding.root
    }



    }
