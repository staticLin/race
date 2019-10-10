# race
1024程序员比赛

# 题目一

标题：json框架编写

题目描述：

编写一个json序列化框架，要求能够实现POJO对象到json字符串的转化。

+ 如下调用api完成pojo到json的序列化
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

题目描述：

NC 是一个资金分解系统，资金分解的作用是将执收区划的一笔资金，分解到多个收入区划中。

+ 主类为race.question.demo.deliver.NC
+ 调用Test类即可测试结果
```java
Integer projectId = 1;
String zoneName = "福州市";
BigDecimal deliverAmount = new BigDecimal("10");
BigDecimal stander = new BigDecimal("10");
BigDecimal totalAmount = new BigDecimal("100");

Result result = NC.deliver(projectId, zoneName, deliverAmount, stander, totalAmount);
if (result != null) {
    result.print();
}
```

