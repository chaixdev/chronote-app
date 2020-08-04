package be.chaidev.chronote.util

import org.bitcoinj.core.Base58
import java.nio.ByteBuffer
import java.util.*

class UUIDUtil {
    fun shortUUID(): String {
        val uuid: UUID = UUID.randomUUID()
        return shortUUID(uuid)
    }

    fun shortUUID(uuid: UUID): String {
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(16)
        byteBuffer.putLong(uuid.mostSignificantBits)
        byteBuffer.putLong(uuid.leastSignificantBits)

        // Compared with Base64, the following similar-looking letters are omitted: 0 (zero), O (capital o),
        // I (capital i) and l (lower case L) as well as the non-alphanumeric characters + (plus) and / (slash).
        return Base58.encode(byteBuffer.array())
    }
}