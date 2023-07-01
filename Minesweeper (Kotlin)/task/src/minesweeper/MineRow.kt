package minesweeper

import kotlin.random.Random

class MineRow(private val row :Int = 9, private val column :Int = 9, private val mines :Int = 0) {
    private var checkMines = 0;
    private val realField = MutableList(column) { MutableList(row){"0"} }
    private val visibleField = MutableList(column) { MutableList(row){"0"} }
    init {
        addMines(mines)
    }

    fun printMineRow() {
        println(" │123456789│\n—│—————————│")
        var num = 1
        for (list in visibleField) {
            println("$num|" + list.joinToString("") + "|")
            num++
        }
        println("-│—————————│")
    }

    private fun addMines(amount :Int) {
        var allMines = 0;
        while (allMines < amount) {
            val row = Random.nextInt(0,this.row)
            val column = Random.nextInt(0,this.column)
            if(realField[column][row] != "X") {
                realField[column][row] = "X"
                allMines++
                }
        }
        addNumbers()
    }

    private fun addNumbers() {
        for (column in 0 until  this.column) {
            for (row in 0 until  this.row) {
                if (realField[column][row] == "X") {
                    visibleField[column][row] = "."
                    if(row > 0 && realField[column][row - 1] != "X") {
                        realField[column][row - 1] = (realField[column][row - 1].toInt() + 1).toString()
                        visibleField[column][row - 1] = (visibleField[column][row - 1].toInt() + 1).toString()
                    }
                    if(row < this.row - 1 && realField[column][row + 1] != "X") {
                        realField[column][row + 1] = (realField[column][row + 1].toInt() + 1).toString()
                        visibleField[column][row + 1] = (visibleField[column][row + 1].toInt() + 1).toString()
                    }
                    if(column > 0 && realField[column - 1][row] != "X") {
                        realField[column - 1][row] = (realField[column - 1][row].toInt() + 1).toString()
                        visibleField[column - 1][row] = (visibleField[column - 1][row].toInt() + 1).toString()
                    }
                    if(column < this.column - 1 && realField[column + 1][row] != "X") {
                        realField[column + 1][row] = (realField[column + 1][row].toInt() + 1).toString()
                        visibleField[column + 1][row] = (visibleField[column + 1][row].toInt() + 1).toString()
                    }
                    if(column > 0 && row > 0 && realField[column - 1][row - 1] != "X") {
                        realField[column - 1][row - 1] = (realField[column - 1][row - 1].toInt() + 1).toString()
                        visibleField[column - 1][row - 1] = (visibleField[column - 1][row - 1].toInt() + 1).toString()
                    }
                    if(column < this.column - 1 && row < this.row - 1 && realField[column + 1][row + 1] != "X") {
                        realField[column + 1][row + 1] = (realField[column + 1][row + 1].toInt() + 1).toString()
                        visibleField[column + 1][row + 1] = (visibleField[column + 1][row + 1].toInt() + 1).toString()
                    }
                    if(column < this.column - 1 && row > 0 && realField[column + 1][row - 1] != "X") {
                        realField[column + 1][row - 1] = (realField[column + 1][row - 1].toInt() + 1).toString()
                        visibleField[column + 1][row - 1] = (visibleField[column + 1][row - 1].toInt() + 1).toString()
                    }
                    if(column > 0 && row < this.row - 1 && realField[column - 1][row + 1] != "X") {
                        realField[column - 1][row + 1] = (realField[column - 1][row + 1].toInt() + 1).toString()
                        visibleField[column - 1][row + 1] = (visibleField[column - 1][row + 1].toInt() + 1).toString()
                    }
                }
            }
        }
        for (column in 0 until  this.column) {
            for (row in 0 until  this.row) {
                if (realField[column][row] == "0") {
                    realField[column][row] = "."
                    visibleField[column][row] = "."
                }
            }
        }
    }

    fun checkRow(coordinates :String) {
        val coordinates = coordinates.split(" ")

        if(visibleField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] == ".") {
            visibleField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] = "*"
        } else if (visibleField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] == "*") {
            visibleField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] = "."
        }

        if (realField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] == "X") {
            checkMines++
        }
    }

    fun isCheckAllMines() :Boolean {
        var isVisibleRow = 0;
        for (column in 0 until  this.column) {
            for (row in 0 until  this.row) {
                if(visibleField[column][row] == ".") {
                    isVisibleRow++
                }
            }
        }
        return mines == checkMines || mines == isVisibleRow
    }
}
