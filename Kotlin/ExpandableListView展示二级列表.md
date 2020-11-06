

```
        <ExpandableListView
            android:id="@+id/exp_list_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/ve_diagnosis_result_bg"
            android:padding="20dp"
            android:groupIndicator="@null"
            android:divider="@color/white"
            android:dividerHeight="0dp"
            android:scrollbars="none"
            android:layout_marginRight="15dp"
            android:layout_marginLeft="15dp"
            android:layout_marginTop="20dp">

        </ExpandableListView>
```

- [x] BaseExpandableListAdapter

```
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.beans.base.utils.log.logE
import com.beans.vehicle.R
import com.beans.vehicle.bean.DiagnosisReportDetail
import kotlinx.android.synthetic.main.ve_diagnosis_item_sub.view.*
import kotlinx.android.synthetic.main.ve_diagnosis_item_super.view.*


/**
 * @Author yangtianfu
 * @CreateTime 2020/5/15 17:43
 * @Describe 二级列表adapter
 */
class DiagnosisAdapter : BaseExpandableListAdapter {
    var diagnosisReportDetailFirst: MutableList<DiagnosisReportDetail> = mutableListOf()
    var diagnosisReportDetailSecond: MutableList<MutableList<DiagnosisReportDetail>> = mutableListOf()
    lateinit var groupViewHolder:GroupViewHolder
    lateinit var childViewHolder:ChildViewHolder
    constructor(
        diagnosisReportDetailFirst: MutableList<DiagnosisReportDetail>,
        diagnosisReportDetailSecond: MutableList<MutableList<DiagnosisReportDetail>>
    ) : super() {
        this.diagnosisReportDetailFirst = diagnosisReportDetailFirst
        this.diagnosisReportDetailSecond = diagnosisReportDetailSecond
    }

    //返回第一级List长度
    override fun getGroupCount(): Int {
        return diagnosisReportDetailFirst.size
    }

    //返回指定groupPosition的第二级List长度
    override fun getChildrenCount(groupPosition: Int): Int {
        return diagnosisReportDetailSecond.get(groupPosition).size
    }

    //返回一级List里的内容
    override fun getGroup(groupPosition: Int): Any {
        return diagnosisReportDetailFirst.get(groupPosition)
    }


    //返回二级List的内容
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return diagnosisReportDetailSecond.get(groupPosition).get(childPosition)
    }

    //返回一级View的id 保证id唯一
    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    //返回二级View的id 保证id唯一
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (groupPosition+childPosition).toLong()
    }

    //基础数据进行更改时子ID和组ID是否稳定
    override fun hasStableIds(): Boolean {
        return true
    }

    // 返回一级父View
    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var mconvertView = convertView
        if (convertView == null){
            mconvertView =  LayoutInflater.from(parent?.context).inflate(R.layout.ve_diagnosis_item_super, parent,false)
            groupViewHolder = GroupViewHolder(mconvertView)
            mconvertView?.tag = groupViewHolder
        }else{
            groupViewHolder = mconvertView?.tag as GroupViewHolder
        }
        groupViewHolder.tv_name_sup?.text = diagnosisReportDetailFirst[groupPosition].sysN.toString()
        groupViewHolder.tv_name_des?.text = "${diagnosisReportDetailSecond[groupPosition].size}项异常"
        if (isExpanded){
            groupViewHolder.iv_super?.setImageResource(R.drawable.icon_diagnosis_up)
        }else{
            groupViewHolder.iv_super?.setImageResource(R.drawable.icon_diagnosis_down)
        }

        return mconvertView!!
    }

    // 返回二级子View
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var mconvertView = convertView
        if (convertView == null){
            mconvertView =  LayoutInflater.from(parent?.context).inflate(R.layout.ve_diagnosis_item_sub, parent,false)
            childViewHolder = ChildViewHolder(mconvertView)
            // 用mconvertView代替convertView，不然会有空指针异常
            mconvertView?.tag = childViewHolder
        }else{
            childViewHolder = mconvertView?.tag as ChildViewHolder
        }
        childViewHolder.tv_name_sub?.text = diagnosisReportDetailSecond[groupPosition][childPosition].itemN
        childViewHolder.iv_sub?.setImageResource(R.drawable.icon_diagnosis_right)

        return mconvertView!!

    }

    //指定位置的子项是否可选
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }


    inner class GroupViewHolder(view:View) {
        var tv_name_sup: TextView? = null
        var tv_name_des: TextView? = null
        var iv_super: ImageView? = null
        init {
            tv_name_sup = view.tv_name_sup
            tv_name_des = view.tv_name_des
            iv_super = view.iv_super
        }
    }

    inner  class ChildViewHolder(view:View){
        var tv_name_sub: TextView? = null
        var iv_sub: ImageView? = null
        init {
            tv_name_sub = view.tv_name_sub
            iv_sub = view.iv_sub
        }
    }

}
```
- [x] 展示二级列表

```

    private fun initListView() {
        diagnosisAdapter = DiagnosisAdapter(diagnosisReportDetailFirst,diagnosisReportDetailSecond)
        exp_list_view.setAdapter(diagnosisAdapter)
        //展开某个分组时，并关闭其他分组
        exp_list_view.setOnGroupExpandListener {
            for (groupPosition in diagnosisReportDetailFirst.indices){
                if (groupPosition != it){
                    exp_list_view.collapseGroup(groupPosition)
                }
            }
        }
        //点击某个分组监听
//        exp_list_view.setOnGroupClickListener { parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long ->
//            Toast.makeText(this,"点击一级列表$groupPosition",Toast.LENGTH_SHORT).show()
//            logE("点击一级列表item$groupPosition")
//            false // true屏蔽一级列表点击
//        }

        //某个分组中的子View点击事件
        exp_list_view.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            logE("点击二级列表item$groupPosition")
            false
        }

    }
```
