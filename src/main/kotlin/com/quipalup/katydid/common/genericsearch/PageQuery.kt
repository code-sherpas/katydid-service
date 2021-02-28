package com.quipalup.katydid.common.genericsearch

data class PageQuery(val number: Int, val size: Int, val maxSize: Int) {
    init {
        ensureValidNumber(number)
        ensureValidSize(size, maxSize)
    }

    private fun ensureValidSize(size: Int, maxSize: Int) {
        when {
            size < 0 -> throw IllegalPageSizeException(size.toString())
            size > maxSize -> throw IllegalPageSizeException(size.toString())
        }
    }

    private fun ensureValidNumber(number: Int) {
        when {
            number < 1 -> {
                throw IllegalPageNumberException(number.toString())
            }
        }
    }
}

class IllegalPageNumberException(override val message: String) : RuntimeException(message)
class IllegalPageSizeException(override val message: String) : RuntimeException(message)
