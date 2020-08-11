package npl.domain

import arrow.core.Option

interface Result {
    val status: Status
    val exception: Option<Throwable>
}

data class KeyPhraseResult(
    override val status: Status,
    val keyPhrases: Option<Iterable<String>>,
    override val exception: Option<Throwable>
): Result

data class SentimentResult(
    override val status: Status,
    val sentiment: Option<String>,
    override val exception: Option<Throwable>
): Result

enum class Status {
    SUCCESS,
    FAILURE
}

