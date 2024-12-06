package eu.klech.adventofcode2024.d06

import java.io.File
import java.security.KeyStore.TrustedCertificateEntry
import kotlin.math.abs

val directions = listOf('^','>','v','<')
val visitedPlaces = mutableSetOf<Pair<Int, Int>>()

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "6a-sample.txt"
    val data = "6a-data.txt"

    val input = readFile(data)

    var (i, j) = findInitLocation(input)
    var directionIndex = directions.indexOf(input[i][j])

    println("Init: ${findInitLocation(input)}, initDirection: ${directions[directionIndex]}")
    var inBounds = true
    while (inBounds) {
        visitedPlaces.add(Pair(i,j))
        val nextStep = getNextStep(input, Pair(i,j), directionIndex)
        println("Next Step: $nextStep Count: ${visitedPlaces.count()}")
        i = nextStep.first
        j = nextStep.second
        directionIndex = nextStep.third
        inBounds = isInBounds(input,i,j)
    }

    println("\n\n Result: ${visitedPlaces.count()}")
}

fun readFile(fileName: String): List<String>
        = File("src${File.separator}main${File.separator}resources${File.separator}" + fileName).useLines { it.toList() }

fun findInitLocation(input: List<String>): Pair<Int, Int> {
    for (i in input.indices) {
        for (j in input[0].indices) {
            if (directions.contains(input[i][j])) {
                return Pair(i,j)
            }
        }
    }
    return Pair(0,0)
}

fun getNextStep(input: List<String>, currentLocation: Pair<Int, Int>, directionIndex: Int): Triple<Int, Int, Int> {
    var direction = directionIndex
    for (k in 1..4) {
        val nextCoords = getNextCoords(currentLocation, direction)
        val i = nextCoords.first
        val j = nextCoords.second
        if (isInBounds(input, i, j)) {
            if (input[i][j] != '#') {
                return Triple(i,j,direction)
            } else {
                direction = rotateDirection(direction)
            }
        } else {
            return Triple (i,j,direction)
        }
    }
    return Triple (currentLocation.first, currentLocation.second, directionIndex)
}

fun getNextCoords(currentLocation: Pair<Int, Int>, directionIndex: Int): Pair<Int, Int> {
    return when(directionIndex) {
        0 -> Pair(currentLocation.first -1, currentLocation.second)
        1 -> Pair(currentLocation.first, currentLocation.second + 1)
        2 -> Pair(currentLocation.first + 1, currentLocation.second)
        3 -> Pair(currentLocation.first, currentLocation.second - 1)
        else -> Pair(0,0)
    }
}

fun rotateDirection(current: Int):Int {
    return (current + 1) %  directions.size
}

fun isInBounds(input: List<String>, i: Int, j: Int): Boolean {
    return i >= 0 && i < input.size && j >= 0 && j < input[0].length
}