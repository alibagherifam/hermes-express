package dev.alibagherifam.hermesexpress.fakeoffer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dev.alibagherifam.hermesexpress.common.domain.Constants
import dev.alibagherifam.hermesexpress.fakeoffer.data.FakeOrderOfferingService
import dev.alibagherifam.hermesexpress.fakeoffer.data.OrderDto
import dev.alibagherifam.hermesexpress.fakeoffer.data.broadcastFakeOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FakeOrderOfferViewModel(
    private val cloudMessaging: FirebaseMessaging,
    private val fakeOrderOfferingService: FakeOrderOfferingService
) : ViewModel() {
    private val _uiState = MutableStateFlow(FakeOrderOfferUiState())
    val uiState: StateFlow<FakeOrderOfferUiState> get() = _uiState

    fun offerFakeOrderToBikers() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isOfferingInProgress = true)
            }
            cloudMessaging.subscribeToTopic(Constants.TOPIC_BIKERS).await()
            fakeOrderOfferingService.broadcastFakeOrder(
                OrderDto("Kotlin Conf 2023")
            )
            _uiState.update {
                it.copy(isOfferingInProgress = false)
            }
        }
    }
}
