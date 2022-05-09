package me.thunderbiscuit.krepl

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.TermUi.echo
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import kotlin.system.exitProcess

fun main() {
    while (true) {
        print("K-REPL ❯❯❯ ")
        val input: List<String> = readln().split(" ")

        try {
            KReadEvalPrintLoop()
                .subcommands(CallHome(), Cookies(), Exit())
                .parse(input)
            println()
        } catch (e: PrintHelpMessage) {
            echo(e.command.getFormattedHelp())
            println()
        } catch (e: UsageError) {
            echo(e.helpMessage())
            println()
        } catch (e: Throwable) {
            println("ERROR: ${e.cause}")
            println()
        }
    }
}

class KReadEvalPrintLoop : CliktCommand() {
    override val commandHelp = """
        This application is a Read-Eval-Print-Loop.

        Call any of the commands to do fun stuff.
    """
    override fun run() = Unit
}

class CallHome : CliktCommand(help = "Call home", name = "callhome") {
    private val name by option().default("me")
    override fun run() { echo("Hi mom... it's $name") }
}

class Cookies : CliktCommand(help = "Bake cookies") {
    private val number by option().int().default(12)
    override fun run() { echo("Baking $number cookies...") }
}

class Exit : CliktCommand(help = "Exit REPL") {
    override fun run() {
        echo("Exiting REPL...")
        exitProcess(0)
    }
}
