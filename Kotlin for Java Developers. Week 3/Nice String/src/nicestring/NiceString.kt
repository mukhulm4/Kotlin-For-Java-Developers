package nicestring

fun String.isNice(): Boolean {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')

    var notContainBuBaBe = true
    if (this.contains("bu") || this.contains("ba") || this.contains("be")) {
        notContainBuBaBe = false
    }

    var numVowels = 0
    for (char in this) {
        if (char in vowels) {
            numVowels += 1
        }
    }

    var doubleLetter = false
    for (i in 1 until this.length) {
        if (this[i] == this[i - 1]) {
            doubleLetter = true
            break
        }
    }

    var count = 0
    if (notContainBuBaBe) count += 1

    if (numVowels >= 3) {
        count += 1
    }

    if (doubleLetter) count += 1

    return count >= 2
}