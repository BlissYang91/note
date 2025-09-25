import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tvResult;
    private ApiManager apiManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvResult = findViewById(R.id.tv_result);
        apiManager = new ApiManager();
        
        // 发起网络请求
        fetchUserInfo("12345");
    }

    private void fetchUserInfo(String userId) {
        Disposable disposable = apiManager.getUserInfoWithRetry(userId)
                .subscribeOn(Schedulers.io()) // 在IO线程执行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理结果
                .subscribeWith(new DisposableObserver<UserResponse>() {
                    @Override
                    public void onNext(UserResponse userResponse) {
                        // 处理成功结果
                        Log.d(TAG, "获取用户信息成功: " + userResponse);
                        tvResult.setText("用户ID: " + userResponse.getId() + 
                                        "\n用户名: " + userResponse.getName() + 
                                        "\n邮箱: " + userResponse.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 处理错误（所有重试都失败后）
                        String errorMsg = "请求失败: " + e.getMessage();
                        Log.e(TAG, errorMsg, e);
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        tvResult.setText(errorMsg);
                    }

                    @Override
                    public void onComplete() {
                        // 请求完成
                        Log.d(TAG, "请求完成");
                    }
                });
        
        // 添加到CompositeDisposable管理
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除所有订阅，防止内存泄漏
        compositeDisposable.clear();
    }
}
