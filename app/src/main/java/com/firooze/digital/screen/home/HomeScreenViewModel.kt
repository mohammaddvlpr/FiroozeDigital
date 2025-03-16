package com.firooze.digital.screen.home

import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.firooze.digital.provider.dateTime.DateTimeProvider
import com.firooze.digital.screen.home.models.HomeScreenState
import com.firooze.digital.screen.home.models.HomeScreenUiMapper
import com.firooze.domain.news.useCase.GetAllNewsPagingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getAllNewsPagingFlowUseCase: GetAllNewsPagingFlowUseCase,
    private val homeScreenUiMapper: HomeScreenUiMapper,
    private val dateTimeProvider: DateTimeProvider
) :
    ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state


    val pagingFlow = getAllNewsPagingFlowUseCase().map { pagingData ->
        pagingData.map { newsModel ->
            homeScreenUiMapper.mapDomainToUi(newsModel)
        }

    }

    fun onSnackBarShowed() {
        _state.update { it.copy(message = 0) }
    }

}