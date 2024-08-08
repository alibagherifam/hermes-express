package dev.alibagherifam.hermesexpress.common.data

import dev.alibagherifam.hermesexpress.common.domain.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun getUserLocationStream(): Flow<Location?>
}
