package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.FinanceMapper;
import com.wn.demo.mapper.RestaurantOrderMapper;
import com.wn.demo.mapper.RoomMapper;
import com.wn.demo.mapper.RoomOrderMapper;
import com.wn.demo.pojo.*;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/roomOrder")
public class RoomOrderController {

    @Autowired
    RoomOrderMapper roomOrderMapper;

    @Autowired
    FinanceMapper financeMapper;

    @Autowired
    RoomMapper roomMapper;

    @GetMapping("/index")
    public String index() {
        return "roomOrder/roomOrder-list";
    }

    @GetMapping
    public String add(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());
        String endTime = sdf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L));
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "roomOrder/roomOrder-add2";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<RoomOrder> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<RoomOrder> siteList= roomOrderMapper.selectAll(null);
        PageInfo<RoomOrder> userPageInfo = new PageInfo<>(siteList);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("roomOrderInfo", roomOrderMapper.selectOne(id));
        return "roomOrder/roomOrder-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(RoomOrder roomOrder) {
        RoomOrder order = roomOrderMapper.selectOne(roomOrder.getId());
        if (!order.getStatus().equals("已预约")) {
            return ResultBean.error("无需操作");
        }
        roomOrder.setStatus("已完成");
        roomOrderMapper.update(roomOrder);
        Finance finance = new Finance();
        finance.setDataType("收入");
        finance.setInfo("客房收入");
        finance.setMoney(order.getPrice()+"");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        finance.setMonthDate(sdf.format(new Date()));
        financeMapper.add(finance);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        roomOrderMapper.delete(id);
        return ResultBean.success();
    }

    @GetMapping(value = {"/search"})
    @ResponseBody
    public List<Room> search(Room room, HttpServletRequest request) {
        String startTime = room.getStartTime();
        String endTime = room.getEndTime();
        room.setStartTime(startTime + " 12:00:00");
        room.setEndTime(endTime + " 12:00:00");
        List<Room> roomList = roomMapper.selectAll2(room);
        room.setStartTime(startTime);
        room.setEndTime(endTime);
        return roomList;
    }

    @PostMapping("/addRoomOrder")
    @ResponseBody
    public ResultBean addRoomOrder(RoomOrder roomOrder, HttpServletRequest request, Model model){
        //Room room = roomMapper.selectByRoomNo(roomOrder.getRoomNo());
        roomOrder.setOrderNo(System.currentTimeMillis()+"");
        roomOrder.setStartTime(roomOrder.getStartTime()+" 12:00:00");
        roomOrder.setEndTime(roomOrder.getEndTime()+" 12:00:00");

        Finance finance = new Finance();
        finance.setDataType("收入");
        finance.setInfo("客房收入");
        finance.setMoney(roomOrder.getPrice()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        finance.setMonthDate(sdf.format(new Date()));
        financeMapper.add(finance);

        return ResultBean.success(roomOrderMapper.add2(roomOrder));
    }

}
