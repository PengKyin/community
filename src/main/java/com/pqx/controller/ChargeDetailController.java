package com.pqx.controller;


import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.ChargeDetail;
import com.pqx.service.ChargeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detail")
public class ChargeDetailController {
    @Autowired
    private ChargeDetailService chargeDetailService;

    @RequestMapping("/find")
    public Result find(){
        List<ChargeDetail> all = chargeDetailService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<ChargeDetail> page = chargeDetailService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody ChargeDetail chargeDetail){
        chargeDetailService.add(chargeDetail);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        ChargeDetail chargeDetail = chargeDetailService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,chargeDetail);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody ChargeDetail chargeDetail){
        chargeDetailService.update(chargeDetail);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }
    

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = chargeDetailService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }
}
