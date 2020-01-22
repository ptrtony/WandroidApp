package com.foxcr.base.ext
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

//fun <T> Observable<T>.execute(baseSubscribe:BaseSubscr<T>,lifecycleProvider: LifecycleProvider<*>){
//    this.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .compose(lifecycleProvider.bindToLifecycle())
//        .subscribe(baseSubscribe)
//
//}