package eu.klech.adventofcode2024.d10

import java.io.File

fun main () {

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "10a-sample.txt"
    val data = "10a-data.txt"

    val input = readFile(data)

    val zeros = getStartPositions(input)
    println(zeros)

    val result = zeros.sumOf { countTrailHeads(input, it) }
    println("\n\n Result: $result")
}

fun readFile(fileName: String): List<List<Int>>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName)
            .useLines { it.toList() }
            .map { Regex("\\d").findAll(it).map { it.value.toInt() }.toList() }

fun countTrailHeads(data: List<List<Int>>, item: Pair<Int,Int>): Int {
    if(data[item.first][item.second] == 9) {
        return 1
    }
    var result = 0
    potentialNeighbours(item.first, item.second).forEach { potentialNeighbour ->
        if(isInBounds(data, potentialNeighbour.first, potentialNeighbour.second)) {
            val neighbour = data[potentialNeighbour.first][potentialNeighbour.second]
            if (neighbour == data[item.first][item.second] + 1) {
                result += countTrailHeads(data, Pair(potentialNeighbour.first, potentialNeighbour.second))
            }
        }
    }
    return result
}

fun potentialNeighbours(i: Int, j: Int): Set<Pair<Int,Int>> {
    return setOf(
        Pair(i - 1, j),
        Pair(i, j + 1),
        Pair(i + 1, j),
        Pair(i, j - 1)
    )
}

fun getStartPositions(data: List<List<Int>>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int,Int>>()
    data.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            if (c == 0) {
                result.add(Pair(i,j))
            }
        }
    }
    return result
}

fun isInBounds(input: List<List<Int>>, i: Int, j: Int): Boolean {
    return i >= 0 && i < input.size && j >= 0 && j < input[0].size
}
