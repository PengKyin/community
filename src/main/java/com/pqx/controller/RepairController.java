package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Repair;
import com.pqx.service.*;
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
@RequestMapping("/repair")
public class RepairController {
    
    @Autowired
    private RepairService repairService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private CarService carService;

    @RequestMapping("/find")
    public Result find(){
        List<Repair> all = repairService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Repair> page = repairService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Repair repair) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);

        Integer communityId = parkingService.getCommunityId(repair.getCommunityName());
        repair.setCommunityId(communityId);

        Integer ownerId = carService.getOwnerId(repair.getOwnerName());
        repair.setOwnerId(ownerId);

        Integer buildingId = buildingService.getBuildingId(repair.getBuildingName());
        repair.setBuildingId(buildingId);

        repair.setCreateTime(date);
        repair.setUpdateTime(date);
        repairService.add(repair);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Repair repair = repairService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,repair);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Repair repair) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        repair.setUpdateTime(date);
        repairService.update(repair);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }

    @RequestMapping("/updateStatus/{status}/{id}")
    public Result updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag  = repairService.updateStatus(status,id);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = repairService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }

    @RequestMapping("/getBuildingName")
    public Result getBuildingName(@RequestBody Repair repair){
        List<String> buildingName = buildingService.getBuildingName(repair.getCommunityName());
        return new Result(true,200,"获取成功",buildingName);
    }
    @RequestMapping("/getCommunityName")
    public Result getCommunity(){
        List<String> communityName = communityService.getCommunityName();
        return new Result(true,200,"获取成功",communityName);
    }
}
