package ch11_event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;

/**
 * Java原生事件机制
 * java的事件机制一般包括三个部分：EventObject，EventListener和Source
 *
 * @author yuxuan
 * @create 2018-06-27 下午11:51
 **/
public class JavaEventExplained {

    /**
     * 事件源
     * 事件源不需要实现或继承任何接口或类，它是事件最初发生的地方
     * 因为事件源需要注册事件监听器，所以事件源内需要有相应的盛放事件监听器的容器
     */
    static public class Source {

        private int flag = 0;
        Set<EventListener> listeners = new HashSet<EventListener>();

        /**
         * 注册事件监听器
         *
         * @param listener
         */
        public void addStateChangeListener(StateChangeListener listener) {
            listeners.add(listener);
        }

        /**
         * 注册事件监听器
         *
         * @param listener
         */
        public void addStateChangeToOneListener(StateChangeToOneListener listener) {
            listeners.add(listener);
        }

        /**
         * 当事件发生时，通知注册在事件源上的所有事件做出相应的反映
         */
        public void notifyListener() {
            for (EventListener listener : listeners) {
                try {
                    ((StateChangeListener)listener).handleEvent(new MyEvent(this));
                } catch (Exception e) {
                    if (flag == 1) {
                        ((StateChangeToOneListener)listener).handleEvent(new MyEvent(this));
                    }
                }
            }
        }

        /**
         * 改变状态
         */
        public void changeFlag() {
            flag = (flag == 0 ? 1 : 0);
            notifyListener();
        }

        public int getFlag() {
            return flag;
        }
    }

    /**
     * 事件对象
     * java.util.EventObject是事件状态对象的基类，它封装了事件源对象以及和事件相关的信息。所有java的事件类都需要继承该类
     */
    static class MyEvent extends EventObject {

        private static final long serialVersionUID = 1L;
        private int sourceState;

        public MyEvent(Object source) {
            super(source);
            sourceState = ((Source)source).getFlag();
        }

        public int getSourceState() {
            return sourceState;
        }

    }

    /**
     * 事件监听器
     * java.util.EventListener是一个标记接口，就是说该接口内是没有任何方法的
     * 所有事件监听器都需要实现该接口。事件监听器注册在事件源上，当事件源的属性或状态改变的时候，调用相应监听器内的回调方法
     */
    static class StateChangeListener implements EventListener {

        public void handleEvent(MyEvent event) {
            System.out.println("触发状态改变事件");
            System.out.println("当前事件源状态为: " + event.getSourceState());
            System.out.println();
        }
    }

    static class StateChangeToOneListener implements EventListener {

        public void handleEvent(MyEvent event) {
            System.out.println("触发状态变为1的事件");
            System.out.println("当前事件源状态为: " + event.getSourceState());
            System.out.println();
        }

    }


    public static void main(String[] args) {

        Source source = new Source();
        source.addStateChangeListener(new StateChangeListener());
        source.addStateChangeToOneListener(new StateChangeToOneListener());

        System.out.println("开始改变: ");
        source.changeFlag();
        System.out.println("开始改变: ");
        source.changeFlag();
        System.out.println("开始改变: ");
        source.changeFlag();
    }
}
