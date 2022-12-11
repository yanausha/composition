package com.example.composition.presentation

import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel: ViewModel() {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    fun startGame(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
    }

}
