package com.elhady.gesswordkotlin.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elhady.gesswordkotlin.R
import com.elhady.gesswordkotlin.databinding.FragmentGameBinding
import com.elhady.gesswordkotlin.model.MAX_NO_OF_WORDS
import com.elhady.gesswordkotlin.model.allWordsList
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

        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
    }

    /*
     * Checks the user's word, and updates the score accordingly.
     * Displays the next guess word.
     * After the last word, the user is shown a Dialog with the final score.
     */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    /*
    * Skips the current word without changing the score.
    */
    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }



    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
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
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }
}