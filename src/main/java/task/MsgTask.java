package task;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Data
public class MsgTask extends AbstractTask{

    private static Logger logger = LoggerFactory.getLogger(MsgTask.class);

    private long uid;

    private Method method;

    private Object[] params;

    @Override
    public void action() {

    }
}
