package basic.throwables;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.sql.SQLException;

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

            Throwables.getRootCause(t);
            Throwables.getCausalChain(t);
            Throwables.getStackTraceAsString(t);

            Throwables.throwIfInstanceOf(t, IOException.class);
            Throwables.throwIfInstanceOf(t, SQLException.class);
            Throwables.throwIfUnchecked(t);
            throw new RuntimeException(t);
        }
    }
}
