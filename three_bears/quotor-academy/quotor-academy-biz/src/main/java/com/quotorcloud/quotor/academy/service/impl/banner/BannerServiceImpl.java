package com.quotorcloud.quotor.academy.service.impl.banner;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.api.dto.banner.BannerDTO;
import com.quotorcloud.quotor.academy.api.entity.banner.Banner;
import com.quotorcloud.quotor.academy.mapper.banner.BannerMapper;
import com.quotorcloud.quotor.academy.service.banner.BannerService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * banner管理 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-02
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    private static final String IMG = "img/";

    private static Integer MAX_ENABLE_BANNER = 6;

    //TODO 店铺和添加人处理
    @Override
    public Boolean saveBanner(BannerDTO bannerDTO) {
        //一个店铺启用的banner不能超过六张
        if(!ComUtil.isEmpty(bannerDTO.getBShopId())){
            Integer enableCount = bannerMapper.selectCount(new QueryWrapper<Banner>()
                    .eq("b_shop_id", bannerDTO.getBShopId())
                    .eq("b_status", 1)
                    .eq("b_del_state", 0));
            if(enableCount >= MAX_ENABLE_BANNER){
                throw new MyException(ExceptionEnum.ENABLE_BANNER_OVER);
            }
        }
        //新增banner
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerDTO, banner, "bImg");

        //设置店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        banner.setBAddUserId(user.getId().toString());
        banner.setBAddUserName(user.getName());
        banner.setBShopId(String.valueOf(user.getDeptId()));
        banner.setBShopName(String.valueOf(user.getDeptName()));

        //处理图片
        FileUtil.saveFileAndField(banner, bannerDTO, "bImg",
                FileConstants.FileType.FILE_BANNER_IMG_DIR, null);
        banner.setBDelState(CommonConstants.STATUS_NORMAL);
        bannerMapper.insert(banner);
        return Boolean.TRUE;
    }

    @Override
    public JSONObject listBanner(BannerDTO bannerDTO) {
        //设置店铺信息,如果shopId为空 则查询的是当前账号所属店铺的banner信息
        if(ComUtil.isEmpty(bannerDTO.getBShopId())){
            QuotorUser user = SecurityUtils.getUser();
            if(ComUtil.isEmpty(user)){
                return null;
            }
            bannerDTO.setBShopId(String.valueOf(user.getDeptId()));
        }

        Page<Banner> page = PageUtil.getPage(bannerDTO.getPageNo(), bannerDTO.getPageSize());

        IPage<Banner> bannerIPage = bannerMapper.selectPage(page, new QueryWrapper<Banner>()
                .eq("b_del_state",0)
                .like(!ComUtil.isEmpty(bannerDTO.getBTitle()), "b_title", bannerDTO.getBTitle())
                .like(!ComUtil.isEmpty(bannerDTO.getBAddUserName()), "b_add_user_name", bannerDTO.getBAddUserName())
                .like(!ComUtil.isEmpty(bannerDTO.getBShopName()), "b_shop_name", bannerDTO.getBShopName())
                .eq("b_shop_id", bannerDTO.getBShopId())
                .orderByAsc("e_sort"));

        List<Banner> records = bannerIPage.getRecords();
        return PageUtil.getPagePackage("banners", records, page);
    }

    //TODO 店铺和添加人处理
    @Override
    public Boolean updateBanner(BannerDTO bannerDTO) {

        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        bannerDTO.setBAddUserId(user.getId().toString());
        bannerDTO.setBAddUserName(user.getName());

        Banner banner = bannerMapper.selectById(bannerDTO.getId());

        BeanUtils.copyProperties(bannerDTO, banner, "b_img");

        FileUtil.removeFileAndField(banner, bannerDTO, "bImg", FileConstants.FileType.FILE_BANNER_IMG_DIR);
        FileUtil.saveFileAndField(banner, bannerDTO, "bImg",
                FileConstants.FileType.FILE_BANNER_IMG_DIR, null);

        bannerMapper.updateById(banner);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeBanner(String id) {
        Banner banner = new Banner();
        banner.setId(id);
        banner.setBDelState(CommonConstants.STATUS_DEL);
        bannerMapper.updateById(banner);
        return Boolean.TRUE;
    }

    @Override
    public List<JSONObject> selectBannerByShopId(String shopId) {
        List<Banner> banners = bannerMapper.selectList(new QueryWrapper<Banner>().eq("b_shop_id", shopId)
                .eq("b_del_state", 0).orderByAsc("e_sort"));
        List<JSONObject> list = new ArrayList<>();
        for(Banner banner:banners){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", banner.getBImg().substring(banner.getBImg().lastIndexOf("/") + 1));
            jsonObject.put("url", banner.getBImg());
            jsonObject.put("link", banner.getBLink());
            list.add(jsonObject);
        }
        return list;
    }

    @Override
    public Banner selectBannerById(String id) {
        return bannerMapper.selectById(id);
    }
}
