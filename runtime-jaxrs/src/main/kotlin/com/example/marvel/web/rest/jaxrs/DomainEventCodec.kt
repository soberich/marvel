package com.example.marvel.web.rest.jaxrs

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.JsonObject

class DomainEventCodec : MessageCodec<Any, Any> {
    override fun encodeToWire(buffer: Buffer, o: Any) {
        // Encode object to string
        val jsonToStr = JsonObject.mapFrom(o).encode()

        // Length of JSON: is NOT characters count
        val length = jsonToStr.toByteArray().size

        // Write data into given buffer
        buffer.appendInt(length)
              .appendString(jsonToStr)
    }

    /**
     * Get JSON string by it`s length
     * Jump 4 because getInt() == 4 bytes
     * @param pos Custom message starting from this *position* of buffer
     */
    override fun decodeFromWire(pos: Int, buffer: Buffer): Any =
            JsonObject(buffer.getString(pos + 4, pos + 4 + buffer.getInt(pos))).mapTo(Any::class.java)
    override fun transform(o: Any): Any = o
    override fun name(): String = this::class.java.simpleName
    override fun systemCodecID(): Byte = -1
};
