package com.think.zrt.domain;

import java.io.Serializable;

public class ProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 商品id
	private long id;

	// 商品名称
	private String productName;

	// 产品名
	private String productSimple;

	// 商品系列名称
	private String seriesName;

	// 商品图片地址
	private String productPic;

	// 商品描述信息
	private String productDesc;

	// 音频信息地址
	private String audioUrl;

	// 视屏信息地址
	private String videoUrl;

	// 视频封面地址
	private String videoPic;

	// 视频模板id
	private int videoTemplateId;

	// 模板id
	private int templateId;

	// 自然堂的名称
	private String searchName;

	// 防伪验证图片
	private String codePic;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoPic() {
		return videoPic;
	}

	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getCodePic() {
		return codePic;
	}

	public void setCodePic(String codePic) {
		this.codePic = codePic;
	}

	public String getProductSimple() {
		return productSimple;
	}

	public void setProductSimple(String productSimple) {
		this.productSimple = productSimple;
	}

	public int getVideoTemplateId() {
		return videoTemplateId;
	}

	public void setVideoTemplateId(int videoTemplateId) {
		this.videoTemplateId = videoTemplateId;
	}

	@Override
	public String toString() {
		return "ProductInfo [id=" + id + ", productName=" + productName + ", productSimple=" + productSimple
				+ ", seriesName=" + seriesName + ", productPic=" + productPic + ", productDesc=" + productDesc
				+ ", audioUrl=" + audioUrl + ", videoUrl=" + videoUrl + ", videoPic=" + videoPic + ", videoTemplateId="
				+ videoTemplateId + ", templateId=" + templateId + ", searchName=" + searchName + ", codePic=" + codePic
				+ "]";
	}

}
