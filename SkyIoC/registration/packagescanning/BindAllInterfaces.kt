package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.Lifecycle
import com.ajmeer.SkyIoC.registration.TypeRegistry
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindAllInterfaces(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        classes
            .filter { x -> !x.java.isInterface }
            .forEach { c -> c.java.interfaces.forEach { i -> typeRegistry.bind(i.kotlin, c, condition, lifeCycle) }}
    }
}