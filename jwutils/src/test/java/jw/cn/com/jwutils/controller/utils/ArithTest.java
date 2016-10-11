package jw.cn.com.jwutils.controller.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ArithTest {
    @Test
    public void add() throws Exception {
        double a =  ArithTools.add(3.4,4);
        double b = ArithTools.add(3.4,4,2,0);
        assertEquals(7.4,a,0);
        assertEquals(7.4+"",String.valueOf(b));
        assertEquals("7.40",ArithTools.addFormat(3.4,4,2, BigDecimal.ROUND_HALF_UP));
        assertEquals(7.43+"",ArithTools.addFormat(3.433,4,2,BigDecimal.ROUND_HALF_UP));
        assertEquals("7.00",ArithTools.setCapacityFormat(7,2,ArithTools.ROUND_MODE_HELF_UP));
        assertEquals("7.30",ArithTools.setTowCapacityFormat(7.3));
        assertEquals("700%",ArithTools.setPercentFormat(7.3,0,ArithTools.ROUND_MODE_HELF_UP));
        assertEquals(7.0000,ArithTools.setCapacity(7,2,ArithTools.ROUND_MODE_HELF_UP),0);
    }

}