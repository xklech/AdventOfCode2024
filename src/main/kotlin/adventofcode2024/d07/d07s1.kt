package eu.klech.adventofcode2024.d07

import java.io.File

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "7a-sample.txt"
    val data = "7a-data.txt"

    val input = readFile(data)
    var result:Long = 0

    input.forEach { line ->
        val lineNumbers = Regex("\\d+").findAll(line).map { it.value.toLong() }.toList()
        val lineResult = lineNumbers[0]
        val numbers = lineNumbers.subList(1,lineNumbers.size)
        if (isValid(lineResult, numbers)) {
            result += lineResult
        }
    }


    println("\n\n Result: $result")
    println("\n\n Long max: ${Long.MAX_VALUE}")
}


fun readFile(fileName: String): List<String>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }

fun isValid(goal: Long, numbers: List<Long>): Boolean {
    val permutations = permutations(numbers.size -1)
    var result:Long = -1
    permutations.forEach { perm ->
        result = numbers[0]
        for(i in 1.. numbers.lastIndex) {
            val next = numbers[i]
            result = when(perm[i-1]){
                '+' -> result + next
                '*' -> result * next
                else -> 0
            }
            if (result > goal) {
                break
            }
        }
        if(result == goal) {
            println("goal: $goal, numbers: $numbers")
            println("winning perm: $perm")
            return true
        }
    }

    return false
}
val permutationOperations = listOf('+', '*')

fun permutations(operationsCount: Int): List<List<Char>> {
    val permutations = mutableListOf<List<Char>>()
    if (operationsCount == 1) {
        permutations += permutationOperations.map { listOf(it) }
        return permutations
    }

    for (j in 0 .. permutationOperations.lastIndex) {
        val permReminder = permutations(operationsCount - 1)
        permReminder.forEach { perm ->
            permutations.add(listOf(permutationOperations[j]) + perm)
        }
    }

    return permutations
}