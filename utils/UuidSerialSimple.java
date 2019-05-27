
import java.util.UUID;

/**
 * @autor YangTianFu
 * @Date 2019/4/29 10:22
 * @Description
 */
public class UuidSerialSimple {
    public static String getUniquePsuedoID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
