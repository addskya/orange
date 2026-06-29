package com.orange.io

class SimpleDataOutput(private val output: OutputStream) : DataOutput {
    override fun write(buffer: ByteArray, offset: Int, length: Int) = output.write(buffer, offset, length)

    override fun writeByte(value: Int) = output.write(value)

    override fun flush() = output.flush()

    override fun close() = output.close()
}
