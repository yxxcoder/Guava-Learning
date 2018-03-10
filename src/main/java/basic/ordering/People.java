package basic.ordering;

import com.google.common.base.MoreObjects;

public class People {
    public String name;
    public int age;

    People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("name", name)
                .add("age", age)
                .toString();
    }
}
