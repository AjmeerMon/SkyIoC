package com.ajmeer.SkyIoC.registration.conditionalbinding

import com.ajmeer.SkyIoC.activation.ActivationContext

class AlwaysMatches : IBindingCondition {
    override fun matches(context : ActivationContext) : Boolean {
        return true
    }
}