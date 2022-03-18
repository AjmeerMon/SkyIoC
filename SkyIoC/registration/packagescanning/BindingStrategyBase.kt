package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.Lifecycle
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition

abstract class BindingStrategyBase(
    val condition: ((op: BindingConditions) -> IBindingCondition)? = null,
    lifecycle: Lifecycle? = null
) : IBindingStrategy {
    val lifeCycle : Lifecycle? = lifecycle
}