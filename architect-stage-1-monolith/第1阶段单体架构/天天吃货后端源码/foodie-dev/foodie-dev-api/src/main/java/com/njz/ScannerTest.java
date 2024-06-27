package com.njz;

import java.util.Scanner;

public class ScannerTest {
    public static void main(String[] args) {
        System.out.print("请输入一个字符串：");

        // 读取整数输入
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        printStr(str);
    }

    private static void printStr(String str) {
        System.out.println(str);
    }
}
