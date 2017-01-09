package org.clover4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author smallclover
 * @create 2017-01-08
 */
public class View {

    /**
     * 视图路径
     */
    private String path;

    private Map<String, Object> model;

    /**
     * 模型数据
     * @param path
     */
    public View(String path){
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath(){
        return  path;
    }

    public Map<String, Object> getModel(){
        return model;
    }


}
