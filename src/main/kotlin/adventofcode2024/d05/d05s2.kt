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
    val orderingRules = extractOrderingRules(inputData)
    val pageUpdates = extractPageUpdates(inputData)

    val updates = pageUpdates.map { line -> Regex("\\d+").findAll(line).map { it.value.toInt() }.toList() }
    val invalidUpdates = updates.filter { update -> !isValid(update, orderingRules) }
    println(invalidUpdates.size)

    val count = AtomicInteger()
    val remaining = AtomicInteger()
    val threads = ArrayDeque<Thread>()
    invalidUpdates.forEach { update ->
        threads.addFirst(thread(start = false) {
            val permutation = permutationsRecursive(orderingRules, update, 0)
            println(permutation)
            println(permutation[permutation.size / 2])
            count.addAndGet(permutation[permutation.size / 2])
            remaining.addAndGet(1)
            println("Updated result: ${count.get()}")
            println("Done: ${remaining.get()}/${invalidUpdates.size}")
            if(threads.isNotEmpty()) {
                threads.pop().start()
            }
        })
    }
    println("Processors: ${Runtime.getRuntime().availableProcessors()}")
    for (i in 1..Runtime.getRuntime().availableProcessors()-2) {
        threads.pop().start()
    }
}

fun isValid(updates: List<Int>, orderingRules: Rules): Boolean {
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
    return isValid
}

fun permutationsRecursive(rules: Rules, input: List<Int>, index: Int): List<Int> {
    if (index == input.lastIndex && isValid(input, rules)) {
        return input
    }
    for (i in index .. input.lastIndex) {
        Collections.swap(input, index, i)
        val result = permutationsRecursive(rules, input, index + 1)
        if (result.isNotEmpty()) {
            return result
        }
        Collections.swap(input, i, index)
    }
    return listOf()
}