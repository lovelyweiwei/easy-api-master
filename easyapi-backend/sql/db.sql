-- 接口信息
create table if not exists easyapi.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(512) null comment '描述',
    `url` varchar(256) not null comment '请求地址',
    `requestParams` text not null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` dateTime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息';

-- 用户调用接口关系表
create table if not exists easyapi.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';

insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('姚笑愚', '卢思源', 'www.nadene-pagac.info', '许智辉', '陆展鹏', 0, '夏弘文', 62213);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('冯越彬', '周鑫磊', 'www.cleora-grimes.biz', '范鑫鹏', '梁哲瀚', 0, '胡远航', 680);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孙晓啸', '韦子轩', 'www.lewis-hagenes.io', '史明', '任伟泽', 0, '江瑾瑜', 27619345);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('朱泽洋', '姚健柏', 'www.maria-orn.name', '武越彬', '董伟祺', 0, '杜鸿煊', 53599);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('曾胤祥', '雷思', 'www.miles-medhurst.info', '傅炫明', '马健雄', 0, '刘风华', 20195072);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('任浩宇', '梁鑫鹏', 'www.sonny-kuhlman.co', '何健柏', '陶泽洋', 0, '黄展鹏', 71);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('冯博超', '孟哲瀚', 'www.stephany-witting.name', '杨乐驹', '袁思远', 0, '江风华', 4349024706);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('杜旭尧', '洪明辉', 'www.jed-bergnaum.com', '史明杰', '许立辉', 0, '孔志泽', 887364407);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('苏修洁', '孙金鑫', 'www.deangelo-olson.biz', '严绍辉', '陈雨泽', 0, '冯烨霖', 33);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吴胤祥', '朱思源', 'www.trent-lynch.biz', '钱擎苍', '曹航', 0, '郑正豪', 743);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('龙子轩', '万越彬', 'www.lexie-streich.biz', '杜伟宸', '尹靖琪', 0, '吕擎宇', 2651);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('蒋文博', '孔烨伟', 'www.eric-shanahan.info', '万伟祺', '高乐驹', 0, '洪雨泽', 140);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('马浩宇', '孔鑫鹏', 'www.stacy-reichert.net', '史楷瑞', '黄思淼', 0, '雷志泽', 94337);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('顾越泽', '余智辉', 'www.deandrea-parker.biz', '吕鹏涛', '马凯瑞', 0, '周建辉', 99882);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('赵浩', '苏煜祺', 'www.isobel-blick.name', '江昊天', '杨峻熙', 0, '史鸿煊', 193160746);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('陈正豪', '周昊强', 'www.bonita-toy.co', '万文博', '熊志泽', 0, '苏钰轩', 89309317);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('姚振家', '黎哲瀚', 'www.courtney-shields.co', '潘睿渊', '于文博', 0, '贺健柏', 8660580287);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('梁果', '邹烨霖', 'www.rodney-crona.info', '金建辉', '龙明轩', 0, '秦哲瀚', 9);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('梁振家', '雷绍辉', 'www.lin-klein.com', '钟哲瀚', '董智宸', 0, '叶擎宇', 7268087);
insert into easyapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('韦立诚', '于烨霖', 'www.arthur-price.biz', '崔君浩', '雷烨华', 0, '谭立轩', 3);
