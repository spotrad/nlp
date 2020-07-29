package npl

import arrow.core.None
import arrow.core.Some
import arrow.core.getOrElse

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import comprehend.Client
import mu.KotlinLogging
import npl.domain.KeyPhraseResult
import npl.domain.Status

fun main(args: Array<String>) = Runner().main(args)

class Runner : CliktCommand() {
    private val text: String by option(help="The text to analyze key phrases").prompt("Your desired text")

    override fun run() {
        Application().run(text)
    }
}

class Application {
    private val logger = KotlinLogging.logger {}
    private val awsCreds = buildAwsCredentials()
    private val client = Client(awsCreds)

    fun run(text: String) {
        logger.info("Starting Comprehend application...")
        val result = runKeyPhraseDetection(text)
        when (result.status) {
            Status.SUCCESS -> logger.info("Key phrase processing results: ${result.keyPhrases.fold({""}, {it.joinToString(",\n")})}")
            Status.FAILURE -> logger.error("There has been an error fetching key phrases from text. Error: ${result.exception.getOrElse { "" }}")
        }
        logger.info("Comprehend application finished.")
    }

    private fun runKeyPhraseDetection(text: String): KeyPhraseResult =
        client.detectPhrases(text)
            .attempt()
            .unsafeRunSync()
            .fold(
                {KeyPhraseResult(Status.FAILURE, None, Some(it))},
                {KeyPhraseResult(Status.SUCCESS, Some(it.map { phrase -> phrase.toString() }), None) }
            )

    private fun buildAwsCredentials(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain.getInstance()
    }
}