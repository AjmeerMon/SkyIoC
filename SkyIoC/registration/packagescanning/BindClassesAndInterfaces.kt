package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.Lifecycle
import com.ajmeer.SkyIoC.registration.TypeRegistry
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition
import kotlin.reflect.KClass

class BindClassesAndInterfaces(condition: ((op: BindingConditions) -> IBindingCondition)?, lifecycle: Lifecycle?) :
    BindingStrategyBase(condition, lifecycle) {
    private var _classes: BindClassesToSelf = BindClassesToSelf(condition, lifecycle)
    private var _interfaces: BindAllInterfaces = BindAllInterfaces(condition, lifecycle)

    override fun bind(typeRegistry: TypeRegistry, classes: List<KClass<*>>) {
        _classes.bind(typeRegistry, classes)
        _interfaces.bind(typeRegistry, classes)
    }
}