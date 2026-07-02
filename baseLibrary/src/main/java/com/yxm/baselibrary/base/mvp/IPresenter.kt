package com.yxm.baselibrary.base.mvp



/**

 * Created by Myron at 2021/8/22 14:50

 * @email yxmbest@163.com

 * @description Presenter lifecycle contract; wired via [BasePresenter] as [DefaultLifecycleObserver].

 */

interface IPresenter<in V : BaseView> {



    fun onCreate(view: V)



    fun onStart()



    fun onResume()



    fun onPause()



    fun onDestroy(view: V)



    fun onAnyLifeCycle()

}

