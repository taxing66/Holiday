package jw.cn.com.jwutils.model.net;

import android.text.TextUtils;


/**
 * 用于fastjson解析，处理整体数据
 *
 * @author 林佳楠
 * @create 2016-06-29
 * @editer 林佳楠
 * @date 2016-06-29
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class RootJson {

	/**
	 * 没有获取到status
	 */
	public static final int TYPE_INT_STATUS_UNSET = -1;
	/**
	 * 成功
	 */
	public static final int TYPE_INT_STATUS_SUCCESS = 0;
	/**
	 * 入参不正确而且不需要提示，提高接口安全性
	 */
	public static final int TYPE_INT_STATUS_PARAMERROR = 1001;
	/**
	 * 短信发送接口 随机码重复
	 */
	public static final int TYPE_INT_STATUS_RADOMCODE_REPEAT = 1002;
	/**
	 * 群发通知次数用完
	 */
	public static final int TYPE_INT_STATUS_GROUPMSG_DISABLE = 1005;
	/**
	 * 手机号码已经被注册
	 */
	public static final int TYPE_INT_STATUS_PHONE_REGISTED = 1007;
	/**
	 * 充值失败
	 */
	public static final int TYPE_INT_STATUS_RECHARGE_LOSE = 1009;
	/**
	 * 支付密码错误，可返回次数
	 */
	public static final int TYPE_INT_STATUS_WRONG_PWD = 1010;
	/**
	 * 支付密码错误次数太多
	 */
	public static final int TYPE_INT_STATUS_PWD_TOO_MUCH =1011;
	/**
	 * 未设置支付密码
	 */
	public static final int TYPE_INT_STATUS_NOT_SET_PAY_PWD = 1012;
	/**
	 * 城市未开放
	 */
	public static final int TYPE_INT_STATUS_CITY_NOT_OPEN =1102;
	/**
	 * 非VIP认证
	 */
	public static final int TYPE_INT_STATUS_NOT_VIP =1109;
	/**
	 * 推送次数已用光
	 */
	public static final int TYPE_INT_STATUS_PUSH_DISABLE = 1114;
	/**
	 * 已开放快捷兼职，但是未开放VIP
	 */
	public static final int TYPE_INT_STATUS_VIP_DISABLE = 2005;
	/**
	 * 企业无权限
	 */
	public static final int TYPE_INT_STATUS_NOT_PERMISSION = 2005;
	/**
	 * 账号被禁用
	 */
	public static final int TYPE_INT_STATUS_ACCOUNT_DISABLED = 2020;
	/**
	 * 发布托管时预算不足
	 */
	public static final int TYPE_INT_STATUS_HOST_JOB_PREPAY_LOWER = 2022;
	/**
	 * 重复提交工资订单
	 */
	public static final int TYPE_INT_STATUS_TRUSTEESHIP_RESUBMIT_PAYROLL = 2034;
	/**
	 * 单个录用时，简历被其它企业抢先录用
	 */
	public static final int TYPE_INT_STATUS_EMPLOY_SINGLE_CONFLICT = 2035;
	/**
	 * 批量录用时，部分简历被其它企业抢先录用
	 */
	public static final int TYPE_INT_STATUS_EMPLOY_GROUP_CONFLICT = 2036;
	/**
	 * 批量录用时，全部简历被其它企业抢先录用
	 */
	public static final int TYPE_INT_STATUS_EMPLOY_ALL_CONFLICT = 2037;
	/**
	 * 批量录用时，录用人数超过允许录用上限
	 */
	public static final int TYPE_INT_STATUS_EMPLOY_OVER_MAX_COUNT = 2038;
	/**
	 * 兼职已过期，请重新发布
	 */
	public static final int TYPE_INT_STATUS_JOB_TIMEOUT = 2040;
	/**
	 * 托管当前状态不允许去支付
	 */
	public static final int TYPE_INT_STATUS_TRUSTEESHIP_CANNOT_PAY = 2046;
	/**
	 * 预付款金额有变化，无法继续支付
	 */
	public static final int TYPE_INT_STATUS_PREPAY_MONEY_TIMEOUT = 2047;
	/**
	 * 发工资的简历中有些简历已经有支付订单，无法继续支付
	 */
	public static final int TYPE_INT_STATUS_RESUME_HAVEN_ORDER = 2048;
	/**
	 * 取消兼职失败 - 兼职已经发布成功，无法取消
	 */
	public static final int TYPE_INT_STATUS_JOB_CANCEL_DISABLE = 2049;
	/**
	 * 申请代招服务订单成功接单，请勿再次申请
	 */
	public static final int TYPE_INT_STATUS_APPLY_TRUSTEESHIP_SUCCESS_DONOT_TRY_AGAIN = 2056;
	/**
	 * 申请代招服务订单申请成功！系统需要核对您的信息，请耐心等待
	 */
	public static final int TYPE_INT_STATUS_APPLY_TRUSTEESHIP_SUCCESS_BUT_NEED_CHECK = 2057;
	/**
	 * 支付订单已经失效
	 */
	public static final int TYPE_INT_STATUS_ORDER_TIMEOUT = 4000;
	/**
	 * 支付订单已经支付成功，请重新刷新数据
	 */
	public static final int TYPE_INT_STATUS_ORDER_HAVE_PAID = 4001;
	/**
	 * 支付订单重复
	 */
	public static final int TYPE_INT_STATUS_ORDER_DUPLICATE = 4002;
	/**
	 * 支付订单不存在
	 */
	public static final int TYPE_INT_STATUS_ORDER_NO_EXIST = 4003;
	/**
	 * 支付订单处理中，请耐心等待
	 */
	public static final int TYPE_INT_STATUS_ORDER_PROCESSING = 4004;
	/**
	 * 支付订单异常，请联系客服
	 */
	public static final int TYPE_INT_STATUS_ORDER_ILLEGAL = 4005;

	private int status = TYPE_INT_STATUS_UNSET;
	private String msg;
	private String data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		if (TextUtils.isEmpty(msg)){
			return "网络或系统繁忙";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
