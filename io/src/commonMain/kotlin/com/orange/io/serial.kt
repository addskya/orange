package com.orange.io


enum class DataBits(val bits: Int) {
    DATABITS_7(7),
    DATABITS_8(8)
}

enum class StopBits(val bits: Int) {
    STOPBITS_1(1),
    STOPBITS_2(2)
}

enum class Parity {
    NONE,
    EVEN,
    ODD
}