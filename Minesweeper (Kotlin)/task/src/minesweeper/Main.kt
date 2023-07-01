package minesweeper

fun main() {
    println("Enter the height, width of the field and the number of mines")
    val valuesMineRow = readln().split(" ");
    val mineRow = MineRow(valuesMineRow[0].toInt(),valuesMineRow[1].toInt(),valuesMineRow[2].toInt())
    while (!mineRow.isCheckAllMines()) {
        println("Set/unset mines marks or claim a cell as free:")
        mineRow.checkRow(readln())
    }
}
