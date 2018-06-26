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

    static class Event {
        String msg;
        public Event(String msg) {
            this.msg = msg;
            System.out.println("构造ChangeEvent: " + msg);
        }
    }

    static class EventBusChangeRecorder {
        @Subscribe
        public void recordCustomerChange(Event e) {
            System.out.println("接收到消息: " + e.msg);
        }
    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus("test");
        EventBusChangeRecorder changeRecorder = new EventBusChangeRecorder();

        eventBus.register(changeRecorder);
        eventBus.post(new Event("中奖啦！"));
    }
}

