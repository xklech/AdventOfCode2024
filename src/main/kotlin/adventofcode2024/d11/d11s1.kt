package eu.klech.adventofcode2024.d11

import java.io.File

val cache: MutableMap<Pair<Int,Long>, Long> = mutableMapOf()

fun main () {

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "11a-sample.txt"
    val data = "11a-data.txt"

    val input = readFile(data)

    val result = countNumbersAfter25Blinks(input)
    println("Cache items: ${cache.size}")
    println("\n\n Result: $result")
}

fun readFile(fileName: String): List<Long>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName)
            .useLines { it.toList() }
            .map { Regex("\\d+").findAll(it).map { it.value.toLong() }.toList() } [0]

fun countNumbersAfter25Blinks(data: List<Long>): Long {
    return data.sumOf {fillCacheRecursively(1, it) }
}

fun fillCacheRecursively(level:Int, stone: Long): Long {
    if (level > 75) {
        return 1
    }
    if (cache.containsKey(Pair(level, stone))) {
        return cache[Pair(level, stone)]!!
    }
    //expand
    val count: Long
    if(stone == 0L) {
        count = fillCacheRecursively(level+1, 1L)
    } else {
        val str = stone.toString()
        if(str.isEven()) {
            val firstStone = str.splitAtIndex(str.length/2).first.toLong()
            val secondStone = str.splitAtIndex(str.length/2).second.toLong()
            count = fillCacheRecursively(level+1, firstStone) + fillCacheRecursively(level+1, secondStone)
        } else {
            count = fillCacheRecursively(level+1, stone*2024L)
        }
    }
    cache[Pair(level, stone)] = count
    return count
}

fun String.isEven(): Boolean = this.length % 2 == 0

fun String.splitAtIndex(index : Int) = take(index) to substring(index)