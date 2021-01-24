package com.elhady.gesswordkotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elhady.gesswordkotlin.R
import com.elhady.gesswordkotlin.databinding.FragmentGameBinding
import com.elhady.gesswordkotlin.model.MAX_NO_OF_WORDS
import com.elhady.gesswordkotlin.model.SCORE_INCREASE
import com.elhady.gesswordkotlin.model.wordsList
import com.elhady.gesswordkotlin.viewmodel.GameViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by islam elhady on 24-Jan-21.
 */
class GameFragment : Fragment() {


    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        updateNextWordOnScreen()
        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(
            R.string.word_count, 0, MAX_NO_OF_WORDS
        )
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
//        currentGuessWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        binding.score.text = getString(R.string.score, score)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
//        currentGuessWord = getNextScrambledWord()
//        currentWordCount++
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
    }

    private fun onSubmitWord() {
        if (viewModel.nextWord()) {
            updateNextWordOnScreen()
        } else {
            showFinalScoreDialog()
        }
    }

    /*
     * Gets a random word for the list of words and shuffles the letters in it.
     */
//    private fun getNextScrambledWord(): String {
//        val tempWord = wordsList.random().toCharArray()
//        tempWord.shuffle()
//        return String(tempWord)
//    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next guess word on screen.
     */
    private fun updateNextWordOnScreen() {
//        binding.textViewGuessWord.text = currentGuessWord
        binding.textViewGuessWord.text = viewModel.currentGuessWord
    }

    /**
     * Creates and shows an AlertDialog with the final score.
     *
     *  This syntax may be new to you, but this is shorthand for
     *  setNegativeButton(getString(R.string.exit), { _, _ -> exitGame()})
     *  where the setNegativeButton() method takes in two parameters: a String and a function,
     *  DialogInterface.OnClickListener() which can be expressed as a lambda.
     *  When the last argument being passed in is a function,
     *  you could place the lambda expression outside the parentheses.
     *  This is known as trailing lambda syntax. Both ways of writing
     *  the code (with the lambda inside or outside the parentheses)
     *  is acceptable. The same applies for the setPositiveButton function.
     */
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
    }
}