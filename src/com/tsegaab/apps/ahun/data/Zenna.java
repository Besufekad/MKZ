package com.tsegaab.apps.ahun.data;

public class Zenna {
	
	private String title;
	private String fullContent;
	private String url;
	private String tumbleImageUrl;
	private String imageUrl;
	private byte[] tumbleImage;
	private byte[] image;
	private String channel;
	
	
	public Zenna(String title2, String content, String link, String channel2,
			String imageUrl2) {
		this.title = title2; this.fullContent = content; this.url = link; 
		this.channel = channel2; this.imageUrl = imageUrl2;
	}
	
	public Zenna(String title2, String content, String link, String channel2,
			String imageUrl2, byte[] image) {
		this.title = title2; this.fullContent = content; this.url = link; 
		this.channel = channel2; this.imageUrl = imageUrl2;
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFullContent() {
		return fullContent;
	}
	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTumbleImageUrl() {
		return tumbleImageUrl;
	}
	public void setTumbleImageUrl(String tumbleImage) {
		this.tumbleImageUrl = tumbleImage;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String image) {
		this.imageUrl = image;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public byte[] getTumbleImage() {
		return tumbleImage;
	}
	public void setTumbleImage(byte[] tumbleImage) {
		this.tumbleImage = tumbleImage;
	}

}
