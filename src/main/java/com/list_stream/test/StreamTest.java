package com.list_stream.test;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static  void main (String [] args){

        List<Traders> list=new ArrayList<Traders>();//创建交易员集合
        //往集合中交易员数据
        list.add(new Traders("小明","青秀区",10000));
        list.add(new Traders("小明","青秀区",10000));
        list.add(new Traders("小红","青秀区",20000));
        list.add(new Traders("小蓝","西乡塘区",30000));
        list.add(new Traders("小黄","江南区",40000));
        //最常用的方法，将List转换成流
        list.stream();

        //获取地址为青秀区的交易员
        final List<Traders> address = list.stream()
                 .filter(e -> e.getAddress().equals("青秀区"))
                 .collect(Collectors.toList());
        System.out.println(address);
        //[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}]

        //获取交易员名，将List<Traders>转换成List<String>
        List<String> names = list.stream().map(e -> e.getName()).collect(Collectors.toList());
        System.out.println(names);
        //结果[小明, 小红, 小蓝, 小黄]

        //去重
        System.out.println("去重前--:"+list);
        List<Traders> distincts = list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后--:"+distincts);
        //执行结果
        // 去重前--:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小黄', address='江南区', turnover=40000}]
        // 去重后--:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小黄', address='江南区', turnover=40000}]
        //可以看出并没有什么效果
        //我们使用上面获取名称的集合来试一试
        List<String> distinctToNames = names.stream().distinct().collect(Collectors.toList());
        System.out.println("distinctToNames:"+distinctToNames);
        //执行前 [小明, 小明, 小红, 小蓝, 小黄]
        //执行后 distinctToNames:[小明, 小红, 小蓝, 小黄]
        //就可以看出效果啦，以对象来举例子时，其实对象只是变量值一样，但是对象不一样啊

        //排序
        //List<Traders> sorted01 = list.stream().sorted().collect(Collectors.toList());
        //System.out.println(sorted01);
        //结果：类没有实现java.lang.Comparable
        //会报错，java.lang.ClassCastException: com.list_stream.test.Traders cannot be cast to java.lang.Comparable
        final List<Traders> sorted02 = list.stream().sorted((p1, p2) -> p2.getTurnover() - p1.getTurnover()).collect(Collectors.toList());
        System.out.println("排序:"+sorted02);
        //执行结果：排序:[Traders{name='小黄', address='江南区', turnover=40000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}]
    }




}
