package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.RestaurantMapper;
import com.wn.demo.pojo.Restaurant;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantMapper restaurantMapper;

    @GetMapping("/index")
    public String index() {
        return "restaurant/restaurant-list";
    }

    @GetMapping
    public String add() {
        return "restaurant/restaurant-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Restaurant> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        Restaurant restaurant) {
        PageHelper.startPage(page, limit);
        List<Restaurant> sites = restaurantMapper.selectAll(restaurant);
        PageInfo<Restaurant> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(Restaurant restaurant, HttpServletRequest request) {
        Restaurant flag = restaurantMapper.selectByRestNo(restaurant.getRestNo());
        if (flag!=null) {
            return ResultBean.error("餐厅号重复");
        }
        return ResultBean.success(restaurantMapper.add(restaurant));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("restaurantInfo", restaurantMapper.selectOne(id));
        return "restaurant/restaurant-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Restaurant restaurant) {
        restaurantMapper.update(restaurant);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        restaurantMapper.delete(id);
        return ResultBean.success();
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> image(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        String filename = "";
        String localPath = "D:\\pet_img\\"; //存放我们上传的头像

        try {
            if (file != null) {
                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType = file.getContentType();
                //获得文件后缀名
                String suffixName = contentType.substring(contentType.indexOf("/") + 1);
                //得到 文件名(随机数+uuid+后缀)
                filename = (int) ((Math.random()) * 100000000) + uuid + "." + suffixName;

//            判断是否有这个文件夹，没有就创建
                if (!new File(localPath).exists()) {
                    new File(localPath).mkdirs();
                }
//            复制到当前文件夹
                file.transferTo(new File(localPath + filename));
            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "");
            map.put("data", map2);
            map2.put("src", "/pet_img/"+filename);
            //  System.out.println("上传失败");
            return map;
        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", map2);
        map2.put("src", "/pet_img/"+filename);
        // System.out.println("上传成功:"+filename);
        return map;
    }

}
