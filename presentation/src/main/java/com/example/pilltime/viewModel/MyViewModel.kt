package com.example.pilltime.viewModel

import android.util.Log
import com.example.baseProjectCA.base.BaseViewModel
import com.example.domain.model.local.NoticeData
import com.example.domain.usecase.room.DeleteNoticeDataUseCase
import com.example.domain.usecase.room.GetNoticeDataListUseCase
import com.example.domain.usecase.room.InsertNoticeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * 2023-11-07
 * pureum
 */

@HiltViewModel
class MyViewModel @Inject constructor(
    private val insertNoticeUseCase: InsertNoticeDataUseCase,
    private val getNoticeListUseCase: GetNoticeDataListUseCase,
    private val deleteNoticeUseCase: DeleteNoticeDataUseCase
): BaseViewModel() {
    init {
        getNoticeList()
    }

    private val _uiState = MutableStateFlow(listOf(NoticeData()))
    val uiState: StateFlow<List<NoticeData>> = _uiState.asStateFlow()

    fun getNoticeList(){
        ioScope.launch {
            Log.e("TAG", "getNoticeList: 눌렀어", )
            _uiState.value = getNoticeListUseCase()
            Log.e("TAG", "getNoticeList: ${_uiState.value}", )
        }
    }

    fun addNotice(){
        ioScope.launch {
            Log.e("TAG", "addNotice: 눌렀어", )
            insertNoticeUseCase(
                NoticeData(
                    submitTime = "${LocalDateTime.now()}",
                    time = "12:18",
                    month = "11",
                    day = "08",
                    isChecked = false,
                    pills = "감기약"
                )
            )
            getNoticeList()
        }
    }

    fun deleteNotice(submitTime: String){
        ioScope.launch {
            Log.e("TAG", "deleteNotice: 삭제 $submitTime", )
            deleteNoticeUseCase(submitTime)
            getNoticeList()
        }
    }


}