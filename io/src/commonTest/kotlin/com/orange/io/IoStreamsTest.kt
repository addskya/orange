package com.orange.io

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class IoStreamsTest {
    @Test
    fun byteArrayOutputStreamCollectsWrittenBytes() {
        val stream = ByteArrayOutputStream()

        stream.write(0x01)
        stream.write(byteArrayOf(0x02, 0x03, 0x04), offset = 1, length = 2)

        assertContentEquals(byteArrayOf(0x01, 0x03, 0x04), stream.toByteArray())
    }

    @Test
    fun reverseByteArrayOutputStreamWritesFromTail() {
        val stream = ReverseByteArrayOutputStream(4)

        stream.write(0x03)
        stream.write(byteArrayOf(0x01, 0x02), 0, 2)

        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03), stream.toByteArray())
        assertEquals(3, stream.size())
    }

    @Test
    fun timeoutInputStreamDelegatesToTimeoutCapableBase() {
        val base = FakeTimeoutInputStream(
            singleByte = 0x7F,
            chunk = byteArrayOf(0x11, 0x22)
        )
        val stream = TimeoutInputStream(base, timeoutMillis = 250)
        val buffer = ByteArray(4)

        val first = stream.read()
        val count = stream.read(buffer, 1, 2)

        assertEquals(0x7F, first)
        assertEquals(2, count)
        assertEquals(listOf(250L), base.singleReadTimeouts)
        assertEquals(listOf(250L), base.bufferReadTimeouts)
        assertContentEquals(byteArrayOf(0x00, 0x11, 0x22, 0x00), buffer)
    }

    private class FakeTimeoutInputStream(
        private val singleByte: Int,
        private val chunk: ByteArray
    ) : InputStream, TimeoutCapableInput {
        val singleReadTimeouts = mutableListOf<Long>()
        val bufferReadTimeouts = mutableListOf<Long>()

        override fun read(): Int = -1

        override fun read(buffer: ByteArray, offset: Int, length: Int): Int = -1

        override fun readWithTimeout(timeoutMillis: Long): Int {
            singleReadTimeouts += timeoutMillis
            return singleByte
        }

        override fun readWithTimeout(timeoutMillis: Long, buffer: ByteArray, offset: Int, length: Int): Int {
            bufferReadTimeouts += timeoutMillis
            val count = minOf(length, chunk.size)
            chunk.copyInto(buffer, destinationOffset = offset, endIndex = count)
            return count
        }

        override fun close() = Unit
    }
}
