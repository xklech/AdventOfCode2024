package eu.klech.adventofcode2024.d11

import java.io.File

fun main () {

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "11a-sample.txt"
    val data = "11a-data.txt"

    val input = readFile(data)

    val result = countNumbersAfter25Blinks(input)
    println("\n\n Result: $result")
}

fun readFile(fileName: String): List<Long>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName)
            .useLines { it.toList() }
            .map { Regex("\\d+").findAll(it).map { it.value.toLong() }.toList() } [0]

fun countNumbersAfter25Blinks(data: List<Long>): Int {
    val numbers = data.toMutableList()
    repeat(25) { index ->
        if (index <5) println(numbers)
        var i = 0
        while(i <= numbers.lastIndex) {
            if(numbers[i] == 0L) {
                numbers[i] = 1L
            } else {
                val str = numbers[i].toString()
                if(str.isEven()) {
                    val firstStone = str.splitAtIndex(str.length/2).first.toLong()
                    val secondStone = str.splitAtIndex(str.length/2).second.toLong()
                    numbers.add(i, firstStone)
                    numbers[i+1] = secondStone
                    i++
                } else {
                    numbers[i] *= 2024L
                }
            }
            i++
        }
    }
    return numbers.size
}

fun String.isEven(): Boolean = this.length % 2 == 0

fun String.splitAtIndex(index : Int) = take(index) to substring(index)