package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()
    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val textViewOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClickListenersToOptions()
    }

    private fun observeViewModel() {
        viewModel.formattedTimer.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until textViewOptions.size)
                textViewOptions[i].text = it.options[i].toString()
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        viewModel.enoughCountOfAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(setColorForAnswers(it))
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.enoughPercentOfAnswers.observe(viewLifecycleOwner) {
            val color = setColorForAnswers(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun setOnClickListenersToOptions() {
        for (tvOption in textViewOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswers(tvOption.text.toString().toInt())
            }
        }
    }

    private fun setColorForAnswers(state: Boolean): Int {
        val colorResId =
            if (state) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }
}