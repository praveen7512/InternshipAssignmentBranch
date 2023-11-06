package com.example.internshipassignmentbranch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipassignmentbranch.data.model.LoginRequest
import com.example.internshipassignmentbranch.data.model.MessagesModel
import com.example.internshipassignmentbranch.data.model.ReplyMsg
import com.example.internshipassignmentbranch.data.model.TokenInfo
import com.example.internshipassignmentbranch.data.repository.MessageRepository
import com.example.internshipassignmentbranch.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val repository: MessageRepository) : ViewModel() {

    private val _messages = MutableLiveData<NetworkResult<List<MessagesModel>>>()
    val messages: LiveData<NetworkResult<List<MessagesModel>>> = _messages

    private val _loginResult = MutableLiveData<NetworkResult<TokenInfo>>()
    val loginResult: LiveData<NetworkResult<TokenInfo>> = _loginResult

    private val _replyResult = MutableLiveData<NetworkResult<MessagesModel>>()
    val replyResult: LiveData<NetworkResult<MessagesModel>> = _replyResult

    // Use this method to fetch messages
    fun fetchMessages() {
        viewModelScope.launch {
            _messages.value = NetworkResult.Loading()
            val result = repository.getMessages()
            _messages.value = result
        }
    }

    // Use this method to perform a login
    fun performLogin(loginDetails: LoginRequest) {
        viewModelScope.launch {
            _loginResult.value = NetworkResult.Loading()
            val result = repository.login(loginDetails)
            _loginResult.value = result
        }
    }

    // Use this method to reply to a message
    fun replyToMessage(reply: ReplyMsg) {
        viewModelScope.launch {
            _replyResult.value = NetworkResult.Loading()
            val result = repository.replyMessage(reply)
            _replyResult.value = result
        }
    }
}
