package com.xxxifan.motorcade.ui.map

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.xxxifan.devbox.core.base.BasePresenter
import com.xxxifan.devbox.core.base.BaseView

interface AmapContract {
  interface View : BaseView {

  }

  interface Presenter : BasePresenter<View>, LifecycleObserver {
    @OnLifecycleEvent(ON_PAUSE)
    fun onLifeCyclePaused(owner: LifecycleOwner)

    @OnLifecycleEvent(ON_RESUME)
    fun onLifeCycleResumed(owner: LifecycleOwner)

    @OnLifecycleEvent(ON_DESTROY)
    fun onLifeCycleDestroyed(owner: LifecycleOwner)

    fun enableLocation()
    fun startLocation()
  }
}