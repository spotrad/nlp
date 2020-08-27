package nlp

import arrow.core.None
import arrow.core.Some
import arrow.core.getOrElse

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import comprehend.Client
import mu.KotlinLogging
import nlp.domain.KeyPhraseResult
import nlp.domain.SentimentResult
import nlp.domain.Status

fun main(args: Array<String>) = Runner().main(args)

class Runner : CliktCommand() {
    private val text: String by option(help="The text to analyze key phrases").prompt("Your desired text")
    private val type: AnalysisOptions by option("-t", "--type", help="The Comprehend analysis type to run")
        .choice(AnalysisOptions.PHRASEDETECTION.abbreviation, AnalysisOptions.SENTIMENT.abbreviation)
        .convert { AnalysisOptions.valueOf(it.toUpperCase()) }
        .required()

    override fun run() {
        Application().run(text, type)
    }
}

class Application {
    private val logger = KotlinLogging.logger {}
    private val awsCreds = buildAwsCredentials()
    private val client = Client(awsCreds)

    fun run(text: String, analysisOption: AnalysisOptions) {
        logger.info("Starting Comprehend application...")
        when(analysisOption) {
            AnalysisOptions.PHRASEDETECTION -> runKeyPhraseDetection(text)
            AnalysisOptions.SENTIMENT -> runSentimentDetection(text)
        }
        logger.info("Comprehend application finished.")
    }

    private fun runKeyPhraseDetection(text: String) {
        val result = submitPhraseDetection(text)
        when (result.status) {
            Status.SUCCESS -> logger.info("Key phrase processing results: ${result.keyPhrases.fold({""}, {it.joinToString(",\n")})}")
            Status.FAILURE -> logger.error("There has been an error fetching key phrases from text. Error: ${result.exception.getOrElse { "" }}")
        }
    }

    private fun runSentimentDetection(text: String) {
        val result = submitSentimentDetection(text)
        when (result.status) {
            Status.SUCCESS -> logger.info("Sentiment processing results: ${result.sentiment.fold({""}, {it})}")
            Status.FAILURE -> logger.error("There has been an error fetching key phrases from text. Error: ${result.exception.getOrElse { "" }}")
        }
    }

    private fun submitPhraseDetection(text: String): KeyPhraseResult =
        client.detectPhrases(text)
            .attempt()
            .unsafeRunSync()
            .fold(
                {KeyPhraseResult(Status.FAILURE, None, Some(it))},
                {KeyPhraseResult(Status.SUCCESS, Some(it.map { phrase -> phrase.toString() }), None) }
            )

    private fun submitSentimentDetection(text: String): SentimentResult =
        client.detectSentiment(text)
            .attempt()
            .unsafeRunSync()
            .fold(
                {SentimentResult(Status.FAILURE, None, Some(it))},
                {SentimentResult(Status.SUCCESS, Some(it.toString()), None)}
            )

    private fun buildAwsCredentials(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain.getInstance()
    }
}

enum class AnalysisOptions(val abbreviation: String){
    PHRASEDETECTION("phrase"),
    SENTIMENT("sentiment")
}