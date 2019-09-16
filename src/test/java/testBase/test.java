package testBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Lides
 * @Date: 2019/8/20 15:45
 */
public class test {

    public static void main(String[] args) {
        List<Integer> l = new ArrayList();
        l.add(1);
        l.add(2);
        l.add(4);
        l.add(5);
        l =  l.stream().map(a->a+1).collect(Collectors.toList());
        System.out.println(l);
    }
}
