package ch11_event;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


/**
 * 事件总线
 *
 * @author
 * @create 2018-06-24 下午10:01
 **/
public class EventBusExplained {

    /**
     * 自定义事件
     */
    static class Event {
        String msg;
        public Event(String msg) {
            this.msg = msg;
            System.out.println("构造ChangeEvent: " + msg);
        }
    }

    /**
     * 事件监听者[Listeners]
     * 只需在指定的方法上加上@Subscribe注解即可
     */
    static class EventBusChangeRecorder {
        @Subscribe
        public void recordCustomerChange(Event e) {
            System.out.println("接收到消息: " + e.msg);
        }
        @Subscribe
        public void listenInteger(Integer event) {
            System.out.println("event Integer:" + event);
        }

        @Subscribe
        public void listenLong(Long event) {
            System.out.println("event Long:" + event);
        }
    }

    public static void main(String[] args) {

        /**
         * Guava事件总线
         */
        EventBus eventBus = new EventBus("test");
        EventBusChangeRecorder changeRecorder = new EventBusChangeRecorder();

        // 把事件监听者注册到事件生产者
        eventBus.register(changeRecorder);
        // 向监听者分发事件
        eventBus.post(new Event("中奖啦！"));
        eventBus.post(new Integer(300));
        eventBus.post(new Long(800));
    }
}

