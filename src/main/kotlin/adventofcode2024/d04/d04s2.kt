package eu.klech.adventofcode2024.d04

import java.io.File
import kotlin.math.abs


fun main (){

    println("Path: ${File("src${File.separator}main${File.separator}resources${File.separator}" +"1a-sample.txt").absolutePath}")

    val sample = "4a-sample.txt"
    val data = "4a-data.txt"


    val inputData = readFile(data)
    var result = 0

    for (i in 1..<inputData.size-1){
        for (j in 1..<inputData[0].length-1) {
            if (inputData[i][j] == 'A') {
                result += countMasses(i,j,inputData)
            }
        }
    }

    println("\n\n Result: $result")
}

fun countMasses(i:Int, j: Int, data: List<String>): Int {

    if (!((data[i-1][j-1] == 'M' && data[i+1][j+1] == 'S') || (data[i-1][j-1] == 'S' && data[i+1][j+1] == 'M'))) {
        return 0
    }
    if (!((data[i-1][j+1] == 'S' && data[i+1][j-1] == 'M') || (data[i-1][j+1] == 'M' && data[i+1][j-1] == 'S'))) {
        return 0
    }
    return 1
}
