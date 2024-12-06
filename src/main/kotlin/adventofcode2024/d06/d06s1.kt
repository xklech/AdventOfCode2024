package eu.klech.adventofcode2024.d06

import java.io.File
import java.security.KeyStore.TrustedCertificateEntry
import kotlin.math.abs

val directions = listOf('^','>','v','<')

fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "6a-sample.txt"
    val data = "6a-data.txt"

    val input = readFile(data)

    val maxTriesUntilLoop = input.size * input[0].length * 4

    val (initI, initJ) = findInitLocation(input)
    val directionIndex = directions.indexOf(input[initI][initJ])
    var result = 0
   for (i in input.indices) {
       for (j in input[0].indices) {
           if ((i == initI && j == initJ) || input[i][j] == '#') {
               continue
           }
           val copy = input.toMutableList()
           copy[i] = copy[i].replaceRange(j,j+1,"#")

           if (isLoop(maxTriesUntilLoop, copy, initI, initJ, directionIndex)) {
               result++
           }

           println("Result: $result")
       }
   }

    println("\n\n Result: $result")
}

fun isLoop(maxTries: Int, input: List<String>, initI: Int, initJ: Int, initDirection: Int): Boolean {
    var inBounds = true
    var i = initI
    var j = initJ
    var directionIndex = initDirection
    var tries = 0
    while (inBounds) {
        tries++
        val nextStep = getNextStep(input, Pair(i,j), directionIndex)
        i = nextStep.first
        j = nextStep.second
        directionIndex = nextStep.third
        inBounds = isInBounds(input,i,j)
        if (tries > maxTries) {
            return true
        }
    }
    return false
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