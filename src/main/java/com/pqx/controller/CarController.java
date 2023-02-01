package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Car;
import com.pqx.service.CarService;
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
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("/find")
    public Result find(){
        List<Car> all = carService.findAll();
        boolean flag = all.isEmpty();
        return new Result(flag,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Car> page = carService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.CAR_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Car car) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        car.setCreateTime(date);
        car.setUpdateTime(date);
        Integer ownerId = carService.getOwnerId(car.getOwnerName());
        car.setOwnerId(ownerId);
        carService.add(car);
        return new Result(true,StatusCode.OK,MessageConstant.CAR_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Car car = carService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.CAR_FIND_BY_ID_SUCCESS,car);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Car car){
        carService.update(car);
        return new Result(true,StatusCode.OK,MessageConstant.CAR_UPDATE_SUCCESS);
    }


    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = carService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.CAR_DELETE_SUCCESS);
    }

    @RequestMapping("/getOwner")
    public Result getOptionsList(){
        List<String> owner = carService.getOwner();
        return new Result(true,1000,"good",owner);
    }
}
