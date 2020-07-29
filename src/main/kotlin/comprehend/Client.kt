package comprehend

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesRequest
import com.amazonaws.services.comprehend.model.KeyPhrase

class Client(credentials: AWSCredentialsProvider) {

    private val comprehendClient = AmazonComprehendClientBuilder
        .standard()
        .withCredentials(credentials)
        .withRegion(AWSRegion.OREGON.abbreviation)
        .build()

    fun detectPhrases(
        text: String, language: SupportedLanguages=SupportedLanguages.ENGLISH
    ): IO<Iterable<KeyPhrase>> {
        val request = DetectKeyPhrasesRequest()
            .withText(text)
            .withLanguageCode(language.abbreviation)
        return IO.fx {
            comprehendClient.detectKeyPhrases(request).keyPhrases
        }
    }
}

enum class SupportedLanguages(val abbreviation: String) {
    ENGLISH("en")
}

enum class AWSRegion(val abbreviation: String) {
    OREGON("us-west-2")
}