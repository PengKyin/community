package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Charge;
import com.pqx.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charge")
public class ChargeController {
    @Autowired
    private ChargeService chargeService;

    @RequestMapping("/find")
    public Result find(){
        List<Charge> all = chargeService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Charge> page = chargeService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Charge charge){
        chargeService.add(charge);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Charge charge = chargeService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,charge);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Charge charge){
        chargeService.update(charge);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }

    @RequestMapping("/updateStatus/{status}/{id}")
    public Result updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag  = chargeService.updateStatus(status,id);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = chargeService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }
}
