package ru.vsu.goncharenko;
import ru.vsu.goncharenko.treemap.MyTreeMap;



public class Main {

    public static void main(String[] args) {
        MyTreeMap<Integer, String> map = new MyTreeMap<>();
        map.put(1, "12");
        map.put(5, "aa");
        map.put(2, "vvv");
        map.put(7, "sss");
        map.put(6, "qq");
        System.out.println(map.toString());
    }
}
