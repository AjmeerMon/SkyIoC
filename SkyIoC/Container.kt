package com.ajmeer.SkyIoC

import com.ajmeer.SkyIoC.activation.ActivationContext
import com.ajmeer.SkyIoC.activation.IActivateTypes
import com.ajmeer.SkyIoC.activation.LifeCycleManagingTypeActivator
import com.ajmeer.SkyIoC.activation.TypeActivator
import com.ajmeer.SkyIoC.registration.TypeRegistry
import kotlin.reflect.KClass

class Container {

    @get:JvmName("registrations")
    val registrations : TypeRegistry
    private val creator : IActivateTypes

    constructor () : this(TypeRegistry())
    constructor (typeRegistry : TypeRegistry) {
        registrations = typeRegistry
        creator = LifeCycleManagingTypeActivator(TypeActivator(registrations))
    }

    inline fun <reified T: Any> resolve(): T {
        return resolve(T::class) as T
    }

    fun resolve(requestedType: KClass<*>): Any {
        val bindings = registrations.retrieveBindingFor(requestedType)
        val activationContext = ActivationContext(requestedType)

        try {
            return creator.create(bindings, activationContext)
        } catch (ex : StackOverflowError){
            throw CircularDependencyException(ex)
        }
    }

    // For Java
    fun resolve(requestedType: java.lang.Class<*>) : Any {
        return resolve(requestedType.kotlin)
    }
    // For Java
}

