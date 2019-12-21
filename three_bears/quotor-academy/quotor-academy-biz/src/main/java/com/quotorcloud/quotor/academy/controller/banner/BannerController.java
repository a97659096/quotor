package com.quotorcloud.quotor.academy.controller.banner;


import com.quotorcloud.quotor.academy.api.dto.banner.BannerDTO;
import com.quotorcloud.quotor.academy.service.banner.BannerService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * banner管理 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-02
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("save")
    public R saveBanner(BannerDTO bannerDTO){
        return R.ok(bannerService.saveBanner(bannerDTO));
    }

    @GetMapping("list")
    public R listBanner(BannerDTO bannerDTO){
        return R.ok(bannerService.listBanner(bannerDTO));
    }

    @GetMapping("list/{id}")
    public R selectByBannerId(@PathVariable String id){
        return R.ok(bannerService.selectBannerById(id));
    }

    @GetMapping("list/shop/{shopId}")
    public R selectBannerByShopId(@PathVariable String shopId){
        return R.ok(bannerService.selectBannerByShopId(shopId));
    }

    @PutMapping("update")
    public R updateBanner(BannerDTO bannerDTO){
        return R.ok(bannerService.updateBanner(bannerDTO));
    }

    @DeleteMapping("{id}")
    public R removeBanner(@PathVariable String id){
        return R.ok(bannerService.removeBanner(id));
    }
}
