package npl

import arrow.core.Either

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.comprehend.model.KeyPhrase
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import comprehend.Client
import mu.KotlinLogging

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
        runKeyPhraseDetection(text).fold(
            {logger.error("There has been an error while processing text.", it)},
            {logger.info("Finished processing text. Results: ${it.joinToString()}")}
        )
        logger.info("Comprehend application finished.")
    }

    private fun runKeyPhraseDetection(text: String): Either<Throwable, Iterable<KeyPhrase>> =
        client.detectPhrases(text)
            .attempt()
            .unsafeRunSync()

    private fun buildAwsCredentials(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain.getInstance()
    }
}