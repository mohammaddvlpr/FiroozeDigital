package com.firooze.digital.screen.newsDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firooze.digital.navigation.ID
import com.firooze.digital.screen.newsDetail.models.NewsDetailScreenMapper
import com.firooze.digital.screen.newsDetail.models.NewsDetailScreenState
import com.firooze.domain.news.useCase.GetNewsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val mapper: NewsDetailScreenMapper
) :
    ViewModel() {
    private val _state = MutableStateFlow(NewsDetailScreenState())
    val state: StateFlow<NewsDetailScreenState> = _state

    private val id: String? = savedStateHandle[ID]

    init {
        viewModelScope.launch {
            if (id != null) {
                val result = getNewsByIdUseCase(id)
                val resultValue = result.getOrNull()

                if (resultValue != null)
                    _state.update {
                        it.copy(
                            newsDetailUiModel = mapper.mapNewModelToNewsDetailUiModel(
                                resultValue
                            )
                        )
                    }
            }


        }
    }


}