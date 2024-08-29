package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SliderViewModel: ViewModel() {

    private val _sliderValue = mutableFloatStateOf(0f)
    val sliderValue: MutableFloatState = _sliderValue

    private val _sliderStatePause = mutableStateOf(false)
    val sliderStatePause: State<Boolean> = _sliderStatePause

    suspend fun onUpdateSliderValue(){
        delay(200)
        if(sliderStatePause.value) return
        _sliderValue.floatValue += .200f
    }

    fun reset(){
        println("Reset")
        _sliderValue.floatValue = 0.000f
        _sliderStatePause.value = false
    }

    fun setPauseState(value: Boolean){
        _sliderStatePause.value = value
        println("Pause State: " + getPauseState())
    }

    fun getPauseState(): Boolean{
        return _sliderStatePause.value
    }
}