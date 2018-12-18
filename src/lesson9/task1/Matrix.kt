@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height <= 0 || width <= 0) throw IllegalArgumentException()
        else MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, val e: E) : Matrix<E> {

    private val table = mutableListOf<MutableList<E>>()

    init {
        for (row in 0 until height) {
            table.add(mutableListOf())
            for (column in 0 until width) table[row].add(e)
        }
    }

    override fun get(row: Int, column: Int): E = table[row][column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        table[row][column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (other !is MatrixImpl<*> || height != other.height || width != other.width) return false
        for (row in 0 until table.size)
            for (column in 0 until table[row].size) {
                if (get(row, column) != other[row, column]) return false
            }
        return true
    }

    override fun toString(): String {
        val ans = StringBuilder()
        ans.append("{")
        for (row in 0 until height) {
            ans.append("[")
            for (column in 0 until width) {
                ans.append(get(row, column))
                if (column != width - 1) ans.append("; ")
            }
            ans.append("]")
        }
        ans.append("}")
        return "$ans"
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + table.hashCode()
        return result
    }
}

