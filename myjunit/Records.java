import org.omg.CORBA.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengcs on 2017/11/15.
 */
public class Records {

    private int recordNum;
    private int type;
    private static Map<String, Object> recordMap = new HashMap<>();

    public Records(int type) {
        this.type = type;
    }

    public void addRecoed(String key, Object value){
        recordNum++;
        recordMap.put(key, value);
    }
}
