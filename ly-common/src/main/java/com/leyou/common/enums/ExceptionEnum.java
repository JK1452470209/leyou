package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @outhor Mr.JK
 * @create 2020-05-12  17:26
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {//枚举的构造函数默认都是私有的

    BRAND_NOT_FOUND(404,"品牌不存在"),
    CATEGORY_NOT_FOND(404,"商品分类没查到"),
    SPEC_GROUP_NOT_FOND(404,"商品规格组不存在"),
    SPEC_PARAM_NOT_FOND(404,"商品规格参数不存在"),
    GOODS_NOT_FOND(404,"商品不存在"),
    GOODS_DETAIL_NOT_FOND(404,"商品详情不存在"),
    GOODS_SKU_NOT_FOND(404,"商品SKU不存在"),
    GOODS_STOCK_NOT_FOND(404,"商品库存不存在"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPLOAD_FILE_ERROR(500,"文件上传失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    GOODS_SAVE_ERROR(400,"新增商品失败"),
    INVALID_USER_DATA_TYPE(400,"用户数据类型无效"),
    INVALID_VERIFY_CODE(400,"无效的验证码"),
    INVALID_USERNAME_PASSWORD(400,"用户名或密码错误"),
    CREATE_TOKEN_ERROR(500,"用户凭证生成失败"),
    UNAUTHORIZED(403,"未授权"),
    CART_NOT_FOUND(404,"购物车为空"),
    ORDER_NOT_FOUND(404,"订单不存在"),
    ORDER_DETAIL_NOT_FOUND(404,"订单详情不存在"),
    ORDER_STATUS_NOT_FOUND(404,"订单状态不存在"),
    CREATE_ORDER_ERROR(500,"创建订单失败"),
    STOCK_NOT_ENOUGH(500,"库存不足"),
    WX_PAY_ORDER_FAIL(400,"微信下单失败"),
    INVALID_SIGN_ERROR(400,"签名错误"),
    INVALID_ORDER_PARAM(400,"订单参数错误"),
    UPDATE_ORDER_STATUS_ERROR(400,"更新订单状态错误"),
    ORDER_STATUS_ERROR(400,"订单状态错误"),
    ADDRESS_SAVE_ERROR(400,"地址保存错误"),
    ADDRESS_QUERY_ERROR(500,"地址查询错误"),
    ADDRESS_UPDATE_ERROR(500,"地址更新错误"),
    ADDRESS_DELETE_ERROR(500,"地址删除错误"),
    ;
    private int code;
    private String msg;

}
