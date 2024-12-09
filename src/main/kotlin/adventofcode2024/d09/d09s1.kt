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

fun expand(inputString: String): List<Pair<Int,Int>> {
    val list = mutableListOf<Pair<Int,Int>>()
    val numList = Regex("\\d").findAll(inputString).map { it.value.toInt() }.toList()
    var blockFile = 0
    numList.forEachIndexed { index, num ->
        if (index % 2 == 0) {
            list.add(Pair(blockFile, num))
            blockFile++
        } else {
            list.add(Pair(-1, num))
        }
    }
    return list
}

fun rearrange(expanded: List<Pair<Int,Int>>): List<Pair<Int,Int>> {
    val result = expanded.toMutableList()
    val toOptimize = expanded.filter { it.first != -1 }.sortedBy { it.first }.asReversed()
    println("To Optimize: $toOptimize")
    toOptimize.forEach{file ->
        squashSpaces(result)
        val fileIndex = result.indexOf(file)
        for(i in 0..<fileIndex) {
            if (result[i].first == -1 && result[i].second >= file.second) {
                val space = result[i]
                result.removeAt(fileIndex)
                result.add(fileIndex, Pair(-1, file.second))
                result.removeAt(i)
                result.add(i, file)
                val remainingSpace = space.second - file.second
                if (remainingSpace > 0) {
                    result.add(i+1, Pair(-1, remainingSpace))
                }
                break
            }
        }

    }
    return result
}

fun squashSpaces(list: MutableList<Pair<Int,Int>>) {
    var iMax = list.lastIndex
    var i = 0
    while(i<iMax) {
        if (list[i].first == -1 && list[i+1].first == -1) {
            val combinedSize = list[i].second+list[i+1].second
            list.removeAt(i+1)
            list.removeAt(i)
            list.add(i, Pair(-1, combinedSize))
            iMax--
        }
        i++
    }
}

fun computeChecksum(rearranged: List<Pair<Int,Int>>): Long {
    var index = 0
    var result = 0L
    rearranged.forEach { item ->
        for(i in 0..<item.second) {
            if(item.first != -1) {
                result += item.first*(index)
            }
            index++
        }
    }
    return result
}