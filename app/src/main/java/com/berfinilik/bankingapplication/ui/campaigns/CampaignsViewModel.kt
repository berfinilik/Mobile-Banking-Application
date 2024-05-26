package com.berfinilik.bankingapplication.ui.campaigns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berfinilik.bankingapplication.Domain.KampanyaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignsViewModel @Inject constructor() : ViewModel() {

    private val _selectedKampanya = MutableLiveData<KampanyaModel>()
    val selectedKampanya: LiveData<KampanyaModel> get() = _selectedKampanya

    fun selectKampanya(kampanya: KampanyaModel) {
        _selectedKampanya.value = kampanya
    }
}