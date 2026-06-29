package com.orange.io

interface DataInput : Closeable {
    fun read(buffer: ByteArray, offset: Int, length: Int): Int
    fun readByte(): Byte
    fun readUnsignedByte(): Int
    fun available(): Int = 0
}