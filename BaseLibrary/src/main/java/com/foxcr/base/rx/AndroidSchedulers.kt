package com.foxcr.base.rx

import android.os.Handler
import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class AndroidSchedulers : Executor {
    private val mMainScheduler : Scheduler = Schedulers.from(this)
    private val mHandler: Handler = Handler(Looper.getMainLooper())

    companion object{
        private val instance by lazy {
            AndroidSchedulers()
        }

        @Synchronized
        fun mainThread():Scheduler{
            return instance.mMainScheduler
        }

    }



    override fun execute(command: Runnable) {
        mHandler.post(command)
    }

}