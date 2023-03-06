package pers.kedis.core.command;

import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;

/**
 * @author kwsc98
 */
public abstract class CommandAbstract implements Command {

    protected KedisData getNullKedisData() {
        return new KedisData(DataType.BULK_STRING).setData(null);
    }

    protected KedisData getSuccessKedisData() {
        return new KedisData(DataType.INTEGER).setData(1L);
    }

    protected KedisData getErrorKedisData() {
        return new KedisData(DataType.INTEGER).setData(0L);
    }

}
