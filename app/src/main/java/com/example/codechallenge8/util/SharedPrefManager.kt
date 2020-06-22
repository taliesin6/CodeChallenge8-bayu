package com.example.codechallenge8.util

import android.content.Context
import com.example.codechallenge8.model.Data
import com.example.codechallenge8.model.DataLogin
import com.example.codechallenge8.model.UsersResponse

class SharedPrefManager(private val mContext: Context) {
    companion object {
        private const val SHARED_PREF_NAME = "code"
        private var mInstance: SharedPrefManager? = null

        fun getInstance(mContext: Context): SharedPrefManager {
            if (mInstance == null){
                mInstance = SharedPrefManager(mContext)
            }
            return mInstance as SharedPrefManager
        }
    }

    val user: Data
    get() {
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return Data(
            sharedPreferences.getString("id", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("username", null)
        )
    }

    fun saveUser(user: DataLogin){
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("id", user.id)
        editor.putString("username", user.username)
        editor.putString("email", user.email)
        editor.putString("token", user.token)
        editor.apply()
    }

    val putUser: DataLogin
    get() {
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return DataLogin(
            sharedPreferences.getString("id", null),
            sharedPreferences.getString("username", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("token", null)
        )
    }

    fun updateUser(user: UsersResponse){
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor =  sharedPreferences.edit()

        editor.putString("id", user.data?.id)
        editor.putString("email", user.data?.email)
        editor.putString("username", user.data?.username)
        editor.putString("photo", user.data?.photo)
        editor.apply()
    }

    fun clear(){
        val sharedPrefManager = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefManager.edit()
        editor.clear()
        editor.apply()
    }
}