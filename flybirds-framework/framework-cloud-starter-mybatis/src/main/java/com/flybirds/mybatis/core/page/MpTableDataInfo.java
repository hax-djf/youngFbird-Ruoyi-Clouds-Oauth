package com.flybirds.mybatis.core.page;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author ruoyi
 */
@Data
@Builder
public class MpTableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> data;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
