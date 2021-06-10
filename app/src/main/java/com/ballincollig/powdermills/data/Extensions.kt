package com.ballincollig.powdermills.data

import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.isActive

/*
This is required due to what appears to be a bug with the callback flow, more info here:
https://github.com/Kotlin/kotlinx.coroutines/issues/1762
 */
inline fun <reified E : Any> ProducerScope<E>.offerSafe(element: E) {
    if (isActive) {
        offer(element)
    }
}