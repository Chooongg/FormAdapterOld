package com.chooongg.widget.formAdapter.option

sealed class OptionResultState {
    object NotLoading : OptionResultState()
    object Loading : OptionResultState()
    class Success(val options: List<Option>) : OptionResultState()
    class Error(e: Exception) : OptionResultState()
}