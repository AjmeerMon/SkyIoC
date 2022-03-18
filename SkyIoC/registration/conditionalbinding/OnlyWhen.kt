package com.ajmeer.SkyIoC.registration.conditionalbinding

import com.ajmeer.SkyIoC.activation.ActivationContext

class OnlyWhen(filter: ((ctx: ActivationContext) -> Boolean)) : IBindingCondition {
    val filter = filter

    override fun matches(context : ActivationContext): Boolean {
        return filter(context)
    }
}