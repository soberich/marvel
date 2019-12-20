package com.example.marvel

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(vararg args: String) {
        Micronaut.run(Application::class.java)
    }
}
