import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiManager apiManager;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        apiManager = new ApiManager();

        // 发起请求
        fetchUserInfo("12345");
    }

    private void fetchUserInfo(String userId) {
        compositeDisposable.add(
            apiManager.getUserInfoWithRetry(userId)
                .observeOn(AndroidSchedulers.mainThread()) // 主线程处理结果
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        // 请求成功（业务成功且重试完成）
                        tvResult.setText("用户信息：\nID: " + user.getId() + "\n姓名: " + user.getName());
                        Log.d(TAG, "请求成功，用户姓名：" + user.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 所有重试失败或不需要重试的错误
                        String errorMsg;
                        if (e instanceof ApiManager.BusinessException) {
                            errorMsg = "业务错误（" + ((ApiManager.BusinessException) e).getCode() + "）：" + e.getMessage();
                        } else if (e instanceof ApiManager.RetryException) {
                            errorMsg = "重试次数耗尽（" + ((ApiManager.RetryException) e).getCode() + "）：" + e.getMessage();
                        } else {
                            errorMsg = "网络错误：" + e.getMessage();
                        }
                        tvResult.setText(errorMsg);
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        Log.e(TAG, "请求失败：" + errorMsg);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求完成");
                    }
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // 清除所有订阅，防止内存泄漏
    }
}
    