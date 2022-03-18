package com.ajmeer.SkyIoC.resolution

import com.ajmeer.SkyIoC.resolution.autoresolution.IAutoResolutionStrategy
import com.ajmeer.SkyIoC.resolution.autoresolution.ResolveConcreteTypeToSelfStrategy
import com.ajmeer.SkyIoC.resolution.autoresolution.ResolveFooToFooImplStrategy
import com.ajmeer.SkyIoC.resolution.autoresolution.ResolveIFooToFooStrategy
import kotlin.reflect.KClass

class AutoDiscoveryResolver {
    var strategies: MutableList<IAutoResolutionStrategy> = mutableListOf(
        ResolveConcreteTypeToSelfStrategy(),
        ResolveIFooToFooStrategy(),
        ResolveFooToFooImplStrategy()
    )

    fun selectTypeFor(requestedType: KClass<*>): KClass<*> {
        for (stat in strategies) {
            var match = stat.discover(requestedType)
            if (match != null) {
                return match
            }
        }
        return requestedType
    }
}

