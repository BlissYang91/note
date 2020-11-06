[TOC]
##  android:visibility 可见性绑定和onClick绑定
 
```
 <data>
        <import type="android.view.View"/>
        <variable
            name="data"
            type="com.x.x.x.Bean" />
        <variable
            name="listener"
            type="android.view.View.OnClickListener" />
    </data>
    
    
    <!--要在data节点下引入view-->
     android:visibility="@{data.isCharge ? View.VISIBLE: View.GONE}">

```
## 数据单向绑定的问题（数据改变更改视图）
> binding中的数据data类对应的JavaBean类bean，bean的属性修改后重新赋值给binding，就可以通知UI更改绑定的视图，实现数据和视图的动态绑定

```
  bean = xxBean("数据绑定",false) 
  mBinding.data = bean //javabean的值更改，视图层UI也就是xml文件中引用data的地方也随之修改
  
```
- [x] 数据承载类

```
open class BaseDto : Serializable {
}

data class SupportCodeDes(
    var codeList:List<String>? = arrayListOf()
):BaseDto()

```

- [x] model层获取数据

```
var codeList:MutableLiveData<SupportCodeDes> = MutableLiveData()

   fun getSupportCode(){
        var list = VehicleConfig.getAllFunction()
        var supportCodeDes = SupportCodeDes(list)
        codeList.postValue(supportCodeDes)
    }
```
- [x] UI调用方法

```
 override fun initData() {
        mModel.getSupportCode()
        mModel.codeList.observe(this, Observer {
            logE("更新UI ${it.codeList}")
            mBinding.code = it
        })
    }
```
- [x] xml中更新UI

```
   <data>
        <import type="android.view.View" />
        <variable
            name="listener"
            type="android.view.View.OnClickListener" />
        <variable
            name="code"
            type="com.beans.vehicle.dto.SupportCodeDes" />
    </data>
    
 <LinearLayout
            android:id="@+id/ll_find_car"
            android:onClick="@{listener}"
            android:visibility="@{code.codeList.contains(`1-1-3`)==true?View.VISIBLE:View.GONE}"
            style="@style/RemoteShowLineCenter">
```



## 点击事件监听

```
  mBinding.listener = View.OnClickListener {
            when(it.id){
                R.id.tv_temperature -> showAirControlDialog()
            }
        }
```

## 数据双向绑定问题
> 数据改变时同时使视图刷新,例如EditText的绑定
>绑定变量的方式比单向绑定多了一个等号：android:text="@={user.name}"

```
<data>
        <variable name="userInfo" type="com.github.ixiaow.sample.model.ObservableUser"/>
    </data>
<EditText
                app:layout_constraintVertical_chainStyle="spread"
                android:id="@+id/mUserName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@={userInfo.name, default=`name`}"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                app:layout_constraintTop_toTopOf="parent"/>
```
> 当 EditText的输入内容改变时，会同时同步到变量 user

## 调用类中的方法::

```
 android:onClick="@{onClickUtil::onClickWithMe}"
```

## Unresolved reference: BR

>手动导入BR 
- import com.xx.xx.BR
- 
## 常量值和变量组合绑定

```
 android:text="@{`您的爱车`+myData.licenseNumber+`非常健康，继续保持哦`}"
```

## BindingAdapter
- [x]  apply plugin: 'kotlin-kapt'
- [x] ImageView

```
@BindingAdapter("imageFromUrl")
@JvmStatic
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

 <ImageView
                    android:id="@+id/detail_image"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/plant_detail_app_bar_height"
                    android:contentDescription="@string/plant_detail_image_content_description"
                    android:fitsSystemWindows="true"
                    android:scaleType="centerCrop"
                    app:imageFromUrl="@{viewModel.plant.imageUrl}"
                    app:layout_collapseMode="parallax" />
```
- [x] View Gone

```
@BindingAdapter("isGone")
@JvmStatic
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isGone")
@JvmStatic
fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}

//xml使用app:isGone="@{!hasPlantings}"
//UI赋值 binding.hasPlantings = !result.isNullOrEmpty()
```
- [x] TextView

```
@BindingAdapter("renderHtml")
@JvmStatic
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

                <TextView
                    android:id="@+id/plant_description"
                    style="?android:attr/textAppearanceMedium"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="@dimen/margin_small"
                    android:layout_marginTop="@dimen/margin_small"
                    android:layout_marginEnd="@dimen/margin_small"
                    android:textIsSelectable="true"
                    android:minHeight="@dimen/plant_description_min_height"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toBottomOf="@id/plant_watering"
                    app:renderHtml="@{viewModel.plant.description}"
                    tools:text="Details about the plant" />
```

```
   @BindingAdapter(value = ["licenseNumber","vinLast4"])
    @JvmStatic
   fun bindText(textView: TextView,licenseNumber: String,vinLast4:String){
       if (licenseNumber==""){
           textView.text = vinLast4
       }else{
           textView.text = licenseNumber
       }
   }
   
   
                       <TextView
                        android:id="@+id/tv_item_name"
                        android:layout_width="wrap_content"
                        android:layout_height="25dp"
                        android:textSize="14sp"
                        android:textColor="#ff696d77"
                        android:gravity="center_vertical"
                        app:licenseNumber="@{myData.licenseNumber}"
                        app:vinLast4="@{myData.vinLast4}"/>
```

## data class 可选参数


```
data class SendCmdBaseDto (
    val vin: String,
    val remoteType: String,
    val commandType: String,
    val type: Int,
    val seqNo: String,
    val securityPassword: String?,
    val temp:String? = null, //可以在调用的时候不给改变量赋值
    val openAngle: String? = null,
    val runtime: String? = null
):BaseDto()
```


```
  windowControlModel.sendRemoteCmdCommon(
            SendCmdBaseDto(
                vin = vin,
                remoteType ="0",
                seqNo = seqNo,
                commandType = RemoteTypeCode,
                type = 1,
                openAngle = "2",
                securityPassword = scyPwd
            )
        )
```










