package race.question.demo.pojo;

import java.util.List;
import java.util.Map;

public class User {

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    private Boolean test;

    private int age;
    private short a;
    private byte b;
    private long c;
    private double d;
    private boolean e;
    private char f;
    private float g;
    private char[] charArray;
    private byte[] array;
    private int[] intarray;

    public char[] getCharArray() {
        return charArray;
    }

    public void setCharArray(char[] charArray) {
        this.charArray = charArray;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public int[] getIntarray() {
        return intarray;
    }

    public void setIntarray(int[] intarray) {
        this.intarray = intarray;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }

    public short getA() {
        return a;
    }

    public void setA(short a) {
        this.a = a;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public boolean isE() {
        return e;
    }

    public void setE(boolean e) {
        this.e = e;
    }

    public char getF() {
        return f;
    }

    public void setF(char f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private List<Role> roles;

    private Map<String, Role> roleMap;

    private List<String> strings;

    private EnumTest enumTest;

    public EnumTest getEnumTest() {
        return enumTest;
    }

    public void setEnumTest(EnumTest enumTest) {
        this.enumTest = enumTest;
    }

    public Map<String, Role> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, Role> roleMap) {
        this.roleMap = roleMap;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                ", roleMap=" + roleMap +
                ", strings=" + strings +
                ", enumTest=" + enumTest +
                '}';
    }
}
