package basic.ordering;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * 比较年龄
 * @author yuxuan
 * @create 2018-03-10 下午10:07
 **/
public class PeopleAgeOrder extends Ordering<People> {
    public int compare(People left, People right) {
        return Ints.compare(left.age, right.age);
    }
}
