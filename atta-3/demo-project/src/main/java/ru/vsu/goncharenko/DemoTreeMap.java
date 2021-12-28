package ru.vsu.goncharenko;

import ru.vsu.goncharenko.treemap.MyTreeMap;

import java.util.Scanner;

public class DemoTreeMap {


    public static void demo(){
        MyTreeMap<Integer, String> treeMap = new MyTreeMap<>();
        commandMenu();
        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();

        while (!operation.equals("end")) {
            if (operation.equals("put")) {
                System.out.println("operation: put");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                System.out.println("Value");
                String value = scanner.next();
                treeMap.put(key, value);
                System.out.println();
            }else if (operation.equals("remove")) {
                System.out.println("operation: remove");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                System.out.println("Value");
                String value = scanner.next();
                treeMap.remove(key, value);
                System.out.println();
            } else if (operation.equals("containsKey")) {
                System.out.println("operation: containsKey");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                System.out.println(treeMap.containsKey(key));
                System.out.println();
            }else if (operation.equals("containsValue")) {
                System.out.println("operation: containsValue");

                System.out.println("Value");
                String value = scanner.next();
                System.out.println(treeMap.containsValue(value));
                System.out.println();
            }else if (operation.equals("firstKey")) {
                System.out.println("operation: firstKey");

                System.out.println(treeMap.firstKey());
                System.out.println();
            }else if (operation.equals("lastKey")) {
                System.out.println("operation: lastKey");

                System.out.println(treeMap.lastKey());
                System.out.println();
            }else if (operation.equals("size")) {
                System.out.println("operation: size");

                System.out.println(treeMap.size());
                System.out.println();
            }else if (operation.equals("clear")) {
                System.out.println("operation: clear");

                treeMap.clear();
                System.out.println();
            } else if(operation.equals("getValue")) {
                System.out.println("operation: getValue");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                if(treeMap.containsKey(key)) {
                    System.out.println(treeMap.getValue(key));
                    System.out.println();
                } else {
                    System.out.println("null");
                    System.out.println();
                }
            } else if(operation.equals("print")){
                System.out.println(treeMap.toString());
            } else {
                commandMenu();
            }
            operation = scanner.nextLine();
        }
    }

    private static void commandMenu(){
        System.out.println("Usage:");
        System.out.println(" put");
        System.out.println(" remove");
        System.out.println(" containsKey");
        System.out.println(" containsValue");
        System.out.println(" firstKey");
        System.out.println(" lastKey");
        System.out.println(" size");
        System.out.println(" clear");
        System.out.println(" getValue");
        System.out.println(" print");
        System.out.println("end");
    }
}
