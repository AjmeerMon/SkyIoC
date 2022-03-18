package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.Lifecycle
import com.ajmeer.SkyIoC.registration.TypeRegistry
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindClassesToSelf(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        classes
            .filter { c -> !c.java.isInterface }
            .forEach { c -> typeRegistry.bind(c, c, condition, lifeCycle) }
    }
}

