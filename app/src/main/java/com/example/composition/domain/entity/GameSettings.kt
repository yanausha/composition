package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightsAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable {

    val minCountOfRightsAnswersStr: String
        get() = minCountOfRightsAnswers.toString()
    val minPercentOfRightAnswersStr: String
        get() = minPercentOfRightAnswers.toString()
}