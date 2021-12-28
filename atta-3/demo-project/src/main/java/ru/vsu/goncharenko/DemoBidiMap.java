package ru.vsu.goncharenko;

import ru.vsu.goncharenko.bidimap.DualTreeBidiMap;

import java.util.Scanner;

public class DemoBidiMap {

    public static void demo(){
        DualTreeBidiMap<Integer, String> bidiMap = new DualTreeBidiMap<>();
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
                bidiMap.put(key, value);
                System.out.println();
            }else if (operation.equals("removeKey")) {
                System.out.println("operation: remove");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                bidiMap.removeKey(key);
                System.out.println();
            } else if(operation.equals("removeValue")){
                System.out.println("Value");
                String value = scanner.next();
                bidiMap.removeValue(value);
                System.out.println();
            } else if (operation.equals("containsKey")) {
                System.out.println("operation: containsKey");

                System.out.println("Key: ");
                Integer key = scanner.nextInt();
                System.out.println(bidiMap.containsKey(key));
                System.out.println();
            }else if (operation.equals("containsValue")) {
                System.out.println("operation: containsValue");

                System.out.println("Value");
                String value = scanner.next();
                System.out.println(bidiMap.containsValue(value));
                System.out.println();
            }else if(operation.equals("print")){
                System.out.println(bidiMap.toString());
            }else {
                commandMenu();
            }
            operation = scanner.nextLine();
        }


    }

    private static void commandMenu(){
        System.out.println("Usage:");

        System.out.println(" put");
        System.out.println(" removeKey");
        System.out.println(" removeValue");
        System.out.println(" containsKey");
        System.out.println(" containsValue");
        System.out.println(" inverseBidiMap");
        System.out.println(" print");
        System.out.println("end");
    }
}
