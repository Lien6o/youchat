package com.youchat.common.project;

public class BizAop {
    /**
     * 1.在流程节点上设置业务截面，运行修改的字段和范围。
     * 2.流程节点判断是否存在业务截面
     * 3.存在则广播消息，交由业务处理，保存当前快照。
     * 4.业务处理后交还流程处理主动权，并修改数据。做安全校验。状态，字段修改。
     * 5.流程继续
     *
     * eg: 结算金额抽佣 平台抽佣 ，抽佣规则是 格式化的数据流程按订单商品属性，商户等级。
     *     不能支持更个性化，临时性，试错类型的业务创新。
     *
     *     所以：把个性化逻辑交由业务处理，穿针引线。模板方法的分布式化。
     *
     *
     *     config: status=3 field=feeAmount bizLineId=2021001
     *
     *
     *     BEFORE:   id=1 status=3 feeAmount=300 hasBizAopConfig=true
     *     SNAPSHOT: id=1 status=3 feeAmount=300 hasBizAopConfig=true      if(hasBizAopConfig) ->    id=1  status=3 processStatus=0
     *
     *     AFTER:    id=1 status=3 feeAmount=270 hasBizAopConfig=ture      if(hasBizAopConfig) ->    id=1  status=3 processStatus=1
     *
     */
}
