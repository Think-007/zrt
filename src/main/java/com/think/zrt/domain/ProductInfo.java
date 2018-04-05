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

	// 商品系列名称
	private String seriesName;

	// 商品图片地址
	private String productPic;

	// 商品描述信息
	private String productDesc;

	// 音频信息地址
	private String videoUrl;

	// 视屏信息地址
	private String audioUrl;

	// 视频封面地址
	private String audioPic;

	// 模板id
	private int templateId;

	// 自然堂的名称
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getAudioPic() {
		return audioPic;
	}

	public void setAudioPic(String audioPic) {
		this.audioPic = audioPic;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ProductInfo [id=" + id + ", productName=" + productName + ", seriesName=" + seriesName + ", productPic="
				+ productPic + ", productDesc=" + productDesc + ", videoUrl=" + videoUrl + ", audioUrl=" + audioUrl
				+ ", audioPic=" + audioPic + ", templateId=" + templateId + ", searchName=" + searchName + "]";
	}

}
