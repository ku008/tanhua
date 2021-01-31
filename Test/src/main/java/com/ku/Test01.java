package com.ku;

/**
 * @author ku
 * @date 2020/12/31
 */
public class Test01 {

    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        System.out.println(os);

        String path = System.getProperty("user.dir");
        System.out.println(path);
    }

}
