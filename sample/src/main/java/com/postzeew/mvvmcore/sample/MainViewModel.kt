package com.postzeew.mvvmcore.sample

import com.postzeew.mvvmcore.BaseViewModel
import com.postzeew.mvvmcore.BaseViewModelImpl

interface MainViewModel : BaseViewModel

class MainViewModelImpl : BaseViewModelImpl(), MainViewModel