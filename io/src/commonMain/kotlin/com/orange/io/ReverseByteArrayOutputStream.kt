package com.orange.io

class ReverseByteArrayOutputStream : OutputStream {
    private val automaticResize: Boolean
    var buffer: ByteArray
    var index: Int

    constructor(bufferSize: Int) : this(bufferSize, false)

    constructor(bufferSize: Int, automaticResize: Boolean) : this(ByteArray(bufferSize), bufferSize - 1, automaticResize)

    constructor(buffer: ByteArray) : this(buffer, buffer.size - 1, false)

    constructor(buffer: ByteArray, startingIndex: Int) : this(buffer, startingIndex, false)

    constructor(buffer: ByteArray, startingIndex: Int, automaticResize: Boolean) {
        require(buffer.isNotEmpty()) { "buffer size may not be <= 0" }
        this.buffer = buffer
        index = startingIndex
        this.automaticResize = automaticResize
    }

    override fun write(b: Int) {
        write(b.toByte())
    }

    fun writeByte(value: Int) {
        write(value)
    }

    fun writeBytes(buffer: ByteArray) {
        write(buffer)
    }

    fun writeBytes(buffer: ByteArray, offset: Int, length: Int) {
        write(buffer, offset, length)
    }

    fun write(b: Byte) {
        if (index < 0 || index >= buffer.size) {
            if (automaticResize) {
                resize()
            } else {
                throw IndexOutOfBoundsException("buffer.length = ${buffer.size}")
            }
        }
        buffer[index] = b
        index--
    }

    fun write(buffer: ByteArray) {
        write(buffer, 0, buffer.size)
    }

    override fun write(buffer: ByteArray, offset: Int, length: Int) {
        if (length <= 0) return
        while (index + 1 - length < 0) {
            if (automaticResize) {
                resize()
            } else {
                throw IndexOutOfBoundsException("buffer.length = ${buffer.size}")
            }
        }
        buffer.copyInto(this.buffer, index - length + 1, offset, offset + length)
        index -= length
    }

    override fun flush() {
    }

    private fun resize() {
        val newBuffer = ByteArray(buffer.size * 2)
        val used = buffer.size - index - 1
        buffer.copyInto(newBuffer, newBuffer.size - used, index + 1, buffer.size)
        index += buffer.size
        buffer = newBuffer
    }

    fun getArray(): ByteArray {
        if (index == -1) return buffer
        val length = buffer.size - index - 1
        return buffer.copyOfRange(index + 1, index + 1 + length)
    }

    fun toByteArray(): ByteArray = getArray()

    fun size(): Int = buffer.size - index - 1

    fun reset() {
        index = buffer.size - 1
    }

    override fun close() {
    }
}
