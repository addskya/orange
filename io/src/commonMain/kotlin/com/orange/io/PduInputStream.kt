package com.orange.io

class PduInputStream(private val base: InputStream) : InputStream {
    override fun read(): Int {
        val b = base.read()
        if (b >= 0) {
            println("R:${toHex(byteArrayOf(b.toByte()))}")
        }
        return b
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        val n = base.read(buffer, offset, length)
        if (n > 0) {
            println("R:${toHex(buffer.copyOfRange(offset, offset + n))}")
        }
        return n
    }

    override fun available(): Int = base.available()

    override fun close() = base.close()

    private fun toHex(bytes: ByteArray): String {
        if (bytes.isEmpty()) return ""
        val sb = StringBuilder(bytes.size * 3)
        for (b in bytes) {
            val v = b.toInt() and 0xFF
            sb.append(HEX[v ushr 4]).append(HEX[v and 0x0F])
        }
        return sb.toString()
    }

    private companion object {
        private val HEX = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        )
    }
}

