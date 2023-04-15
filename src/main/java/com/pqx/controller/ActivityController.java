package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Activity;
import com.pqx.service.ActivityService;
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
@RequestMapping("/activity")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ParkingService parkingService;

    @RequestMapping("/find")
    public Result find(){
        List<Activity> all = activityService.findAll();
        return new Result(false,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Activity> page = activityService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Activity activity) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        activity.setCreateTime(date);
        activity.setUpdateTime(date);
        Integer communityId = parkingService.getCommunityId(activity.getCommunityName());
        activity.setCommunityId(communityId);
        activityService.add(activity);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Activity activity = activityService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,activity);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Activity activity) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        activity.setUpdateTime(date);
        activityService.update(activity);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }

    @RequestMapping("/updateStatus/{status}/{id}")
    public Result updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag  = activityService.updateStatus(status,id);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }

    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = activityService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }
}
