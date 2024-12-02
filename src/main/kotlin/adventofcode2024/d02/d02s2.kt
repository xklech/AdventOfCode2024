package eu.klech.adventofcode2024.d02

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src\\main\\resources\\" +"1a-sample.txt").absolutePath}")

    val sample = "2a-sample.txt"
    val data = "2a-data.txt"

    val reports = mutableListOf<List<Int>>()
    readFile(data).forEach { line ->
        val nextLevels = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
        reports += nextLevels
    }
    var safeReports = 0
    reports.forEach {report ->
        if (checkWithLevelRemoval(report))
            safeReports += 1
    }

    println("\n\n Result: ${safeReports}")
}

fun checkWithLevelRemoval(report: List<Int>): Boolean {
    if (isSafe(report))
        return true
    for (i in report.indices) {
        val copy = report.toMutableList()
        copy.removeAt(i)
        if (isSafe(copy))
            return true
    }
    return false
}