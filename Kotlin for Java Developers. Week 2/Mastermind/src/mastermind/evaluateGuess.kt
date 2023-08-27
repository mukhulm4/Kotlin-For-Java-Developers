package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val secretList = secret.toList()
    val guessList = guess.toList()

    var correctPosition = 0
    var incorrectPosition = 0

    var userSecret = ""
    var userGuess = ""

    for (i in secretList.indices) {
        if (guessList[i] == secretList[i]) {
            correctPosition += 1
        } else {
            userSecret += secretList[i]
            userGuess += guessList[i]
        }
    }

    for (char in 'A'..'F') {
        val secretCount = userSecret.count { it == char }
        val guessCount = userGuess.count { it == char }
        incorrectPosition += secretCount.coerceAtMost(guessCount)
    }

    return(Evaluation(correctPosition, incorrectPosition))
}
