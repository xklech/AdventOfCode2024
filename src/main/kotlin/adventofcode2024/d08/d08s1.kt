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
    nodes.forEach { (_, list) ->
        if (list.size > 1) {
            for (i in 0..list.lastIndex) {
                for (j in i..list.lastIndex) {
                    if (i == j) {
                        continue
                    }
                    val firstNode = list[i]
                    val secondNode = list[j]
                    possibleAntiNodes.addAll(getPossibleAntiNodes(data, firstNode, secondNode))
                }
            }
        }
    }
    return possibleAntiNodes.filter { node -> isInBounds(data, node.first, node.second) }.toSet()
}

fun getPossibleAntiNodes(data:List<String>, firstNode: Pair<Int,Int>, secondNode: Pair<Int,Int>): Set<Pair<Int,Int>> {
    val vector = Pair(firstNode.first-secondNode.first, firstNode.second-secondNode.second)
    val set = mutableSetOf(firstNode, secondNode)

    set.addAll(generateNextNodes(data, firstNode, vector))
    val reverseVector = Pair(vector.first*-1,vector.second*-1)
    set.addAll(generateNextNodes(data, secondNode, reverseVector))

    return set
}

fun generateNextNodes(data: List<String>, current: Pair<Int, Int>, vector: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val set = mutableSetOf<Pair<Int, Int>>()
    var next = current
    while (true) {
        next = generateNextNode(next, vector)
        if (!isInBounds(data, next.first, next.second)) {
            break
        }
        set.add(next)
    }
    return set
}

fun generateNextNode(current: Pair<Int, Int>, vector: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(current.first+vector.first, current.second+vector.second)
}

fun isInBounds(input: List<String>, i: Int, j: Int): Boolean {
    return i >= 0 && i < input.size && j >= 0 && j < input[0].length
}
