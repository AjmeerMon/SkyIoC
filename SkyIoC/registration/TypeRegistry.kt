package com.ajmeer.SkyIoC.registration

import com.ajmeer.SkyIoC.activation.MissingBindingException
import com.ajmeer.SkyIoC.registration.conditionalbinding.AlwaysMatches
import com.ajmeer.SkyIoC.registration.conditionalbinding.BindingConditions
import com.ajmeer.SkyIoC.registration.conditionalbinding.IBindingCondition
import com.ajmeer.SkyIoC.registration.packagescanning.AutoDiscovery
import com.ajmeer.SkyIoC.resolution.AutoDiscoveryResolver
import kotlin.reflect.KClass

class TypeRegistry {
    var autoDiscovery: Boolean = true

    @get:JvmName("scan")
    val scan: AutoDiscovery = AutoDiscovery(this)

    private var _autoDiscovery = AutoDiscoveryResolver()
    private var _bindings = TypeBindings()

    inline fun <reified T1 : Any, reified T2 : Any> bind(
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, T2::class, condition, lifecycle)
    }

    inline fun <reified T1 : Any> bind(
        noinline function: () -> Any,
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, function, condition, lifecycle)
    }

    inline fun <reified T1 : Any> bindSelf(
        noinline condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
        ): TypeRegistry {
        return bind(T1::class, T1::class, condition, lifecycle)
    }
    
    @JvmOverloads
    fun bind(
        from: KClass<*>,
        to: KClass<*>? = null,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        val target = to ?: from
        val ls = lifecycle ?: Lifecycle.PerRequest
        val cond = condition ?: { AlwaysMatches() }

        val binding = Binding(from, target, ls, cond(BindingConditions()))
        _bindings.add(from, binding)
        return this
    }

    @JvmOverloads
    fun bind(
        type: KClass<*>,
        function: () -> Any,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        val ls = lifecycle ?: Lifecycle.PerRequest
        val cond = condition ?: { AlwaysMatches() }

        val binding = Binding(type, function, ls, cond.invoke(BindingConditions()))
        _bindings.add(type, binding)
        return this
    }

    fun retrieveBindingFor(requestedType: KClass<*>): List<Binding> {
        if (_bindings.hasMappingFor(requestedType)) {
            return _bindings.get(requestedType)
        }

        if (autoDiscovery) {
            val type = _autoDiscovery.selectTypeFor(requestedType)
            return listOf(Binding(requestedType, type, Lifecycle.PerRequest))
        }

        throw MissingBindingException("No bindings found for: " + requestedType.qualifiedName)
    }

    // For Java
    fun bind(
        iface: java.lang.Class<*>,
        impl: java.lang.Class<*>? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(iface, impl, null, lifecycle)
    }

    @JvmOverloads
    fun bind(
        iface: java.lang.Class<*>,
        impl: java.lang.Class<*>? = null,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        val ifaceK = iface.kotlin
        var implK = impl?.kotlin

        if (!iface.isInterface) {
            implK = ifaceK
        }

        return bind(ifaceK, implK, condition, lifecycle)
    }

    fun bind(
        type: java.lang.Class<*>,
        function: () -> Any,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(type, function, null, lifecycle)
    }

    @JvmOverloads
    fun bind(
        type: java.lang.Class<*>,
        function: () -> Any,
        condition: ((op: BindingConditions) -> IBindingCondition)? = null,
        lifecycle: Lifecycle? = null
    ): TypeRegistry {
        return bind(type.kotlin, function, condition, lifecycle)
    }
    // End Java friendly overloads

}