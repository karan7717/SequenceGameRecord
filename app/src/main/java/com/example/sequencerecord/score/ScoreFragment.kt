package com.example.sequencerecord.score


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sequencerecord.R
import com.example.sequencerecord.Repository.ScoreRepository
import java.time.LocalDate
import com.example.sequencerecord.databinding.FragmentScoreBinding
import com.example.sequencerecord.db.ScoreEntry
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ScoreFragment : Fragment(), RecyclerAdapter.RowClickListener {
    @Inject
    lateinit var scoreFragmentViewModel: ScoreFragmentViewModel
    private lateinit var binding: FragmentScoreBinding


    lateinit var recyclerAdapter: RecyclerAdapter



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        binding.recyclerView.apply {
            recyclerAdapter = RecyclerAdapter(this@ScoreFragment)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }



        activity?.let {
//
            scoreFragmentViewModel.allScoreLiveData.observe(viewLifecycleOwner, Observer {
                ArrayList(it)?.let { it1 -> recyclerAdapter.submitList(it1)}





            })
            binding.scoreViewModel = scoreFragmentViewModel
            binding.lifecycleOwner = this
        }
        editTextWatcher()
        submitButtonClick()
        scoreFragmentViewModel.getScoreAndResult()
        observeScoreEntryLiveData()

        scoreFragmentViewModel.getAllScores()

        return binding.root
    }

    override fun onDeleteScoreClickListener(score: ScoreEntry?) {
        if (score != null) {
            scoreFragmentViewModel.deleteScoreInfo(score)
        }
        scoreFragmentViewModel.getScoreAndResult()
    }

    override fun onEditClickListener(score: ScoreEntry?) {

        binding.enterScoreGreen.setText(score?.green.toString())
        binding.enterScoreBlue.setText(score?.blue.toString())
        binding.enterScoreGreen.setTag(binding.enterScoreGreen.id, score?.id)
        binding.submitButton.setText("UPDATE")
        if (score?.type.equals("J")) {
            binding.typeJNonJ.setSelection(0)
        } else {
            binding.typeJNonJ.setSelection(1)
        }

    }

    fun editTextWatcher() {
        var greenEditText = binding.enterScoreGreen
        var blueEditText = binding.enterScoreBlue
        var editTexts = listOf(greenEditText, blueEditText)
        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var green = greenEditText.text.toString().trim()
                    var blue = blueEditText.text.toString().trim()
                    binding.submitButton.isEnabled = green.isNotEmpty() && blue.isNotEmpty()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitButtonClick() {

        binding.submitButton.setOnClickListener {

            var green = binding.enterScoreGreen.text.toString().toInt()
            var blue = binding.enterScoreBlue.text.toString().toInt()
            var type = binding.typeJNonJ
            var date = LocalDate.now().toString()

            var test: String = type.getSelectedItem().toString()
            if (binding.submitButton.text.equals("SUBMIT")) {
                println("First if")
                val score = ScoreEntry(0, green, blue, test, date)
                scoreFragmentViewModel.insertScoreInfo(score)
                scoreFragmentViewModel.addScore(green, blue, test)
                binding.enterScoreGreen.setText("")
                binding.enterScoreBlue.setText("")
                binding.typeJNonJ.setSelection(0)

            } else {
                val id: Int =
                    binding.enterScoreGreen.getTag(binding.enterScoreGreen.id).toString().toInt()

                scoreFragmentViewModel.getScoreViaId(id)
            }
            scoreFragmentViewModel.getScoreAndResult()
            it.hideKeyboard()
        }
    }


    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun observeScoreEntryLiveData() {


        scoreFragmentViewModel.scoreEntryLiveData.observe(viewLifecycleOwner, Observer {
            var green = if (binding.enterScoreGreen.text.toString().isEmpty()) 0 else {
                binding.enterScoreGreen.text.toString().toInt()
            }
            var blue = if (binding.enterScoreBlue.text.toString().isEmpty()) 0 else {
                binding.enterScoreBlue.text.toString().toInt()
            }
            var type = binding.typeJNonJ
            var date = LocalDate.now().toString()

            var test: String = type.getSelectedItem().toString()

            val prevGreen = it?.green
            val prevBlue = it?.blue
            val prevType = it?.type
            if (prevGreen != null) {
                if (prevGreen != green || prevBlue != blue || !prevType.equals(test)) {
                    try {
                        val id: Int =
                            binding.enterScoreGreen.getTag(binding.enterScoreGreen.id).toString()
                                .toInt()
                        scoreFragmentViewModel.deleteScore(prevGreen, prevBlue!!, prevType!!)
                        val score = ScoreEntry(id, green, blue, test, date)
                        scoreFragmentViewModel.updateScoreInfo(score)
                        scoreFragmentViewModel.addScore(green, blue, test)
                    } catch (e: NullPointerException) {
                        println("No id found")
                    }

                }
                binding.submitButton.setText("SUBMIT")
            } else {
                val score = ScoreEntry(0, green, blue, test, date)
                if (score.green != 0 && score.blue != 0) {
                    scoreFragmentViewModel.insertScoreInfo(score)
                    binding.submitButton.setText("SUBMIT")
                    scoreFragmentViewModel.addScore(green, blue, test)
                }
            }
            binding.enterScoreGreen.setText("")
            binding.enterScoreBlue.setText("")
            binding.typeJNonJ.setSelection(0)
            scoreFragmentViewModel.getScoreAndResult()


        })
    }

}


