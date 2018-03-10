package basic.ordering;

import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * 比较名字的长度
 * @author yuxuan
 * @create 2018-03-10 下午10:07
 **/
public class PeopleNameLengthOrder extends Ordering<People> {
    public int compare(People left, People right) {
        return Ints.compare(left.name.length(), right.name.length());
    }
}
