package eu.klech.adventofcode2024.d09

import java.io.File

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "9a-sample.txt"
    val data = "9a-data.txt"

    val input = readFile(data)
    val expanded = expand(input)
    val rearranged = rearrange(expanded)

    println("Input: $input")
    println("Expanded: $expanded")
    println("Rearranged: $rearranged")

    val result = computeChecksum(rearranged)
    println("\n\n Result: $result")
}

fun readFile(fileName: String): String
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }[0]

fun expand(inputString: String): List<Int> {
    val list = mutableListOf<Int>()
    val numList = Regex("\\d").findAll(inputString).map { it.value.toInt() }.toList()
    var blockFile = 0
    numList.forEachIndexed { index, num ->
        if (index % 2 == 0) {
            list.addAll(generateSequence { blockFile }.take(num))
            blockFile++
        } else {
            list.addAll(generateSequence { -1 }.take(num))
        }
    }
    return list
}

fun rearrange(expanded: List<Int>): List<Int> {
    val result = expanded.toMutableList()
    var searchIndex = 0
    for (reverseIndex in expanded.lastIndex downTo 0) {
        val reverseChar = expanded[reverseIndex]
        if (reverseChar == -1) continue
        if (searchIndex >= reverseIndex) {
            return result
        }
        for(i in searchIndex..<reverseIndex) {
            if (expanded[i] == -1) {
                result[i] = reverseChar
                result[reverseIndex] = -1
                searchIndex = i + 1
                break
            }
        }
    }
    return result
}

fun computeChecksum(rearranged: List<Int>): Long {
    return rearranged.filter { it != -1 }.mapIndexed { index, i -> index * i }.sumOf { it.toLong() }
}