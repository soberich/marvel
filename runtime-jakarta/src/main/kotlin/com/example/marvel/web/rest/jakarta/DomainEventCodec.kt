package com.example.marvel.web.rest.jakarta

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
    override fun decodeFromWire(pos: Int, buffer: Buffer): Any {
        // My custom message starting from this *position* of buffer
        var _pos = pos

        // Length of JSON
        val length = buffer.getInt(_pos)

        // Get JSON string by it`s length
        // Jump 4 because getInt() == 4 bytes
        _pos += 4
        val start = _pos
        _pos += length
        val end = _pos
        val jsonStr = buffer.getString(start, end)

        // We can finally create custom message object
        return JsonObject(jsonStr).mapTo(Any::class.java)
    }
    override fun transform(o: Any): Any = o
    override fun name(): String = this.javaClass.simpleName
    override fun systemCodecID(): Byte = -1
};
