package ch02_collections.extension;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * Forwarding装饰器
 * 通过创建ForwardingXXX的子类并实现delegate()方法，可以选择性地覆盖子类的方法来增加装饰功能，而不需要自己委托每个方法
 * @author
 * @create 2018-05-07 下午11:33
 **/
class AddLoggingList<E> extends ForwardingList<E> {
    final List<E> delegate = Lists.newArrayList(); // backing list
    @Override
    protected List<E> delegate() {
        return delegate;
    }
    @Override
    public void add(int index, E elem) {
        log(index, elem);
        super.add(index, elem);
    }
    @Override
    public boolean add(E elem) {
        return standardAdd(elem); // implements in terms of add(int, E)
    }
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return standardAddAll(c); // implements in terms of add
    }

    private void log(int index, E elem) {
        System.out.println(String.format("add %s in %d", elem, index));
    }
}
