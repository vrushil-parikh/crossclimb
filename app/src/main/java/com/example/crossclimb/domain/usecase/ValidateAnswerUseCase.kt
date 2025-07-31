package com.example.crossclimb.domain.usecase

class ValidateAnswerUseCase {
    operator fun invoke(userInput: String, correctAnswer: String): Boolean {
        return userInput.trim().equals(correctAnswer.trim(), ignoreCase = true)
    }
}