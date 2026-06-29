package com.orange.io

interface InputStream : Closeable {
    fun read(): Int
    fun read(buffer: ByteArray, offset: Int, length: Int): Int
    fun available(): Int = 0
}