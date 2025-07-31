package com.example.crossclimb.domain.usecase

class GetHintUseCase {
    operator fun invoke(answer: String, revealedLetters: Int): String {
        return if (revealedLetters < answer.length) {
            answer.substring(0, revealedLetters + 1)
        } else {
            answer
        }
    }
}