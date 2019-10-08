package race.question.demo.json;

import com.google.gson.Gson;
import race.question.demo.pojo.EnumTest;
import race.question.demo.pojo.JsonRootBean;
import race.question.demo.pojo.Role;
import race.question.demo.pojo.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linyh
 */
public class Test {

    public static void main(String[] args) throws Exception {

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
        System.out.println(com.alibaba.fastjson.JSON.toJSONString(user));

        Gson gson = new Gson();
        System.out.println(gson.toJson(user));

        //{"UID":"48d4624ad0c8475094d00fc769c7129f","Name":"blqw","Birthday":"\/Date(530964000000+0800)\/","Sex":"Male","IsDeleted":false,"LoginHistory":["\/Date(1377129600000+0800)\/","\/Date(1377137410000+0800)\/","\/Date(1377146036000+0800)\/","\/Date(1377163518000+0800)\/","\/Date(1377184019000+0800)\/"],"Info":{"Address":"广东省广州市","Phone":{"手机":"18688888888","电话":"82580000","短号":"10086","QQ":"21979018"},"ZipCode":510000}}

        JsonRootBean jsonRootBean = gson.fromJson("{\"UID\":\"48d4624ad0c8475094d00fc769c7129f\",\"Name\":\"blqw\",\"Birthday\":\"Date(530964000000+0800)\",\"Sex\":\"Male\",\"IsDeleted\":false,\"LoginHistory\":[\"Date(1377129600000+0800)\",\"Date(1377137410000+0800)\",\"Date(1377146036000+0800)\",\"Date(1377163518000+0800)\",\"Date(1377184019000+0800)\"],\"Info\":{\"Address\":\"广东省广州市\",\"Phone\":{\"手机\":\"18688888888\",\"电话\":\"82580000\",\"短号\":\"10086\",\"QQ\":\"21979018\"},\"ZipCode\":510000}}", JsonRootBean.class);

        System.out.println(jsonRootBean);

        System.out.println(JSON.toJSONString(jsonRootBean));
        System.out.println(com.alibaba.fastjson.JSON.toJSONString(jsonRootBean));
        System.out.println(gson.toJson(jsonRootBean));

        Role role = new Role();
        role.setDate(new Date());

        System.out.println(JSON.toJSONString(role));
        System.out.println(gson.toJson(role));
        System.out.println(com.alibaba.fastjson.JSON.toJSONString(role));

//        long starttime1 = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            com.alibaba.fastjson.JSON.toJSONString(user);
//        }
//        System.out.println("fastJson cost: " + (System.currentTimeMillis() - starttime1));
//
//        long starttime2 = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            JSON.toJSONString(user);
//        }
//        System.out.println("custom cost: " + (System.currentTimeMillis() - starttime2));

//        asmSerializer.write(new JSONSerializer(new SerializeWriter(), SerializeConfig.globalInstance), user);
    }
}
