package com.list_stream.test;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static  void main (String [] args) {

        List<Traders> list = new ArrayList<Traders>();//创建交易员集合
        //往集合中交易员数据
        list.add(new Traders("小明", "青秀区", 10000, 2011));
        list.add(new Traders("小明", "青秀区", 500, 2011));
        list.add(new Traders("小红", "青秀区", 20000, 2020));
        list.add(new Traders("小蓝", "西乡塘区", 30000, 2015));
        list.add(new Traders("小黄", "江南区", 40000, 2010));
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
        System.out.println("去重前--:" + list);
        List<Traders> distincts = list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后--:" + distincts);
        //执行结果
        // 去重前--:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小黄', address='江南区', turnover=40000}]
        // 去重后--:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小黄', address='江南区', turnover=40000}]
        //可以看出并没有什么效果
        //我们使用上面获取名称的集合来试一试
        List<String> distinctToNames = names.stream().distinct().collect(Collectors.toList());
        System.out.println("distinctToNames:" + distinctToNames);
        //执行前 [小明, 小明, 小红, 小蓝, 小黄]
        //执行后 distinctToNames:[小明, 小红, 小蓝, 小黄]
        //就可以看出效果啦，以对象来举例子时，其实对象只是变量值一样，但是对象不一样啊

        //排序
        //List<Traders> sorted01 = list.stream().sorted().collect(Collectors.toList());
        //System.out.println(sorted01);
        //结果：类没有实现java.lang.Comparable
        //会报错，java.lang.ClassCastException: com.list_stream.test.Traders cannot be cast to java.lang.Comparable
        final List<Traders> sorted02 = list.stream().sorted((p1, p2) -> p2.getTurnover() - p1.getTurnover()).collect(Collectors.toList());
        System.out.println("排序:" + sorted02);
        //执行结果：排序:[Traders{name='小黄', address='江南区', turnover=40000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}]
        //简化
        List<Traders> sorted03 = list.stream().sorted(Comparator.comparingInt(e -> e.getTurnover()))
                .collect(Collectors.toList());
        System.out.println("排序:" + sorted03);

        //limit
        final List<Traders> limit = list.stream().limit(3).collect(Collectors.toList());
        System.out.println("limit:" + limit);
        //结果:limit:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}]

        //skip
        List<Traders> skip = list.stream().skip(4).collect(Collectors.toList());
        System.out.println("skip:" + skip);
        //结果：skip:[Traders{name='小黄', address='江南区', turnover=40000}]

        List<String> list01 = new ArrayList<>();
        list01.add("aaa bbb ccc");
        list01.add("ddd eee fff");
        list01.add("ggg hhh iii");

        List<String> stringList = list01.stream().map(s -> s.split(" ")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println("stringList:" + stringList);

        boolean b = list.stream().anyMatch(e -> e.getTurnover() == 10000);
        System.out.println(b);

        Integer reduce1 = list.stream().map(e -> e.getTurnover()).reduce(0, (a, c) -> a + c);
        Optional<Integer> reduce2 = list.stream().map(e -> e.getTurnover()).reduce(Integer::sum);
        System.out.println("reduce1:" + reduce1);
        System.out.println("reduce2:" + reduce2);
        //结果
        //reduce1:110000
        //reduce2:Optional[110000]

        list.forEach(i -> {
            System.out.println("交易员信息:" + i);
        });


        //1.找出2011年发生的所有交易，并按交易额排序（从低到高）。
        List<Traders> demo01 = list.stream().filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparingInt(e -> e.getTurnover()))
                .collect(Collectors.toList());
        System.out.println("demo01:"+demo01);
        //结果
        //demo01:[Traders{name='小明', address='青秀区', turnover=10000, year=2011},
        // Traders{name='小明', address='青秀区', turnover=10000, year=2011}]

        //2.交易员都在哪些不同的城市工作过？
        List<String> demo02 = list.stream()
                .map(e -> e.getAddress())
                .distinct().collect(Collectors.toList());
        System.out.println("demo02:"+demo02);
        //结果
        //demo02:[青秀区, 西乡塘区, 江南区]

        //3.查找所有来自于青秀的交易员，并按姓名排序
        List<Traders> demo03 = list.stream()
                .filter(e -> e.getAddress().equals("青秀区"))
                .collect(Collectors.toList());
        demo03.sort((r1,r2) ->r1.getName().compareTo(r2.getName()));
        System.out.println("demo03:"+demo03);
        //将名字改为字母，看看效果
        //结果
        //demo03:[Traders{name='a', address='青秀区', turnover=20000, year=2020},
        // Traders{name='b', address='青秀区', turnover=10000, year=2011},
        // Traders{name='c', address='青秀区', turnover=500, year=2011}]


        // 4.返回所有交易员的姓名字符串，按字母顺序排序。
        List<String> demo04 = list.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        demo04.sort((r1,r2) ->r1.compareTo(r2));
        System.out.println("demo04:"+demo04);
        //结果
        //demo04:[a, c, c, d, r]

        //5.有没有交易员是在西乡塘工作的？
        boolean demo05 = list.stream().anyMatch(e -> e.getAddress().equals("西乡塘区"));
        System.out.println("demo05:"+demo05);
        //结果
        //demo05:true

        //6.打印生活在青秀区的交易员的所有交易额。
        Integer demo06 = list.stream()
                .filter(e -> e.getAddress().equals("青秀区"))
                .map(e -> e.getTurnover())
                .reduce(0, (o, p) -> o + p);
        System.out.println("demo06:"+demo06);
        //结果
        //demo06:30500

        //7.所有交易中，最高的交易额是多少？
        List<Integer> demo07 = list.stream()
                .map(e -> e.getTurnover())
                .sorted(Comparator.comparingInt(e -> -e.intValue()))
                .limit(1)
                .collect(Collectors.toList());
        System.out.println("demo07:"+demo07);
        //结果
        //demo07:[40000]
    }




}
