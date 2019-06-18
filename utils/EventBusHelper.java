
public class EventBusHelper {

    public static void register(Object context) {
        EventBus.getDefault().register(context);
    }

    public static void unRegister(Object context) {
        EventBus.getDefault().unregister(context);
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }
}
