package minesweeper

import kotlin.random.Random

class MineRow(private val column :Int = 9, private val row :Int = 9, private val mines :Int = 13) {
    private var checkMines = 0;
    private var isLose = false;
    private var isNotVisibleRow = true;
    private val realField = MutableList(column) { MutableList(row){"0"} }
    private val visibleField = MutableList(column) { MutableList(row){"0"} }
    init {
        addMines(mines)
    }

    fun printMineRow() {
        var num = 1
        print("  │ ")
        for (i in 1..row)
        print(if(i < 10) "0$i  " else "$i  ")
        print("│\n——│")
        for (i in 1..row)
        print("————")
        println("|")
        for (list in visibleField) {
            if (num < 10) println("0$num| " + list.joinToString(" | ") + "  |")
            else println("$num| " + list.joinToString(" | ") + "  |")
            num++
        }
        print("——│")
        for (i in 1..row)
            print("————")
        println("|")
    }

    private fun addMines(amount :Int) {
        if (this.row * this.column >= amount) {
            var allMines = 0;
            while (allMines < amount) {
                val row = Random.nextInt(0, this.row)
                val column = Random.nextInt(0, this.column)
                if (realField[column][row] != "X") {
                    realField[column][row] = "X"
                    allMines++
                }
            }
            addNumbers()
            printMineRow()
        } else {
            println("You entered too many mines for the specified field\nHow many mines do you want on the field?")
            addMines(readln().toInt())
        }

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
                    isNotVisibleRow = false
                }
            }
        }
    }

    fun checkRow(coordinates :String) {
        val coordinates = coordinates.split(" ")
        if(realField[coordinates[1].toInt() - 1][coordinates[0].toInt() - 1] != "X") {
            when (coordinates[2]) {
                "free" -> checkFreeRow(coordinates[1].toInt() - 1, coordinates[0].toInt() - 1)
                "mine" -> checkMineRow(coordinates[1].toInt() - 1, coordinates[0].toInt() - 1)
            }
            printMineRow()
        } else {
            for (i in 0 until  this.column) {
                for (j in 0 until  this.row) {
                    if(realField[i][j] == "X")
                        visibleField[i][j] = "X"
                }
            }
            printMineRow()
            println("You stepped on a mine and failed!")
            isLose = true
            checkMines = mines
        }


    }

    private fun checkFreeRow(column: Int, row: Int) {
        if (realField[column][row] == ".") {
            visibleField[column][row] = "/"
            for (i in 0 until this.column) {
                for (j in 0 until this.row) {
                    if (realField[i][j] != "X" && realField[i][j] !in "1".."9")
                        visibleField[i][j] = "/"
                }
            }
            /*for (i in 0 until this.column) {
                if (realField[i][row] != "X" && realField[i][row] !in "1".."9")
                    visibleField[i][row] = "/"
            }

            for (i in 0 until this.column) {
                for (j in 0 until this.row) {
                    if (realField[i][j] != "X" && visibleField[i].contains("/") && realField[i][j] !in "1".."9") {
                        visibleField[i][j] = "/"
                    }
                    if(i > 0) {
                        if(realField[i][j] != "X" && realField[i][j] !in "1".."9" && visibleField[i - 1][j] == "/")
                            visibleField[i][j] = "/"
                    }
                    if(i < this.column - 2) {
                        if(realField[i][j] != "X" && realField[i][j] !in "1".."9" && visibleField[i + 1][j] == "/")
                            visibleField[i][j] = "/"
                    }
                }
            }*/
        }
    }

    private fun checkMineRow(column: Int, row: Int) {
        if(visibleField[column][row] == ".") {
            visibleField[column][row] = "*"
        } else if (visibleField[column][row] == "*") {
            visibleField[column][row] = "."
        }
        if (realField[column][row] == "X") {
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
        if((mines == checkMines || mines == isVisibleRow || isNotVisibleRow) && !isLose) {
            printMineRow()
            println("Congratulations! You found all the mines!")
        }
        return mines == checkMines || mines == isVisibleRow
    }
}
