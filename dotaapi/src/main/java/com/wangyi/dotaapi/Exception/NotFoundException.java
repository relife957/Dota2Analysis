package com.wangyi.dotaapi.Exception;

/**
 * @author wangyi
 * @create 2019-01-18 4:06 PM
 **/
public class NotFoundException extends RuntimeException{
    private Object input ;

    public NotFoundException(Object input) {
        this.input = input;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }
}
