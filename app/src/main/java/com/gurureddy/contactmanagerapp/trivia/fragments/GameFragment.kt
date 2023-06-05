package com.gurureddy.contactmanagerapp.trivia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    data class Question(val text: String, val answers: List<String>)

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries")
        ),
        Question(
            text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")
        ),
        Question(
            text = "Inflate layout in fragments?",
            answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")
        ),
        Question(
            text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Question(
            text = "Android vector format?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Question(
            text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionsIndex = 0
    private val numberQuestions = Math.min((questions.size + 1) / 2, 3)


    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.game = this

        initListener()
        randomizeQuestions()
    }

    private fun initListener() {
        binding.submitBtn.setOnClickListener {
            val checked = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checked) {
                var answerIndex = 0
                when (checked) {
                    R.id.second_answer_radio_btn -> answerIndex = 1
                    R.id.third_answer_radio_btn -> answerIndex = 2
                    R.id.fourth_answer_radio_btn -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionsIndex++
                    if (questionsIndex < numberQuestions) {
                        currentQuestion = questions[questionsIndex]
                        setQuestions()
                        binding.invalidateAll()
                    } else {
                        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numberQuestions,questionsIndex))
                    }
                } else {
                    findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }
    }

    private fun setQuestions() {
        currentQuestion = questions[questionsIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_android_trivia_question, questionsIndex + 1, numberQuestions)
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionsIndex = 0
        setQuestions()
    }
}