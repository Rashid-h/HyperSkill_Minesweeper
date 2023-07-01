package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val mineRow = MineRow(mines = readln().toInt())
    while (!mineRow.isCheckAllMines()) {
        mineRow.printMineRow()
        println("Set/delete mines marks (x and y coordinates):")
        mineRow.checkRow(readln())
    }
    println("Congratulations! You found all the mines!")
}
