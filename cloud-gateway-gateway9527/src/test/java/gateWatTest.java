import org.junit.Test;

import java.time.ZonedDateTime;

public class gateWatTest {

    @Test
    public void test(){
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);//2021-07-29T21:45:34.781+08:00[Asia/Shanghai]
    }
}
