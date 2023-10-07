package com.qsiny.service.impl;

import com.qsiny.constant.CommonValues;
import com.qsiny.entity.*;
import com.qsiny.mapper.UserApplyInfoMapper;
import com.qsiny.mapper.UserBasicInfoMapper;
import com.qsiny.mapper.UserMapper;
import com.qsiny.mapper.UserRoleMapper;
import com.qsiny.service.ApplyService;
import com.qsiny.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    private UserApplyInfoMapper userApplyInfoMapper;

    @Resource
    private MessageService messageService;

    @Resource
    private UserBasicInfoMapper userBasicInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void addUserApply(UserApplyInfo userApplyInfo) {

        userApplyInfoMapper.insert(userApplyInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decideApply(UserApplyInfo userApplyInfo) {

        //根据决定修改申请的
        Date now = new Date(System.currentTimeMillis());
        userApplyInfo.setUpdateTime(now);
        userApplyInfo.setUpdateId(userApplyInfo.getDecideUserId());
        userApplyInfoMapper.updateById(userApplyInfo);

        //如果申请同意则为用户赋予相应的角色以及维护对应的信息
        if(CommonValues.ONE.equals(userApplyInfo.getResult())){

            UserBasicInfo userBasicInfo = new UserBasicInfo();
            BeanUtils.copyProperties(userApplyInfo,userBasicInfo);
            userBasicInfo.setUpdateTime(now);
            userBasicInfo.setUpdateId(String.valueOf(userApplyInfo.getDecideUserId()));
            userBasicInfoMapper.insert(userBasicInfo);

            String applyType = userApplyInfo.getApplyType();
            String applyRole = userApplyInfo.getApplyRole();

            User user = new User();
            user.setUserId(userApplyInfo.getUserId());
            user.setUserType(applyType);
            user.setUpdateId(String.valueOf(userApplyInfo.getDecideUserId()));
            user.setUpdateTime(now);
            userMapper.updateById(user);

            UserRole userRole = new UserRole();
            userRole.setRoleId(Long.valueOf(applyRole));
            userRole.setUserId(userApplyInfo.getUserId());
            userRoleMapper.insert(userRole);

        }
        //通过消息发送角色变动的消息
        Message message = new Message();
        message.setCreateId(userApplyInfo.getDecideUserId());
        message.setSendUserName(userApplyInfo.getDecideUserName());
        message.setSendUserId(userApplyInfo.getDecideUserId());
        message.setTitle("您的申请已被处理");
        message.setText(CommonValues.ONE.equals(userApplyInfo.getResult())?"您的申请以通过":"您的申请未通过");
        message.setSendTime(now);
        message.setReceiveId(userApplyInfo.getUserId());
        message.setUpdateTime(now);
        messageService.sendMessage("","",CommonValues.ZERO,message,null);

    }


}
