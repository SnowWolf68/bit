import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
class Person{
    public String id;

    public Person(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
public class test {
    public static void main(String[] args) {
        HashBuck<Person,String> hashBuck = new HashBuck<>();
        hashBuck.put(new Person("1"),"a");
        hashBuck.put(new Person("2"),"b");
        hashBuck.put(new Person("3"),"qw");
        hashBuck.put(new Person("13"),"e");
        hashBuck.put(new Person("5"),"r");
        hashBuck.put(new Person("6"),"t");
        hashBuck.put(new Person("177"),"y");
        System.out.println(hashBuck.getArrayLength());
        hashBuck.put(new Person("8"),"u");
        System.out.println(hashBuck.getArrayLength());
        hashBuck.put(new Person("9"),"i");
        System.out.println(hashBuck.getArrayLength());
    }
    public static void main3(String[] args) {
        Person person1 = new Person("1234");
        Person person2 = new Person("1234");
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());
        HashBuck<Person,String> hashBuck = new HashBuck();
        hashBuck.put(person1,"aaa");
        String name = hashBuck.get(person2);
        System.out.println(name);
    }
    public static void main2(String[] args) {
        HashBucket hashBucket = new HashBucket();
        hashBucket.put(1,11);
        hashBucket.put(2,22);
        hashBucket.put(12,122);
        hashBucket.put(3,33);
        hashBucket.put(4,44);
        hashBucket.put(14,144);
        hashBucket.put(5,55);
        hashBucket.put(6,166);
    }
    public static void main1(String[] args) {
        Map<String,Integer> map = new TreeMap<>();
        map.put("hello",2);
        map.put("aaa",3);

        System.out.println(map.get("aaa"));

        //System.out.println("aaaa");
    }
}
