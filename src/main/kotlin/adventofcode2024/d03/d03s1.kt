package eu.klech.adventofcode2024.d03

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src\\main\\resources\\" +"1a-sample.txt").absolutePath}")

    val sample = "3a-sample.txt"
    val data = "3a-data.txt"

    val formulas = mutableListOf<String>()
    eu.klech.adventofcode2024.d02.readFile(data).forEach { line ->
        val lineFormulas = Regex("mul\\([1-9]\\d{0,2},[1-9]\\d{0,2}\\)").findAll(line).map { it.value }.toList()
        formulas += lineFormulas
    }
    println("Formulas: $formulas")
    var result = 0
    formulas.forEach {formula ->
        result += evaluate(formula)
    }

    println("\n\n Result: $result")
}

fun evaluate(formula: String):Int {
    println("Formula: $formula")
    return Regex("\\d+").findAll(formula).map { it.value.toInt() }.toList().reduce {a,b -> a * b}
}

fun readFile(fileName: String): List<String>
        = File("src\\main\\resources\\" + fileName).useLines { it.toList() }