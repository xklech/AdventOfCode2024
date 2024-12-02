package eu.klech.adventofcode2024.d01

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src\\main\\resources\\" +"1a-sample.txt").absolutePath}")

    val sample = "1a-sample.txt"
    val data = "1a-data.txt"

    val lines = readFile(data)
    val leftNumbers = mutableListOf<Int>()
    val rightNumbers = mutableListOf<Int>()

    lines.forEach { line ->
        val numbers = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
        leftNumbers += numbers[0]
        rightNumbers += numbers[1]
    }
    leftNumbers.sort()
    rightNumbers.sort()

    println("Numbers left: ${leftNumbers}")
    println("Numbers right: ${rightNumbers}")

    var sum = 0;
    for (i in leftNumbers.indices) {
        sum += abs(leftNumbers[i] - rightNumbers[i])
    }

    println("\n\n Result: ${sum}")
}

fun readFile(fileName: String): List<String>
        = File("src\\main\\resources\\" + fileName).useLines { it.toList() }