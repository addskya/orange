package com.orange.io

class SimpleDataInput(private val input: InputStream) : DataInput {
    override fun read(buffer: ByteArray, offset: Int, length: Int): Int = input.read(buffer, offset, length)

    override fun readByte(): Byte {
        val b = input.read()
        if (b < 0) {
            throw IllegalStateException("Unexpected end of stream.")
        }
        return b.toByte()
    }

    override fun readUnsignedByte(): Int {
        val b = input.read()
        if (b < 0) {
            throw IllegalStateException("Unexpected end of stream.")
        }
        return b and 0xFF
    }

    override fun available(): Int = input.available()

    override fun close() = input.close()
}