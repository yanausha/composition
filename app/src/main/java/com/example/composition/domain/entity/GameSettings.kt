package com.example.composition.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightsAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)