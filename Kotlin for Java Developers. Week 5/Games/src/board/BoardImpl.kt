package board

import board.Direction.*

open class SimpleSquareBoard(final override val width: Int) : SquareBoard {
    private val cells: Array<Array<Cell>>

    init {
        if (width <= 0) throw IllegalArgumentException("Width must be a positive integer.")

        cells = Array(width) { i ->
            Array(width) { j -> Cell(i + 1, j + 1) }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        cells.getOrNull(i - 1)?.getOrNull(j - 1)

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i, j) ?: throw IllegalArgumentException("Invalid cell coordinates.")

    override fun getAllCells(): Collection<Cell> =
        cells.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { j ->
            cells.getOrNull(i - 1)?.getOrNull(j - 1)
        }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { i ->
            cells.getOrNull(i - 1)?.getOrNull(j - 1)
        }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            UP -> getCellOrNull(i - 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
        }
}

class SimpleGameBoard<T>(width: Int) : SimpleSquareBoard(width), GameBoard<T> {
    private val cellValues: MutableMap<Cell, T?> = getAllCells().associateWith { null }.toMutableMap()

    override operator fun get(cell: Cell): T? = cellValues[cell]

    override operator fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        cellValues.filterValues(predicate).keys

    override fun find(predicate: (T?) -> Boolean): Cell? =
        cellValues.entries.find { predicate(it.value) }?.key

    override fun any(predicate: (T?) -> Boolean): Boolean =
        cellValues.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean =
        cellValues.values.all(predicate)
}

fun createSquareBoard(width: Int): SquareBoard = SimpleSquareBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = SimpleGameBoard(width)

