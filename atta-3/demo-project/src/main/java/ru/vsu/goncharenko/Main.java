package ru.vsu.goncharenko;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("choose: treeMap or bidiMap");
        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();

        if(operation.equals("treeMap")) {
            DemoTreeMap.demo();
        }
        if(operation.equals("bidiMap")) {
            DemoBidiMap.demo();
        }
    }
}
