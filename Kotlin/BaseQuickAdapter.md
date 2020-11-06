[TOC]
## 相关依赖

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```
dependencies {
    implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.1'
}
```
[官网链接](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/0-BaseRecyclerViewAdapterHelper.md)
> 使用androidx扩展包下的recycleview

```

                   <androidx.recyclerview.widget.RecyclerView
                        android:id="@+id/recycler_view"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content">
                    </androidx.recyclerview.widget.RecyclerView>
```



## BaseQuickAdapter

```
class MYAdapter(
    layoutId:Int,
    data:MutableList<Bean>? = null
): BaseQuickAdapter<Bean, BaseViewHolder>(layoutId,data){
    override fun convert(helper: BaseViewHolder, item: Bean?) {
        item?.let {
            helper.setText(R.id.tv_start_time,item?.startTime)
                  .setText(R.id.tv_week_day,item?.weekDay)
        }
    }

}
```
## adapter初始化

```
    private val myAdapter by lazy {
    
        var bean1 = StartTimeBean("07:45","星期一",false)
        var bean2 = StartTimeBean("09:45","星期二",false)
        var bean3 = StartTimeBean("08:45","星期三",true)
      
        carList.add(bean1)
        carList.add(bean2)
        carList.add(bean3)
   
        StartTimeAdapter(R.layout.item)
    }

```

## 给子item添加点击事件
 
```
 var carList = mutableListOf<Bean>()
 mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = myAdapter
        myAdapter?.setNewData(carList)
        myAdapter.addChildClickViewIds(R.id.switch_start)
        myAdapter.setOnItemChildClickListener {
                adapter, view, position ->
                    if (view.id == R.id.switch_start){
                        var switch_start:Switch = view as Switch
                        if (switch_start.isChecked){
                            ToastUtils.s(application,"选中$position")
                        }else{
                            ToastUtils.s(application,"取消$position")
                        }

                    }
        }
```

## 常规用法

```
        recyclerView.layoutManager = LinearLayoutManager(this@ComfortControlActivity)
        recyclerView.adapter = myAdapter
        myAdapter.setNewData(carList)
        myAdapter.addChildClickViewIds(R.id.iv_check)
        myAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>?,
                view: View?,
                position: Int
            ) {
                if (view!!.id == R.id.iv_check){
                    view.visibility = View.VISIBLE
                }else{
                    view.visibility = View.GONE
                }
            }
        })
        myAdapter.setOnItemClickListener(OnItemClickListener {
                adapter, view, position ->
            clearGear()
            var iv_check:ImageView = view.findViewById(R.id.iv_check)
            iv_check.visibility = View.VISIBLE
        })

```


