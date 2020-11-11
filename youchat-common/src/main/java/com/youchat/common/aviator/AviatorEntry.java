package com.youchat.common.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

public class AviatorEntry {

    public static void main(String[] args) {
        Long result2 = (Long) AviatorEvaluator.execute("1+2+3+8 *1");
        System.out.println(result2);

        String yourName = "Michael";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("yourName", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        System.out.println(result);  // hello Michael

    }
}
