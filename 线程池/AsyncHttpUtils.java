public class AsyncHttpUtils {
    //获取当前设备的CPU数
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心池大小设为CPU数加1
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    //设置线程池的最大大小
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1;
    //存活时间
    private static final long KEEP_ALIVE = 5L;
    
    //创建线程池对象
    public static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static void get(final String url, final ResponseHandler responseHandler) {
        final Handler mHandler = new Handler(Looper.getMainLooper());
        
        //创建一个新的请求任务
        Runnable requestRunnable = new Runnable() {
            @Override
            public void run() {
                final byte[] result = HttpUtils.get(url);
                if (result != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //result不为空表明请求成功，回调onSuccess方法
                            responseHandler.onSuccess(result);
                        }
                    });
                }
            }
        };
        threadPoolExecutor.execute(requestRunnable);
    }
}