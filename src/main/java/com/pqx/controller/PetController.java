package com.pqx.controller;

import com.github.pagehelper.Page;
import com.pqx.common.MessageConstant;
import com.pqx.common.PageResult;
import com.pqx.common.Result;
import com.pqx.common.StatusCode;
import com.pqx.pojo.Pet;
import com.pqx.service.CarService;
import com.pqx.service.PetService;
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
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private CarService carService;

    @RequestMapping("/find")
    public Result find(){
        List<Pet> all = petService.findAll();
        boolean flag = all.isEmpty();
        return new Result(flag,600,"成功",all);
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Pet> page = petService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.PET_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Pet pet) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dateFormat.format(System.currentTimeMillis());
        Date date = dateFormat.parse(date1);
        pet.setCreateTime(date);
        pet.setUpdateTime(date);
        Integer ownerId = carService.getOwnerId(pet.getOwnerName());
        pet.setOwnerId(ownerId);
        System.out.println(pet);
        petService.add(pet);
        return new Result(true,StatusCode.OK,MessageConstant.PET_ADD_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Pet pet = petService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.PET_FIND_BY_ID_SUCCESS,pet);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Pet pet){
        petService.update(pet);
        return new Result(true,StatusCode.OK,MessageConstant.PET_UPDATE_SUCCESS);
    }


    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = petService.del(ids);
        return new Result(flag,StatusCode.OK,MessageConstant.PET_DELETE_SUCCESS);
    }
}
