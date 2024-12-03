package eu.klech.adventofcode2024.d03

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src\\main\\resources\\" +"1a-sample.txt").absolutePath}")

    val sample = "3b-sample.txt"
    val data = "3a-data.txt"

    val formulas = mutableListOf<String>()
    eu.klech.adventofcode2024.d02.readFile(data).forEach { line ->
        val lineFormulas = Regex("mul\\([1-9]\\d{0,2},[1-9]\\d{0,2}\\)|don't\\(\\)|do\\(\\)").findAll(line).map { it.value }.toList()
        formulas += lineFormulas
    }
    println("Formulas: $formulas")
    var result = 0
    var enabled = true
    formulas.forEach {command ->
        if (command == "do()") {
            enabled = true
            return@forEach
        }
        if (command == "don't()") {
            enabled = false
            return@forEach
        }
        if (enabled)
            result += evaluate(command)
    }

    println("\n\n Result: $result")
}

