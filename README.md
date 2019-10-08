# race
1024程序员比赛

# 题目一

标题：json框架编写 难度系数：1.0

题目描述：

编写一个json序列化框架，要求能够实现POJO对象到json字符串的转化。

如下调用api完成pojo到json的序列化
```java
        User user = new User();
        user.setAge(1);
        user.setName("jack");
        user.setRole(new Role(1, "tom"));
        user.setA((short) 12);
        user.setB((byte) 2);
        user.setC(3);
        user.setD(4);
        user.setE(true);
        user.setF('c');
        user.setG(12.1F);
        user.setArray(new byte[]{1, 2, 3});
        user.setEnumTest(EnumTest.ONE);

        Map<String, Role> roleMap = new HashMap<>();
        roleMap.put("a", new Role(2, "aAdmin"));
        roleMap.put(null, new Role(3, "bAdmin"));

        user.setRoleMap(roleMap);

        System.out.println(JSON.toJSONString(user));
```

# 题目二

标题：资金分解

难度系数：1.0
