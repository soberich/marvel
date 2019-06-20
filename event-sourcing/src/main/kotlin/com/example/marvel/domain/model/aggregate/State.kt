package com.example.marvel.domain.model.aggregate

import java.util.EnumSet


enum class State(val valideTransitions : EnumSet<State>) {
    INSURED_SUB    (EnumSet.      noneOf(State::class.java)),
    INSURANCE_SUB  (EnumSet.          of(State.INSURED_SUB)),
    INSURANCE_MAIN (EnumSet.          of(State.INSURANCE_SUB)),
    INSURED_MAIN   (EnumSet.      noneOf(State::class.java)),
    AON            (EnumSet.      noneOf(State::class.java)),
    GUEST          (EnumSet.complementOf(EnumSet.of(State.AON)));
}
