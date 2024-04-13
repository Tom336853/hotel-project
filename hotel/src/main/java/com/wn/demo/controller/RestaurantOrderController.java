package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.FinanceMapper;
import com.wn.demo.mapper.RestaurantOrderMapper;
import com.wn.demo.pojo.Finance;
import com.wn.demo.pojo.RestaurantOrder;
import com.wn.demo.pojo.RoomOrder;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/restaurantOrder")
public class RestaurantOrderController {

    @Autowired
    RestaurantOrderMapper restaurantOrderMapper;

    @Autowired
    FinanceMapper financeMapper;

    @GetMapping("/index")
    public String index() {
        return "restaurantOrder/restaurantOrder-list";
    }


    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<RestaurantOrder> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<RestaurantOrder> siteList= restaurantOrderMapper.selectAll(null);
        PageInfo<RestaurantOrder> userPageInfo = new PageInfo<>(siteList);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        restaurantOrderMapper.delete(id);
        return ResultBean.success();
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResultBean update(@PathVariable("id") Integer id) {
        RestaurantOrder order = restaurantOrderMapper.selectOne(id);
        if (!order.getStatus().equals("已预约")) {
            return ResultBean.error("无需操作");
        }
        RestaurantOrder  restaurantOrder = new RestaurantOrder();
        restaurantOrder.setId(id);
        restaurantOrder.setStatus("已完成");
        restaurantOrderMapper.update(restaurantOrder);

        Finance finance = new Finance();
        finance.setDataType("收入");
        finance.setInfo("餐厅收入");
        finance.setMoney(order.getPrice()+"");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        finance.setMonthDate(sdf.format(new Date()));
        financeMapper.add(finance);
        return ResultBean.success();
    }

}
