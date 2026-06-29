package com.orange.io

class DelayedOutputStream(
    private val base: OutputStream,
    private val delayedMillis: Long
) : OutputStream {
    override fun flush() {
        base.flush()
    }

    override fun write(b: Int) = base.write(b)

    override fun write(buffer: ByteArray, offset: Int, length: Int) = base.write(buffer, offset, length)

    override fun close() = base.close()
}

