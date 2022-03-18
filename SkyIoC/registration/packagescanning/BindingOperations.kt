package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.Lifecycle
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition

class BindingOperations {
    @JvmOverloads
    fun bindAllInterfaces(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindAllInterfaces(condition, lifecycle)
    }

    @JvmOverloads
    fun bindClassesToSelf(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindClassesToSelf(condition, lifecycle)
    }

    @JvmOverloads
    fun bindClassesAndInterfaces(
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): IBindingStrategy {
        return BindClassesAndInterfaces(condition, lifecycle)
    }
}