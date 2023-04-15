package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Complaint;
import com.pqx.pojo.Device;
import com.pqx.service.DeviceService;
import com.pqx.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ParkingService parkingService;

    @RequestMapping("/find")
    public Result find(){
        List<Device> all = deviceService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Device> page = deviceService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Device device) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        device.setCreateTime(date);
        device.setUpdateTime(date);

        Integer communityId = parkingService.getCommunityId(device.getCommunityName());
        device.setCommunityId(communityId);

        deviceService.add(device);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Device device = deviceService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,device);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Device device) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        device.setUpdateTime(date);
        Integer communityId = parkingService.getCommunityId(device.getCommunityName());
        device.setCommunityId(communityId);
        deviceService.update(device);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }


    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = deviceService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }
}
