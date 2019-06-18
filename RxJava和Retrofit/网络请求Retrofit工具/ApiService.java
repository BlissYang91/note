package edu.com.gaiwen.firstchoice.utils;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by xfc on 2017/7/24.
 * 邮箱:874020287@qq.com
 */

public interface ApiService {

    /**
     * get 请求方式
     * map 作为参数
     */
    @GET()
    Call<ResponseBody> getMap(@Url String url, @QueryMap Map<String, Object> map);

    /**
     * get 请求方式
     * 参数直接拼接
     */
    @GET()
    Call<ResponseBody> getParameter(@Url String url);

    /**
     * 表单数据提交
     * Map的key作为表单的键
     */
    @FormUrlEncoded
    @POST()
    Call<ResponseBody> postformMap(@Url String url, @FieldMap(encoded = true) Map<String, Object> map);

    /**
     * 表单数据提交
     * Map的key作为表单的键
     */
    @POST()
    Call<ResponseBody> uploadjson(@Url String url, @Body RequestBody jsonBody);

    /**
     * 进行文件上传
     */
    @POST()
    @Multipart
    Call<ResponseBody> fileUpload(@Url String url, @PartMap Map<String, RequestBody> args);

    /**
     * 基本数据提交（比如json）
     */
    @POST()
    Call<ResponseBody> setData(@Url String url, @Body String string);

    @Multipart
    @POST()
    Call<ResponseBody> fileUpload(@Url String url, @Part MultipartBody.Part file);

    /**
     * 表单数据提交
     * Map的key作为表单的键
     */
    @POST()
    Observable<ResponseBody> uploadjsonRxjava(@Url String url, @Body RequestBody jsonBody);

    /**
     * get 请求方式
     * 参数直接拼接
     */
    @GET()
    Observable<ResponseBody> getParameterRxjava(@Url String url);

    /**
     * get 请求方式
     * map 作为参数
     */
    @GET()
    Observable<ResponseBody> getMapRxjava(@Url String url, @QueryMap Map<String, Object> map);

    /**
     * 表单数据提交
     * Map的key作为表单的键
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postformRxjava(@Url String url, @FieldMap(encoded = true) Map<String, Object> map);

    /**
     * 进行文件上传
     */
    @POST()
    @Multipart
    Observable<ResponseBody> fileUploadRxjava(@Url String url, @PartMap Map<String, RequestBody> args);

    /**
     * 基本数据提交（比如json）
     */
    @POST()
    Observable<ResponseBody> setDataRxjava(@Url String url, @Body String string);
}
