package com.example.pxh612_viewmodel_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pxh612_viewmodel_practice.Symbols.Companion.EQL

import com.example.pxh612_viewmodel_practice.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    enum class BUTTON_CLICK{
        DONE,
        NICKNAME,
        DEBUG
    }

    private lateinit var binding: ActivityMainBinding
    var mainViewModel : MainViewModel = MainViewModel()
    var keyboardVisibility : KeyboardVisibility = KeyboardVisibility(this)
    var myName : MyName = MyName(MY_NAME)


    var nickname : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Timber.plant(pxh612DebugTree())
        Timber.v("onCreate...")

        onCreateUI()

        observeIsNicknameOnDisplay()
    }



    private fun onCreateUI() {

        binding.myName = myName

        binding.doneButton.setOnClickListener() { onButtonClick(BUTTON_CLICK.DONE, it) }
        binding.nicknameText.setOnClickListener(){ onButtonClick(BUTTON_CLICK.NICKNAME, it) }
        binding.debug.setOnClickListener(){ onButtonClick(BUTTON_CLICK.DEBUG, it) }

        binding.debug.visibility = View.GONE

    }
    fun onButtonClick(buttonClick : BUTTON_CLICK, it : View){
        when(buttonClick){
            BUTTON_CLICK.DONE -> {
                Timber.v("doneButton clicked...")
                keyboardVisibility.hideKeyboard(it)
                if(updateNicknameIsSuccessful()){
                    Timber.d("update nickname successful...")
                    mainViewModel.setIsNicknameOnDisplay(true)
                }
                else {
                    Toast.makeText(this, EmptyNicknameNotification, Toast.LENGTH_SHORT).show();
                }
            }
            BUTTON_CLICK.NICKNAME -> {
                mainViewModel.setIsNicknameOnDisplay(false)
            }
            BUTTON_CLICK.DEBUG -> {
                Timber.d("debug button clicked")
                observeIsNicknameOnDisplay()
            }
        }
    }


    private fun observeIsNicknameOnDisplay() {
        mainViewModel.getIsNicknameOnDisplay().observe(this, Observer { isNicknameOnDisplay ->

            if (isNicknameOnDisplay == true) {
                setVisibilityInputField(View.GONE)
                setVisibilityNicknameText(View.VISIBLE)
            } else {
                setVisibilityInputField(View.VISIBLE)
                setVisibilityNicknameText(View.GONE)
            }
        })
    }
    private fun observeNickname() {
        mainViewModel.getNickname().observe(this, Observer { nickname ->
            Timber.d("nickname" + EQL + nickname)
        })
    }




    private fun updateNicknameIsSuccessful() : Boolean {
        nickname = binding.nicknameEdit.text.toString()
        Timber.i("nickname" + EQL + nickname)

        if(nickname.equals("")) return false;
        else {
            binding.apply {
                myName?.nickname = nickname as String
                mainViewModel.setNickname(nickname!!)
                invalidateAll()
            }
            return true
        }
    }
    private fun setVisibilityNicknameText(VisibilityState: Int) {
        binding.nicknameText.visibility = VisibilityState
    }
    fun setVisibilityInputField(VisibilityState : Int){
        binding.doneButton.visibility = VisibilityState
        binding.nicknameEdit.visibility = VisibilityState
    }


    companion object {
        private const val EmptyNicknameNotification = "Your nickname is empty!"
        private const val MY_NAME = "Pham Xuan Huy"
    }
}