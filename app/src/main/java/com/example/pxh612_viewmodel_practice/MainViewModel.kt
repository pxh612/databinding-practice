package com.example.pxh612_viewmodel_practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pxh612_viewmodel_practice.Symbols.Companion.EQL
import timber.log.Timber

class MainViewModel : ViewModel() {

    private var isNicknameOnDisplay : MutableLiveData<Boolean> = MutableLiveData(false)
    private var nickname : MutableLiveData<String> = MutableLiveData()

    fun setNickname(nickname: String) {
        this.nickname.value = nickname
    }

    fun setIsNicknameOnDisplay(b: Boolean) {
        this.isNicknameOnDisplay.value = b
        Timber.d("this.isNicknameOnDisplay.value" + EQL + this.isNicknameOnDisplay.value)
    }

    fun getIsNicknameOnDisplay() : LiveData<Boolean>{
        Timber.d("this.isNicknameOnDisplay.value" + EQL + this.isNicknameOnDisplay.value)
        return isNicknameOnDisplay
    }
    fun getNickname() : LiveData<String> {
        Timber.d("nickname" + EQL + nickname.value)
        return nickname
    }
}