package com.foxcr.base.rx

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.data.protocal.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

class BaseSubscriber<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.errorCode != 0 || t.errorCode == -1001 || t.errorCode == -1) {
            return Observable.error(BaseException(t.errorCode, t.errorMsg ?: ""))
        }
        if (t.data == null){
            return Observable.just(BaseNoneResponseResult() as T)
        }
        return Observable.just(t.data)
    }
}