package com.example.bahdtech.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.bahdtech.api.GithubService
import com.example.bahdtech.api.GithubUser
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: GithubService
) : ViewModel(), UserAdapter.DataProvider<GithubUser> {

    sealed class LoadNotify {
        object Refresh : LoadNotify()
        class LoadMore(val startPosition: Int, val size: Int) : LoadNotify()
    }

    private val data = arrayListOf<GithubUser>()
    private val usersLiveData = MutableLiveData<LoadNotify>()

    val users = liveData<LoadNotify> {
        emit(LoadNotify.Refresh)
        // I believe this method should NOT be a suspend call
        // Since it doesn't really do anything suspendable besides just dispatching to
        // the internal LiveData thread.
        // This could easily be achieved with a launch or nothing in fact!
        // Also mind you, a suspend function should be such that there are no side effects
        // and all execution should be finished the moment it returns.
        // This isn't doing that, which makes such behavior quite surprising!

        // However, if it would really suspend, it should do so until all value
        // in the source LiveData is fully consumed, then the behavior would be
        // consistent and similar to how Flow#emitAll and/or Channel#toChannel works

        // Or perhaps, I'm just being paranoid for no reason, and all of this is fine! :D
        emitSource(usersLiveData)
        refresh()
    }

    fun refresh() {
        Log.i("MainViewModel", "refresh")
        viewModelScope.launch {
            val users = service.listUsers(0)
            data.clear()
            data.addAll(users)
            usersLiveData.value = LoadNotify.Refresh
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val cursor = data.lastOrNull()?.id ?: 0
            val users = service.listUsers(cursor)
            val initialSize = data.size
            data.addAll(users)
            usersLiveData.value = LoadNotify.LoadMore(initialSize, users.size)
        }
    }

    override fun itemAt(position: Int): GithubUser {
        return data[position]
    }

    override fun size(): Int {
        return data.size
    }
}
