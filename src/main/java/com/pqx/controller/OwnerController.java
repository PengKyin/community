package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.House;
import com.pqx.pojo.Owner;
import com.pqx.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;

    @RequestMapping("/find")
    public Result find(){
        List<Owner> all = ownerService.findAll();
        boolean flag = all.isEmpty();
        return new Result(flag,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Owner> page = ownerService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.OWNER_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Owner owner) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        owner.setCreateTime(date);
        owner.setUpdateTime(date);
        ownerService.add(owner);
        return new Result(true,StatusCode.OK,MessageConstant.OWNER_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Owner owner = ownerService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.OWNER_FIND_BY_ID_SUCCESS,owner);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Owner owner){
        ownerService.update(owner);
        return new Result(true,StatusCode.OK,MessageConstant.OWNER_UPDATE_SUCCESS);
    }


    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = ownerService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.OWNER_DELETE_SUCCESS);
    }

    @RequestMapping("/getCommunity")
    public Result getOptionsList(){
        List<Integer> community = ownerService.getCommunity();
        return new Result(true,1000,"good",community);
    }

    @RequestMapping("/getHouse")
    public Result getBuildingName(@RequestBody Owner owner){
        Integer communityId = owner.getCommunityId();
        List<Integer> house = ownerService.getHouse(communityId);
        return new Result(true,1000,MessageConstant.House_GET_STATUS_SUCCESS,house);
    }
}
