package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.House;
import com.pqx.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("/find")
    public Result find(){
        List<House> all = houseService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        System.out.println(searchMap);
        Page<House> page = houseService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.House_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody House house) {
        Boolean add = houseService.add(house);
        return new Result(add,StatusCode.OK,MessageConstant.House_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        House house = houseService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.House_FIND_BY_ID_SUCCESS,house);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody House house) {
        System.out.println(house);
        Boolean flag = houseService.update(house);
        return new Result(flag,StatusCode.OK,MessageConstant.House_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = houseService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.House_DELETE_SUCCESS);
    }

    @RequestMapping("/getOptions")
    public Result getOptionsList(){
        List<String> option = houseService.getOptions();
        return new Result(true,1000,MessageConstant.House_GET_STATUS_SUCCESS,option);
    }

    @RequestMapping("/getBuildingName")
    public Result getBuildingName(@RequestBody House house){
        System.out.println(house);
        List<String> buildingName = houseService.getBuildingName(house);
        return new Result(true,1000,MessageConstant.House_GET_STATUS_SUCCESS,buildingName);
    }
}
