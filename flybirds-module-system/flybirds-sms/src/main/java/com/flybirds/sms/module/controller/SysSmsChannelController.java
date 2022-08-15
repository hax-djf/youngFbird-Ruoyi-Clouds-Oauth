package com.flybirds.sms.module.controller;

import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.sms.module.convert.SysSmsChannelConvert;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelCreateReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelPageReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelRespVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;
import com.flybirds.sms.module.service.SysSmsChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static com.flybirds.common.util.result.Result.ok;

@Api(tags = "短信渠道")
@RestController
@RequestMapping("channel")
public class SysSmsChannelController extends MpBaseController<SysSmsChannel> {

    @Autowired
    private SysSmsChannelService smsChannelService;

    @PostMapping("/save")
    @ApiOperation("创建短信渠道——未调试接口")
    @PreAuthorize(hasPermi = "sms:channel:add")
    public Result<?> createSmsChannel(@Valid @RequestBody SysSmsChannel sysSmsChannel) {

        if (StringUtils.isNull(sysSmsChannel)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_ENTITY);
        }
        return smsChannelService.saveSmsChannel(sysSmsChannel);
    }

    @PostMapping("/create")
    @ApiOperation("创建短信渠道")
    @PreAuthorize(hasPermi = "sms:channel:add")
    public Result<Long> createSmsChannel(@Valid @RequestBody SysSmsChannelCreateReqVO createReqVO) {

        return ok(smsChannelService.createSmsChannel(createReqVO));
    }


    @PutMapping("/update")
    @ApiOperation("更新短信渠道--未调试接口")
    @PreAuthorize(hasPermi = "sms:channel:edit")
    public Result<?> updateSmsChannel(@Valid @RequestBody SysSmsChannelUpdateReqVO updateReqVO) {

        if (StringUtils.isNull(updateReqVO)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_ENTITY);
        }
        return smsChannelService.updateSmsChannel(updateReqVO);

    }

    @DeleteMapping("/deleteByPrimarykeyId")
    @ApiOperation("删除短信渠道")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize(hasPermi ="sms:channel:delete")
    public Result<?> deleteByPrimarykeyId(@RequestParam("id") Long id) {
        if (StringUtils.isNull(id)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_ID);
        }
       return  smsChannelService.deleteSmsChannel(id);
    }

    @GetMapping("/queryByPrimarykeyId")
    @ApiOperation("获得短信渠道")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize(hasPermi = "sms:channel:query")
    public Result<SysSmsChannel> getSmsChannel(@RequestParam("id") Long id) {

        if (StringUtils.isNull(id)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_ID);
        }
        return ok(smsChannelService.getSmsChannel(id));
    }

    @GetMapping("/queryByParams")
    @ApiOperation("获得短信渠道分页")
    @PreAuthorize(hasPermi = "sms:channel:query")
    public MpTableDataInfo getSmsChannelPage(@Valid @RequestBody Params params) {

        if (StringUtils.isNull(params)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_PARAMS);
        }
        initParameter(params);
        return getMpDataTable(smsChannelService.getSmsChannelPage(params));
    }

    @GetMapping("/page")
    @ApiOperation("获得短信渠道分页")
    @PreAuthorize(hasPermi = "sms:channel:query")
    public Result<PageResult<SysSmsChannelRespVO>> getSmsChannelPage(@Valid SysSmsChannelPageReqVO pageVO) {
        PageResult<SysSmsChannel> pageResult = smsChannelService.getSmsChannelPage(pageVO);
        return ok(SysSmsChannelConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得短信渠道精简列表", notes = "包含被禁用的短信渠道")
    public Result<List<SysSmsChannel>> getSimpleSmsChannels() {

        List<SysSmsChannel> list = smsChannelService.getSmsChannelList();
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysSmsChannel::getId));

        return ok(list);
    }

}
