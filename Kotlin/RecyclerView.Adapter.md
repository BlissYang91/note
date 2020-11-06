[TOC]
## 结合databinding，livedata
- [x] 相关依赖

```
  implementation "androidx.lifecycle:lifecycle-extensions:2.1.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0"
```
## BaseViewHolder

```
package com.beans.base.recycle

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/2 16:56
 * @Describe recycleView封装ViewHolder
 */
open class BaseViewHolder(var dataBinding: ViewDataBinding):RecyclerView.ViewHolder(dataBinding.root) {
}

```

## BaseAdapter

```

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/2 16:50
 * @Describe recycleView统配adapter
 */
abstract class BaseAdapter<T>(var data: List<T> = listOf()):RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder{
        return BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent?.context), viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun refreshData(newData: List<T>) {
        this.data = newData
        this.notifyDataSetChanged()
    }
//  增加点击和长按事件
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
//        fun onItemLongClick(view: View,position: Int): Boolean
    }

}
```

## recycleView通用adapter

```
package com.beans.server.ui.adapter

import androidx.databinding.ViewDataBinding
import com.aquila.lib.logE
import com.beans.base.recycle.BaseAdapter
import com.beans.base.recycle.BaseViewHolder
import com.beans.server.R
import com.beans.server.BR
import com.beans.server.bean.OwnerManualBean

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/2 16:59
 * @Describe recycleView通用adapter
 */
class MyAdapter<T> constructor(layout:Int) : BaseAdapter<T>() {
    var layout:Int = layout //代替下方init方法 constructor(layout:Int)为主构造函数

//    init {
//        主构造函数执行后执行
//        this.layout = layout
//    }
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        var binding : ViewDataBinding = holder.dataBinding
        binding.setVariable(BR.myData,data.get(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(holder.itemView,position)
        }
    }

    override fun getItemViewType(position: Int): Int {
          return this.layout
//        return R.layout.server_owner_dash_board_item
    }
}
```
## 使用
### 数据bean

```

import java.io.Serializable

open class BaseDto:Serializable {
}
```

```

import com.beans.base.bean.BaseDto

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/2 16:43
 * @Describe 
 */
data class OwnerBean(
    var name:String?,
    var img:Int?
):BaseDto()
```
## ViewModel

```
//此处封装与网络请求相关，暂时不用
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beans.base.net.NetService

open class BaseModel : ViewModel() {
    lateinit var owner: LifecycleOwner
    //网络请求状态数据，页面中可以监听这个数据完成状态的切换，空页面，loading 重试等等
    var status: MutableLiveData<Int> = MutableLiveData()
    protected fun <T> getService(c: Class<T>): T = NetService.getRetrofitService(c)

    //访问老接口
    protected fun <T> getOldService(c: Class<T>): T = NetService.getOldRetrofitService(c)

}
```

```

import androidx.lifecycle.MutableLiveData
import com.beans.server.R
import com.beans.server.bean.OwnerDashBoardBean
import com.beans.server.bean.OwnerManualBean
import com.mobile.base.BaseModel

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/2 15:23
 * @Describe 通配相关vm，用于获取数据并将数据赋值给livedata用于监听数据改变通知UI进行更新 ，暂时与网络请求无关，所以不继承BaseModel而是继承ViewModel
 */
class OwnerManualModel :ViewModel(){
    //手册详情数据
    var listDashBoards = MutableLiveData<List<OwnerDashBoardBean>>()
    //仪表盘数据
    var listManuals = MutableLiveData<List<OwnerManualBean>>()

    //获取手册集
    fun getDashBoardData(){
        var data = listOf(
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board),
            OwnerDashBoardBean("转向指示灯","转向灯工作时，相应的转向指示灯闪烁。按下危险警告按钮时，转向指示灯及车外所有的转向灯将一起闪烁",R.mipmap.server_dash_board)
        )
        listDashBoards.value = data
    }



    //获取手册集
    fun getManualsData(){
        var data = listOf(
            OwnerManualBean("仪表盘",R.mipmap.server_dash_board),
            OwnerManualBean("车型说明",R.mipmap.server_dash_board),
            OwnerManualBean("书签",R.mipmap.server_book_mark),
            OwnerManualBean("快速入门",R.mipmap.server_dash_board)
        )
        listManuals.value = data
    }
}
```


### 绑定item_layout布局

```
<?xml version="1.0" encoding="utf-8"?>

<layout>
    <data>
        <import type="com.beans.server.bean.OwnerManualBean"></import>
        <import type="com.beans.server.R"></import>
        <variable
            name="myData"
            type="OwnerlBean" />
        <variable
            name="listener"
            type="android.view.View.OnClickListener" />

    </data>
    <RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="112dp"
        android:gravity="center_vertical"
        android:background="@color/white">
        <ImageView
            android:id="@+id/iv_manual"
            android:layout_width="80dp"
            android:layout_height="match_parent"
            android:src="@{myData.img}"
            android:layout_marginLeft="16dp"
            android:scaleType="centerInside"></ImageView>
        <TextView
            android:id="@+id/tv_manual"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_marginLeft="12dp"
            android:text="@{myData.name}"
            android:gravity="center_vertical"
            android:textColor="@color/text_item_center_color"
            android:textSize="14sp"
            android:layout_toRightOf="@id/iv_manual"></TextView>
        <ImageView
            android:id="@+id/iv_into"
            android:layout_width="24dp"
            android:layout_height="match_parent"
            android:src="@mipmap/right_into_icon"
            android:scaleType="center"
            android:layout_marginRight="16dp"
            android:layout_alignParentRight="true"></ImageView>
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:layout_marginLeft="108dp"
            android:layout_alignParentBottom="true"
            android:background="@color/color_divide_line"></View>
    </RelativeLayout>

</layout>
```
### activity中使用

```
    lateinit var ownerManualModel:OwnerManualModel
    private var mAdapter : MyAdapter<OwnerBean>? =null
    
    
    
    、、、
       ownerManualModel = ViewModelProviders.of(this).get(OwnerManualModel::class.java)
        ownerManualModel.getManualsData()
        val layoutManager = LinearLayoutManager(this)
        mBinding.recyclerManual.layoutManager = layoutManager
        mAdapter = MyAdapter(R.layout.server_owner_manule_item)
        mBinding.recyclerManual.adapter = mAdapter
        mAdapter?.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                ToastUtils.s(this@ServerOwnerManualActivity,"点击 ${position}")
          
            }
        })
        
        
          ownerManualModel.listManuals.observe(this,
            Observer {
        //此处更新UI，将数据赋值并更新adapter
                mAdapter!!.refreshData(it)
                mAdapter?.notifyDataSetChanged()
            })
```












