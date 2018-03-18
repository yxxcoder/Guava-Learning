package basic.throwables;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 简化异常和错误的传播与检查
 * @author yuxuan
 * @create 2018-03-12 下午11:01
 **/
public class ThrowablesExplained {
    public static void main(String args[]) throws Exception {
        try {
            // someMethodThatCouldThrowAnything();
        } catch (NullPointerException e) {
            // do something
            // handle(e);
        } catch (Throwable t) {

            // 获取异常原因链
            Throwable throwable = Throwables.getRootCause(t);
            List<Throwable> throwables = Throwables.getCausalChain(t);
            String str = Throwables.getStackTraceAsString(t);

            // 类型为 X 时才抛出
            Throwables.throwIfInstanceOf(t, SQLException.class);

            // 类型为Error或RuntimeException时抛出
            Throwables.throwIfUnchecked(t);

            // AssertionError 是 Error 类的子类，属于错误非异常，主要由JDK开发人员使用
            // 一般在程序认为“不可能发生”的地方使用此断言
            throw new AssertionError(t);
        }
    }
}
