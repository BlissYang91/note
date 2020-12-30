```
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.byl.mvvm.widget.clicks
import java.lang.reflect.ParameterizedType

/**
 * 通过传入ViewBinding，不再需要写具体xml资源，省略onBindViewHolder中findviewById
 * 注意点：item的最外层布局高度要设为wrap_content，
 * 如果item有需求要设置为固定宽高，可以在子类的convert方法里，通过代码设置
 */
abstract class BaseAdapter<VB : ViewBinding, T>(
    var mContext: Activity,
    var listDatas: ArrayList<T>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val type = javaClass.genericSuperclass as ParameterizedType//参数化类型
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        var vb = method.invoke(null, LayoutInflater.from(mContext)) as VB
        vb.root.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return BaseViewHolder(vb, vb.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.clicks {
            itemClick?.let { it(position) }
        }
        holder.itemView.setOnLongClickListener {
            itemLongClick?.let { it1 -> it1(position) }
            true
        }

        convert(holder.v as VB, listDatas[position], position)
    }

    abstract fun convert(v: VB, t: T, position: Int)

    override fun getItemCount(): Int {
        return listDatas.size
    }


    private var itemClick: ((Int) -> Unit)? = null
    private var itemLongClick: ((Int) -> Unit)? = null


    fun itemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    fun itemLongClick(itemLongClick: (Int) -> Unit) {
        this.itemLongClick = itemLongClick
    }
}


```
```
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder(var v: ViewBinding, itemView: View) : RecyclerView.ViewHolder(itemView)
```

```
import android.app.Activity
import com.bumptech.glide.Glide
import com.byl.mvvm.databinding.ItemArticleBinding
import com.byl.mvvm.ui.base.BaseAdapter
import com.byl.mvvm.ui.main.model.ArticleBean


class ArticleListAdapter(context: Activity, listDatas: ArrayList<ArticleBean>) :
    BaseAdapter<ItemArticleBinding, ArticleBean>(context, listDatas) {

    override fun convert(v: ItemArticleBinding, t: ArticleBean, position: Int) {
        Glide.with(mContext).load(t.envelopePic).into(v.ivCover)
        v.tvTitle.text = t.title
        v.tvDes.text = t.desc
    }

}

```
```
 adapter = ArticleListAdapter(mContext, list!!)
        adapter!!.itemClick {
            startActivity(Intent(mContext, TestEventActivity::class.java))
        }
        v.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        v.mRecyclerView.adapter = adapter
```