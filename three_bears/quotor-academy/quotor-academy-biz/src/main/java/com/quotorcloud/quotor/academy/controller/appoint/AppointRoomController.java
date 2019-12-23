package com.quotorcloud.quotor.academy.controller.appoint;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.api.entity.appoint.AppointRoom;
import com.quotorcloud.quotor.academy.service.appoint.AppointRoomService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 房间信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
@RestController
@RequestMapping("/appoint/room")
public class AppointRoomController {

    @Autowired
    private AppointRoomService appointRoomService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @PostMapping
    public R saveRoom(@RequestBody AppointRoom appointRoom){
        shopSetterUtil.shopSetter(appointRoom, appointRoom.getShopId());
        return R.ok(appointRoomService.save(appointRoom));
    }

    @PutMapping
    public R updateRoom(@RequestBody AppointRoom appointRoom){
        return R.ok(appointRoomService.updateById(appointRoom));
    }

    @DeleteMapping("{id}")
    public R removeRoom(@PathVariable String id){
        return R.ok(appointRoomService.removeById(id));
    }

    @GetMapping("list")
    public R listRoom(AppointRoom appointRoom){
        shopSetterUtil.shopSetter(appointRoom, appointRoom.getShopId());
        return R.ok(appointRoomService.list(new QueryWrapper<AppointRoom>()
                .eq(!ComUtil.isEmpty(appointRoom.getShopId()), "shop_id", appointRoom.getShopId())));
    }
}
