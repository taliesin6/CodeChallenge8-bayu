package com.example.codechallenge8.util

import com.example.codechallenge8.ui.login.LoginPresenter
import com.example.codechallenge8.ui.battle.BattlePresenter
import com.example.codechallenge8.ui.history.HistoryPresenter
import com.example.codechallenge8.ui.profile.ProfilePresenter
import com.example.codechallenge8.ui.register.RegisterPresenter
import com.example.codechallenge8.ui.splash.AuthPresenter
import org.koin.dsl.module

val myModule = module {
    factory { RegisterPresenter(get()) }
    factory { LoginPresenter(get()) }
    factory { ProfilePresenter(get()) }
    factory { HistoryPresenter(get()) }
    factory { BattlePresenter(get()) }
    factory { AuthPresenter(get()) }
}