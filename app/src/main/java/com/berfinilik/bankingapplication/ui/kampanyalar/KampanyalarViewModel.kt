package com.berfinilik.bankingapplication.ui.kampanyalar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.KampanyaModel
import javax.inject.Inject

class KampanyalarViewModel @Inject constructor() : ViewModel() {

    private val _selectedKampanya = MutableLiveData<KampanyaModel>()
    val selectedKampanya: LiveData<KampanyaModel> get() = _selectedKampanya

    fun selectKampanya(kampanya: KampanyaModel) {
        _selectedKampanya.value = kampanya
    }
}