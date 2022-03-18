package com.ajmeer.SkyIoC.registration.packagescanning

import com.ajmeer.SkyIoC.registration.TypeRegistry
import kotlin.reflect.KClass

interface IBindingStrategy {
    fun bind(typeRegistry: TypeRegistry, classes : List<KClass<*>>)
}