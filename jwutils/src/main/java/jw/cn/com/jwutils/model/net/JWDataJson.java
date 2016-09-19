package jw.cn.com.jwutils.model.net;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 *用于fastjson，处理响应数据中data部分
 * @author 陈德华
 * @create 2016-09-01
 * @editer 陈德华
 * @date 2016-09-01
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JWDataJson<I, O> {
	
	private List<I> list;
	private O obj;
	@JSONField(name = "page_sum")
	private int pageSum;
	@JSONField(name = "total_row")
	private int totalRow;

	public List<I> getList() {
		return list;
	}

	public void setList(List<I> list) {
		this.list = list;
	}

	public O getObj() {
		return obj;
	}

	public void setObj(O obj) {
		this.obj = obj;
	}

	public int getPageSum() {
		return pageSum;
	}

	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

}
