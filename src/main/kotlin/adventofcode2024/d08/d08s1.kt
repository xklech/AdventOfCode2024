package eu.klech.adventofcode2024.d08

import java.io.File

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "8a-sample.txt"
    val data = "8a-data.txt"

    val input = readFile(data)
    val antiNodes = antiNodes(input)

    println(antiNodes)

    val result = antiNodes.count()
    println("\n\n Result: $result")
}


fun readFile(fileName: String): List<String>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }

fun extractNodes(data: List<String>): Map<Char, List<Pair<Int, Int>>> {
    val set = mutableMapOf<Char, List<Pair<Int, Int>>>()
    data.forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            if(Regex("[\\da-zA-z]").matches(c.toString())) {
                set[c] = if (set[c] != null) set[c]!!.plus(listOf(Pair(i, j))) else listOf(Pair(i, j))
            }
        }
    }
    return set
}

fun antiNodes(data: List<String>): Set<Pair<Int,Int>> {
    val possibleAntiNodes = mutableSetOf<Pair<Int, Int>>()
    val nodes = extractNodes(data)
    println(nodes)
    nodes.forEach { char, list ->
        for (i in 0..list.lastIndex) {
            for (j in i..list.lastIndex) {
                if (i == j) {
                    continue
                }
                val firstNode = list[i]
                val secondNode = list[j]
                possibleAntiNodes.addAll(getPossibleAntiNodes(firstNode, secondNode))
            }
        }
    }
    return possibleAntiNodes.filter { node -> isInBounds(data, node.first, node.second) }.toSet()
}

fun getPossibleAntiNodes(firstNode: Pair<Int,Int>, secondNode: Pair<Int,Int>): Set<Pair<Int,Int>> {
    val vector = Pair(firstNode.first-secondNode.first, firstNode.second-secondNode.second)
    return setOf(
        Pair(firstNode.first+vector.first, firstNode.second+vector.second),
        Pair(secondNode.first-vector.first, secondNode.second-vector.second)
    )
}

fun isInBounds(input: List<String>, i: Int, j: Int): Boolean {
    return i >= 0 && i < input.size && j >= 0 && j < input[0].length
}
