package eu.klech.adventofcode2024.d05

import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "5a-sample.txt"
    val data = "5a-data.txt"

    val inputData = readFile(data)
    val orderingRules = extractRules(inputData)
    val pageUpdates = extractUpdates(inputData)

    fun missing(left: Int, right: Int) = left to right !in orderingRules

    fun isValid(ints: List<Int>): Boolean {
        for (li in 0..<ints.lastIndex)
            for (ri in li + 1..ints.lastIndex)
                if (missing(ints[li], ints[ri]))
                    return false
        return true
    }

    val result = pageUpdates.filterNot(::isValid).map(List<Int>::toMutableList).sumOf { update ->
        check@ while (true) {
            for (li in 0..<update.lastIndex) {
                val left = update[li]
                val ri = li + 1
                val right = update[ri]

                if (missing(left, right)) {
                    update[li] = right
                    update[ri] = left
                    continue@check
                }
            }
            break
        }

        update[update.size / 2]
    }

    println("Result: $result")
}

fun extractRules(data: List<String>): List<Pair<Int, Int>> {
    return data.filter { Regex("\\d+\\|\\d+").matches(it) }.map {
        val (left, right) = it.split("|", limit = 2).map { it.toInt() }
        left to right
    }
}

fun extractUpdates(data: List<String>): List<List<Int>> {
    return data.filter { Regex("\\d+,").findAll(it).count() > 0 }.map { it.split(",").map { it.toInt() }}
}
