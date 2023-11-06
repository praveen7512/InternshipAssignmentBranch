package com.example.internshipassignmentbranch.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.internshipassignmentbranch.data.model.MessagesModel

class SharedViewModel : ViewModel() {
    var chatDetails by mutableStateOf<MessagesModel?>(null)
        private set
    fun setMessageDetail(messagesModel: MessagesModel){
        chatDetails = messagesModel
    }
}