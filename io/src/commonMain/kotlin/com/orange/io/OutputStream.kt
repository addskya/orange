package com.orange.io

interface OutputStream : Closeable {
    fun write(b: Int)
    fun write(buffer: ByteArray, offset: Int = 0, length: Int = buffer.size)
    fun flush()
}