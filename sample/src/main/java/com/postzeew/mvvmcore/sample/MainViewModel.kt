package com.postzeew.mvvmcore.sample

import com.postzeew.mvvmcore.BaseViewModel
import com.postzeew.mvvmcore.BaseViewModelImpl
import javax.inject.Inject

interface MainViewModel : BaseViewModel

class MainViewModelImpl @Inject constructor() : BaseViewModelImpl(), MainViewModel