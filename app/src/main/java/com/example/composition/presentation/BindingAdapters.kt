package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

@BindingAdapter("requireAnswers")
fun bindRequireAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requirePercentage")
fun bindRequirePercentage(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percent
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) 0
    else ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
}

@BindingAdapter("imageViewResult")
fun bindSetImageView(imageView: ImageView, winner: Boolean) {
    if (winner) imageView.setImageResource(R.drawable.happy_brain)
    else imageView.setImageResource(R.drawable.sad_brain)
}

@BindingAdapter("percentAnswers")
fun bindPercentAnswers(progressBar: ProgressBar, percent: Int) {
    progressBar.setProgress(percent, true)
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, state: Boolean) {
    textView.setTextColor(setColorForAnswers(textView.context, state))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, state: Boolean) {
    val color = setColorForAnswers(progressBar.context, state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun setColorForAnswers(context: Context, state: Boolean): Int {
    val colorResId =
        if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberOfText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}