package npl.domain

import arrow.core.Option

data class KeyPhraseResult(val status: Status, val keyPhrases: Option<Iterable<String>>, val exception: Option<Throwable>)

enum class Status {
    SUCCESS,
    FAILURE
}

