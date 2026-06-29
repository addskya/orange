package com.orange.io

interface DataOutput : Closeable {
    fun write(buffer: ByteArray, offset: Int, length: Int)
    fun writeByte(value: Int)
    fun flush()
}