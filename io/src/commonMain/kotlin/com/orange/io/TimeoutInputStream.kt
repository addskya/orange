package com.orange.io

interface TimeoutCapableInput {
    fun readWithTimeout(timeoutMillis: Long): Int
    fun readWithTimeout(timeoutMillis: Long, buffer: ByteArray, offset: Int, length: Int): Int
}

class TimeoutInputStream(
    private val base: InputStream,
    timeoutMillis: Long = 0L
) : InputStream {
    var timeoutMillis: Long = timeoutMillis
        set(value) {
            require(value >= 0) { "timeout should >= zero" }
            field = value
        }

    override fun read(): Int {
        val t = timeoutMillis
        return if (t > 0 && base is TimeoutCapableInput) {
            base.readWithTimeout(t)
        } else {
            base.read()
        }
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        val t = timeoutMillis
        return if (t > 0 && base is TimeoutCapableInput) {
            base.readWithTimeout(t, buffer, offset, length)
        } else {
            base.read(buffer, offset, length)
        }
    }

    override fun available(): Int = base.available()

    override fun close() = base.close()
}

