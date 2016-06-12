package com.initData.util;

public enum CodeType {
	/**
	 * 采购申请
	 */
	SQ01("SQ01","11","采购申请"),
	/**
	 * 采购
	 */
	CG01("CG01","21","采购"),
	/**
	 * 采购(退货)
	 */
	TH01("TH01","22","采购(退货)"),
	/**
	 * 收货
	 */
	SH01("SH01","41","收货"),
	/**
	 * 配送申请
	 */
	PS01("PS01","31","配送申请"),
	/**
	 * 配送
	 */
	PS02("PS02","32","配送"),
	/**
	 * 	配送(主配)
	 */
	PS03("PS03","33","配送(主配)"),
	/**
	 * 盘点全盘
	 */
	PDQP("PDQP","51","盘点全盘"),
	/**
	 * 盘点局盘
	 */
	PDJP("PDJP","52","盘点局盘"),
	/**
	 * 盘点清单
	 */
	PDQD("PDQD","53","盘点清单"),
	/**
	 * 盘点明细
	 */
	PDMX("PDMX","54","盘点明细"),
	/**
	 * 领用
	 */
	LY01("LY01","61","领用"),
	/**
	 * 报损
	 */
	BS01("BS01","71","报损"),
	/**
	 * 借用
	 */
	JY01("JY01","81","借用"),
	/**
	 * 费用
	 */
	FY01("FY01","85","费用"),
	/**
	 * 应付
	 */
	YF01("YF01","91","应付"),
	/**
	 * 发票
	 */
	FP01("FP01","95","发票"),
	/**
	 * 供应商(经营)
	 */
	GY01("GY01","00031","供应商(经营)"),
	/**
	 * 供应商(生产)
	 */
	GY02("GY02","00032","供应商(生产)"),
	/**
	 * 供应商(一次性)
	 */
	GY03("GY03","00033","供应商(一次性)"),
	/**
	 * 供应商联系人
	 */
	GY04("GY04","00034","供应商联系人"),
	/**
	 * 办公用品类
	 */
	Z001("Z001","0001","办公用品类"),
	/**
	 * 装修材料类
	 */
	Z002("Z002","0002","装修材料类"),
	/**
	 * 广告促销费
	 */
	Z003("Z003","0003","广告促销费"),
	/**
	 * 包装费类
	 */
	Z004("Z004","0004","包装费类"),
	/**
	 * 亲情茶类
	 */
	Z005("Z005","0005","亲情茶类"),
	/**
	 * 书籍类
	 */
	Z006("Z006","0006","书籍类"),
	/**
	 * 服装
	 */
	Z007("Z007","0007","服装"),
	/**
	 * 其他类
	 */
	Z008("Z008","0008","其他类"),
	/**
	 * 福利费类
	 */
	Z009("Z009","0009","福利费类"),
	/**
	 * 通用名
	 */
	Z011("Z011","0011","通用名"),
	
	/**
	 * 物品类型之类生成
	 */
	WPLX("WPLX","2000","物品类型");
	
	private CodeType(String type,String headCode,String name){
		this.type=type;
		this.headCode=headCode;
		this.name=name;
	}
	private String type;
	private String headCode;
	private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHeadCode() {
		return headCode;
	}
	public void setHeadCode(String headCode) {
		this.headCode = headCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
