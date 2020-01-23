package com.foxcr.base.ext
import android.view.View
import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.rx.BaseException
import com.foxcr.base.rx.BaseSubscriber
import io.reactivex.Observable

fun View.onClick(method:()->Unit){
    this.setOnClickListener { method() }
}

fun Throwable.netException(mView:BaseView){
    if (this is BaseException){
        mView.onError(this.errorMsg)
    }else{
        mView.onError(this.message.toString())
    }
}

fun <T>Observable<BaseResp<T>>.convert():Observable<T>{
    return this.flatMap(BaseSubscriber())
}
