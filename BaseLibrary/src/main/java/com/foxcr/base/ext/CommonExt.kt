package com.foxcr.base.ext
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.foxcr.base.common.DefaultTextWatcher
import com.foxcr.base.data.protocal.BaseNoneResponseResult
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

/**
 * @buttonColors 0 button可点击的颜色  1 button不可点击的颜色
 */
fun Button.enable(mEtn:EditText,buttonColors:IntArray,method:()->Boolean){
    val mBtn = this
    mEtn.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            mBtn.isEnabled = method()
            if (method()){
                mBtn.setBackgroundResource(buttonColors[0])
            }else{
                mBtn.setBackgroundResource(buttonColors[1])
            }

        }
    })
}

/**
 * 隐藏软件盘
 */

fun EditText.hideKeyboard(){
    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(this.windowToken,0)

}

fun View.hideKeyboard(){
    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(this.windowToken,0)
}

