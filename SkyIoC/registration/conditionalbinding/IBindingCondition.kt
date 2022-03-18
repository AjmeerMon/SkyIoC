package com.ajmeer.SkyIoC.registration.conditionalbinding

import com.ajmeer.SkyIoC.activation.ActivationContext

interface IBindingCondition {
    fun matches(context : ActivationContext) : Boolean
}

