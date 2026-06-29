package com.orange.io

class ByteArrayOutputStream : OutputStream {
    private var buffer: ByteArray = ByteArray(128)
    private var size: Int = 0


    override fun write(b: Int) {
        ensureCapacity(1)
        buffer[size++] = b.toByte()
    }

    override fun write(buffer: ByteArray, offset: Int, length: Int) {
        if (length <= 0) return
        ensureCapacity(length)
        buffer.copyInto(this.buffer, size, offset, offset + length)
        size += length
    }

    fun toByteArray(): ByteArray = buffer.copyOf(size)

    private fun ensureCapacity(additional: Int) {
        val needed = size + additional
        if (needed <= buffer.size) return
        var newSize = buffer.size
        while (newSize < needed) newSize *= 2
        buffer = buffer.copyOf(newSize)
    }

    override fun flush() {

    }

    override fun close() {

    }
}
