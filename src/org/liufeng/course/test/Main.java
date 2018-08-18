package org.liufeng.course.test;

import java.io.UnsupportedEncodingException;

import org.liufeng.course.util.TransApi;

public class Main {

    private static final String APP_ID = "20180313000135235";
    private static final String SECURITY_KEY = "2_ug8SB9LpOjmpl6TMyZ";

    public static void main(String[] args) throws UnsupportedEncodingException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "你好，这世界";
         String content_result = api.getTransResult(query, "auto", "en");
//         System.out.println(api.getTransResult(query, "auto", "en"));
        char [] content_result_temp = content_result.toCharArray();
        content_result = "";//清空数据
        for(int i = content_result_temp.length-5;;i--) {
            if(content_result_temp[i] == '"') {
                break;
            }
//            content_result = content_result_temp[i] + content_result;
            content_result = content_result_temp[i] + content_result;
//       System.out.println(api.getTransResult(query, "auto", "zh"));
        }	
        System.out.println(content_result);
    }
}
