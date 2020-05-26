## List stream流的操作

因为之前在找工作时，有一家公司给的机式题是这样的，

```
(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
(2) 交易员都在哪些不同的城市工作过？
(3) 查找所有来自于剑桥的交易员，并按姓名排序。
(4) 返回所有交易员的姓名字符串，按字母顺序排序。
(5) 有没有交易员是在米兰工作的？
(6) 打印生活在剑桥的交易员的所有交易额。
(7) 所有交易中，最高的交易额是多少？
(8) 找到交易额最小的交易。
```

题目考察的内容大概就和上面要考察的知识点一样，因为之前没有接触过java8新特性，所有当时用foreach写了几道题😄，事后回去百度想起来我也真是搞笑。

___

#### Stream 的介绍

> 文章出自简书，作者：[Insist_Dream](https://www.jianshu.com/u/116d0ae894f6)关注

> 链接:https://www.jianshu.com/p/24af4f3ab046

Stream 我们都称其为“流”，通过将集合转换为这么一种叫做 “流” 的元素序列，通过声明性方式，能够对集合中的每个元素进行一系列并行或串行的流水线操作。

使用Stream能够很明显的从字面上看出简化了代码， 能够提高了代码的简易性，可维护性，可靠性，更不容易出错。

___

#### Stream 的使用

首先我们需要先拥有一个List集合，然后我们将其转换成Stream进行操作。

~~~
List<Traders> list=new ArrayList<>();
~~~

上面我们创建了一个交易员集合，交易员类包含了三个变量：姓名，工作地址，交易额。

~~~
/**
 * 交易员
 */
public class Traders {

    private String name;//姓名
    private String address;//地址
    private Integer  turnover;//交易额
    }
~~~

初始化测试数据,并将集合转换成流。

~~~
  List<Traders> list=new ArrayList<Traders>();//创建交易员集合
        //往集合中交易员数据
        list.add(new Traders("小明","青秀区",10000));
        list.add(new Traders("小明","青秀区",10000));
        list.add(new Traders("小红","青秀区",20000));
        list.add(new Traders("小蓝","西乡塘区",30000));
        list.add(new Traders("小黄","江南区",40000));
        //最常用的方法，将List转换成流
        list.stream();
    }
~~~

##### 1. 过滤filter(T -> boolean)

保存结果为ture的元素

~~~
       //获取地址为青秀区的交易员
        final List<Traders> address = list.stream()
                 .filter(e -> e.getAddress().equals("青秀区"))
                 .collect(Collectors.toList());
        System.out.println(address);
        //结果：[Traders{name='小明', address='青秀区', turnover=10000},Traders{name='小红', address='青秀区', turnover=20000}]

~~~

##### 2. 映射 map(T -> R)
将流中的每一个元素 T 映射为 R（类似类型转换）

~~~
       //获取交易员名，将List<Traders>转换成List<String>
        List<String> names = list.stream().map(e -> e.getName()).collect(Collectors.toList());
        System.out.println(names);
        //结果[小明, 小红, 小蓝, 小黄]
        
        //因为刚实习，在公司负责接口的开发，需要跟前端和业务层对接传参数，所以该方法用的比较频繁，例如:业务层返回一个参数，我需要将其转换成RO类返回给前端，这时，使用foreach一个一个遍历的获取字段就比较麻烦，可以这么写
        //List<Traders> 转换成 List<TradersRo>,两实体类的变量是一样的
         List<Traders> list=TradersCvs.getTradersList();
         List<TradersRo> ros=list.stream().map(e ->BeanUtil.copsProperties(e,TradersRo.class)).collect(Collectors.toList());
         //这样就完成类的转换啦
       
        
        //BeanUtil工具类来自一下依赖
        <!--hutool -->
		<dependency>
			<groupId>cn.hutool</groupId>
			<artifactId>hutool-core</artifactId>
			<version>${hutool.version}</version>
		</dependency>
		<dependency>
			<groupId>cn.hutool</groupId>
			<artifactId>hutool-json</artifactId>
			<version>${hutool.version}</version>
		</dependency>

~~~

##### 3. 去重 distinct()
去除重复元素，这个方法是通过类的 equals 方法来判断两个元素是否相等的

~~~
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
~~~

##### 4. 排序 sorted() / sorted((T, T) -> int)
 如果流中的元素的类实现了 Comparable 接口，即有自己的排序规则，那么可以直接调用 sorted() 方法对元素进行排序，如 Stream<Integer>
 反之, 需要调用 sorted((T, T) -> int) 实现 Comparator 接口

~~~
//排序
        //List<Traders> sorted01 = list.stream().sorted().collect(Collectors.toList());
        //System.out.println(sorted01);
        //结果：类没有实现java.lang.Comparable
        //会报错，java.lang.ClassCastException: com.list_stream.test.Traders cannot be cast to java.lang.Comparable
        final List<Traders> sorted02 = list.stream().sorted((p1, p2) -> p2.getTurnover() - p1.getTurnover()).collect(Collectors.toList());
        System.out.println("排序:"+sorted02);
        //执行结果：排序:[Traders{name='小黄', address='江南区', turnover=40000}, Traders{name='小蓝', address='西乡塘区', turnover=30000}, Traders{name='小红', address='青秀区', turnover=20000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}]
        //简化
        List<Traders> sorted03 = list.stream().sorted(Comparator.comparingInt(e -> e.getTurnover())).collect(Collectors.toList());
~~~

##### 5. 分页 limit(long n)
返回前 n 个元素

~~~
        //limit
        final List<Traders> limit = list.stream().limit(3).collect(Collectors.toList());
        System.out.println("limit:"+limit);
        //结果:limit:[Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小明', address='青秀区', turnover=10000}, Traders{name='小红', address='青秀区', turnover=20000}]
~~~

##### 6.  skip(long n)
去除前 n 个元素

~~~
        //skip
        List<Traders> skip = list.stream().skip(4).collect(Collectors.toList());
        System.out.println("skip:"+skip);
        //结果：skip:[Traders{name='小黄', address='江南区', turnover=40000}]
~~~

###### 7.flatMap(T -> Stream<R>)
将流中的每一个元素 T 映射为一个流，再把每一个流连接成为一个流

~~~
 List<String> list01 = new ArrayList<>();
        list01.add("aaa bbb ccc");
        list01.add("ddd eee fff");
        list01.add("ggg hhh iii");

        List<String> stringList = list01.stream().map(s -> s.split("")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println("stringList:"+stringList);
        //执行结果
        //stringList:[aaa, bbb, ccc, ddd, eee, fff, ggg, hhh, iii]
~~~

##### 8. 条件筛选 anyMatch(T -> boolean)
流中是否有一个元素匹配给定的 T -> boolean 条件
是否存在一个 Turnover对象的 交易额等于 10000的

~~~
  boolean b = list.stream().anyMatch(e -> e.getTurnover() == 10000);
~~~

##### 9. 求和 reduce((T, T) -> T) 和 reduce(T, (T, T) -> T)
用于组合流中的元素，如求和，求积，求最大值等

reduce 第一个参数 0 代表起始值为 0（可以自定义起始值），lambda (a, b) -> a + b 即将两值相加产生一个新值
同样地

~~~
     Integer reduce1 = list.stream().map(e -> e.getTurnover()).reduce(0, (a, c) -> a + c);
        Optional<Integer> reduce2 = list.stream().map(e -> e.getTurnover()).reduce(Integer::sum);
        System.out.println("reduce1:"+reduce1);
        System.out.println("reduce2:"+reduce2);
        //结果
        //reduce1:110000
        //reduce2:Optional[110000]
~~~

##### 10. 总数 count()
返回流中元素个数，结果为 long 类型

##### 11. 转换成集合 collect()
收集方法，我们很常用的是 .collect(Collectors.toList())，当然还有 collect(Collectors.toSet()) 等，参数是一个收集器接口，这个后面会另外讲

##### 12. 遍历 foreach()

~~~
 list.forEach(i ->{
            System.out.println("交易员信息:"+i);
        });
~~~

___

#### 练习

~~~
        //修改数据
        //往集合中交易员数据
        list.add(new Traders("小明", "青秀区", 10000, 2011));
        list.add(new Traders("小明", "青秀区", 500, 2011));
        list.add(new Traders("小红", "青秀区", 20000, 2020));
        list.add(new Traders("小蓝", "西乡塘区", 30000, 2015));
        list.add(new Traders("小黄", "江南区", 40000, 2010));
~~~



##### 1. 找出2011年发生的所有交易，并按交易额排序（从低到高）。

~~~
       //1.找出2011年发生的所有交易，并按交易额排序（从低到高）。
        List<Traders> demo01 = list.stream()
                .filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparingInt(e -> e.getTurnover()))
                .collect(Collectors.toList());
        System.out.println("demo01:"+demo01);
        //结果
        //demo01:[Traders{name='小明', address='青秀区', turnover=500, year=2011},
        // Traders{name='小明', address='青秀区', turnover=10000, year=2011}]

~~~

##### 2. 交易员都在哪些不同的地区工作过？

~~~
        //2.交易员都在哪些不同的地区工作过？
        List<String> demo02 = list.stream()
                .map(e -> e.getAddress())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("demo02:"+demo02);
        //结果
        //demo02:[青秀区, 西乡塘区, 江南区]
~~~

##### 3. 查找所有来自于青秀的交易员，并按姓名排序

~~~
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
~~~

##### 4.返回所有交易员的姓名字符串，按字母顺序排序。

为了能看到效果，修改业务员名称

~~~
        list.add(new Traders("c", "青秀区", 10000, 2011));
        list.add(new Traders("d", "青秀区", 500, 2011));
        list.add(new Traders("a", "青秀区", 20000, 2020));
        list.add(new Traders("r", "西乡塘区", 30000, 2015));
        list.add(new Traders("c", "江南区", 40000, 2010));
~~~

~~~
        // 4.返回所有交易员的姓名字符串，按字母顺序排序。
        List<String> demo04 = list.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        demo04.sort((r1,r2) ->r1.compareTo(r2));
        System.out.println("demo04:"+demo04);
        //结果
        //demo04:[a, c, c, d, r]
~~~

##### 5. 有没有交易员是在西乡塘工作的？

~~~
       //5.有没有交易员是在西乡塘工作的？
        boolean demo05 = list.stream().anyMatch(e -> e.getAddress().equals("西乡塘区"));
        System.out.println("demo05:"+demo05);
        //结果
        //demo05:true
~~~

##### 6. 打印生活在青秀区的交易员的所有交易额。

~~~
       //6.打印生活在青秀区的交易员的所有交易额。
        Integer demo06 = list.stream()
                .filter(e -> e.getAddress().equals("青秀区"))
                .map(e -> e.getTurnover())
                .reduce(0, (o, p) -> o + p);
        System.out.println("demo06:"+demo06);
        //结果
        //demo06:30500
~~~

##### 7. 所有交易中，最高的交易额是多少？

~~~
     //7.所有交易中，最高的交易额是多少？
        List<Integer> demo07 = list.stream()
                .map(e -> e.getTurnover())
                .sorted(Comparator.comparingInt(e -> -e.intValue()))
                .limit(1)
                .collect(Collectors.toList());
        System.out.println("demo07:"+demo07);
        //结果
        //demo07:[40000]
~~~

##### 8. 找到交易额最小的交易。

~~~
//7.所有交易中，最高的交易额是多少？
        List<Integer> demo07 = list.stream()
                .map(e -> e.getTurnover())
                .sorted(Comparator.comparingInt(e -> e.intValue()))
                .limit(1)
                .collect(Collectors.toList());
        System.out.println("demo07:"+demo07);
        //结果
        //demo07:[500]
~~~

