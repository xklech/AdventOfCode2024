package eu.klech.adventofcode2024.d04

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "4a-sample.txt"
    val data = "4a-data.txt"


    val inputData = readFile(sample)
    var result = 0

    //left right + right left
    inputData.forEach{ line ->
        result += countXmassInLine(line)
    }
    //count rows
    result += countXmassInColumn(inputData)

    println("Traverse diagonally -_-")
    for (i in 0 .. inputData[0].length-4) {
        val subMatrixRight = inputData.subList(i, i+4).mapIndexed { index, line ->  line.takeLast(line.length-index) + "b".repeat(index)}

        result += countXmassInColumn(subMatrixRight)
        val subMatrixLeft = inputData.subList(i, i+4).mapIndexed { index, line ->  "b".repeat(3-index) + line.substring(0, line.length-index-3)}
        result += countXmassInColumn(subMatrixLeft)
        println(subMatrixLeft)
    }

    println("\n\n Result: $result")
}

fun countXmassInLine(line: String):Int {
    return Regex("XMAS").findAll(line).count() + Regex("XMAS").findAll(line.reversed()).count()
}

fun countXmassInColumn(data: List<String>): Int {
    return data[0].foldIndexed(0) { index, acc, c ->
        acc + countXmassInLine(data.map { it[index] }.joinToString(separator = ""))
    }
}

fun readFile(fileName: String): List<String>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }