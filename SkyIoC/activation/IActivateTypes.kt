package com.ajmeer.SkyIoC.activation

import com.ajmeer.SkyIoC.registration.Binding

interface IActivateTypes {
    fun create(
        bindings: List<Binding>,
        activationContext: ActivationContext
    ): Any
}