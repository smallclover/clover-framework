package org.clover4j.framework.bean;

/**
 * 数据对象 返回值为JSON
 * @author smallclover
 * @create 2017-01-08
 * @since 1.0.0
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model){
        this.model = model;
    }

    public Object getModel(){
        return model;
    }

}
