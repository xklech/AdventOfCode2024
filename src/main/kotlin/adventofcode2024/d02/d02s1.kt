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
        if (isSafe(report))
            safeReports += 1
    }

    println("\n\n Result: ${safeReports}")
}

fun isSafe(report: List<Int>):Boolean {
    println("Report: $report")
    var asc = false
    var desc = false
    for (i in 0..<report.size-1) {
        if (abs(report[i]-report[i+1]) > 3)
            return false
        if (asc && report[i] >= report[i+1])
            return false
        if (desc && report[i] <= report[i+1])
            return false
        if (report[i] <= report[i+1])
            asc = true
        if (report[i] >= report[i+1])
            desc = true
    }
    return true
}

fun readFile(fileName: String): List<String>
        = File("src\\main\\resources\\" + fileName).useLines { it.toList() }