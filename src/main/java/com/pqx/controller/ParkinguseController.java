package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.ParkingUse;
import com.pqx.service.CarService;
import com.pqx.service.ParkingService;
import com.pqx.service.ParkinguseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parkinguse")
public class ParkinguseController {
    @Autowired
    private ParkinguseService parkinguseService;
    @Autowired
    private CarService carService;
    @Autowired
    private ParkingService parkingService;


    @RequestMapping("/find")
    public Result find(){
        List<ParkingUse> all = parkinguseService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<ParkingUse> page = parkinguseService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody ParkingUse parkingUse) throws ParseException {
        Integer ownerId = carService.getOwnerId(parkingUse.getOwnerName());
        parkingUse.setOwnerId(ownerId);

        Integer communityId = parkingService.getCommunityId(parkingUse.getCommunityName());
        parkingUse.setCommunityId(communityId);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        parkingUse.setCreateTime(date);
        parkingUse.setUpdateTime(date);
        parkingUse.setStartTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(parkingUse.getUseType());
        if (parkingUse.getUseType().equals("0")){
            calendar.add(Calendar.YEAR,-1);
        }else if (parkingUse.getUseType().equals("1")){
            calendar.add(Calendar.MONTH,1);
        }else if (parkingUse.getUseType().equals("2")){
            calendar.add(Calendar.YEAR,1);
        }
        parkingUse.setEndTime(calendar.getTime());
        parkinguseService.add(parkingUse);
        parkingService.updateByCode("0",parkingUse.getCode());
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        ParkingUse parkingUse = parkinguseService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,parkingUse);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody ParkingUse parkingUse){
        parkinguseService.update(parkingUse);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }

    @RequestMapping("/updateStatus/{status}/{id}")
    public Result updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag  = parkinguseService.updateStatus(status,id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = parkinguseService.del(ids);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }

    @RequestMapping("/getParking")
    public Result getParking(@RequestBody ParkingUse parkingUse){
        System.out.println(parkingUse.getCommunityName());
        List<String> parking = parkinguseService.getParking(parkingUse.getCommunityName());
        return new Result(true,200,"查询成功",parking);
    }
}
