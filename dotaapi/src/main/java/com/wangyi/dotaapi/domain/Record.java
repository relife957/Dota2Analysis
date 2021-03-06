package com.wangyi.dotaapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wangyi
 * @create 2019-11-06 4:53 PM
 **/
@Data
@ApiModel("查询记录")
public class Record implements Validate{
    private int id;
    @ApiModelProperty("查询输入")
    private String input ;
    @ApiModelProperty("查询输出")
    private String output;
    @ApiModelProperty("查询时间")
    private Date addTime;

    public boolean validate(){
        return input!=null && output!=null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Record)){
            return false;
        }
        Record other = (Record)obj ;
        return this.getInput().equals(other.getInput()) &&
                this.getOutput().equals(other.getOutput());
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = result * 31 + this.getInput().hashCode();
        result = result * 31 + this.getOutput().hashCode();

        return result;

    }
}
