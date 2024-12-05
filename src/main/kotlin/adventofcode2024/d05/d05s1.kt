package eu.klech.adventofcode2024.d05

import java.io.File
import kotlin.math.abs

data class Rules(
    var before: Map<Int, Set<Int>>,
    var after: Map<Int, Set<Int>>
)

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "5a-sample.txt"
    val data = "5a-data.txt"

    val inputData = readFile(data)
    val orderingRules = extractOrderingRules(inputData)
    val pageUpdates = extractPageUpdates(inputData)

    println(pageUpdates)
    var result = 0


    pageUpdates.forEach { line ->
        val updates = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
        var isValid = true
        updates.forEachIndexed {index, item ->
            val before = if (index > 0 ) updates.subList(0, index) else listOf()
            val after = if (index < updates.size -1) updates.subList(index + 1 , updates.size) else listOf()

            before.forEach { beforeValue ->
                if (orderingRules.after.containsKey(item) && orderingRules.after[item]!!.contains(beforeValue)) {
                    isValid = false
                }
            }
            after.forEach { afterValue ->
                if (orderingRules.before.containsKey(item) && orderingRules.before[item]!!.contains(afterValue)) {
                    isValid = false
                }
            }
        }
        println("valid: $isValid")
        if (isValid) {
            println(updates)
            println(updates[updates.size / 2])
            result += updates[updates.size / 2]
        }
    }
    println("\n\n Result: $result")
}

fun readFile(fileName: String): List<String>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }

fun extractOrderingRules(data: List<String>): Rules {
    val mapAfter = mutableMapOf<Int, Set<Int>>()
    val mapBefore = mutableMapOf<Int, Set<Int>>()
    data.filter { Regex("\\d+\\|\\d+").matches(it) }.forEach { line ->
        val split = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
        mapAfter[split[0]] = if (mapAfter[split[0]] != null) {
            mapAfter[split[0]]!! + split[1]} else setOf(split[1])
        mapBefore[split[1]] = if (mapBefore[split[1]] != null) {
            mapBefore[split[1]]!! + split[0]} else setOf(split[0])
    }
    return Rules(mapBefore, mapAfter)
}

fun extractPageUpdates(data: List<String>): List<String> {
    return data.filter { Regex("\\d+,").findAll(it).count() > 0 }
}