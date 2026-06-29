package com.orange.io

class PduOutputStream(private val base: OutputStream) : OutputStream {
    private val buffer = ByteArray(MAX_PAYLOAD_SIZE)
    private var size = 0

    override fun write(b: Int) {
        buffer[size] = b.toByte()
        size++
        if (size == MAX_PAYLOAD_SIZE) {
            flush()
        }
    }

    override fun write(buffer: ByteArray, offset: Int, length: Int) {
        var remaining = length
        var index = offset
        while (remaining > 0) {
            val canWrite = minOf(remaining, MAX_PAYLOAD_SIZE - size)
            buffer.copyInto(this.buffer, destinationOffset = size, startIndex = index, endIndex = index + canWrite)
            size += canWrite
            index += canWrite
            remaining -= canWrite
            if (size == MAX_PAYLOAD_SIZE) {
                flush()
            }
        }
    }

    override fun flush() {
        if (size == 0) return
        val data = buffer.copyOfRange(0, size)
        size = 0
        println("W:${toHex(data)}")
        base.write(data, 0, data.size)
        base.flush()
    }

    override fun close() {
        flush()
        base.close()
    }

    private fun toHex(bytes: ByteArray): String {
        if (bytes.isEmpty()) return ""
        val sb = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            val v = b.toInt() and 0xFF
            sb.append(HEX[v ushr 4]).append(HEX[v and 0x0F])
        }
        return sb.toString()
    }

    private companion object {
        const val MAX_PAYLOAD_SIZE: Int = 1024
        private val HEX = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        )
    }
}

