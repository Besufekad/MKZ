package com.tsegaab.apps.ahun;

import android.content.Context;

public class Consts {	
	
	public static final String Z_TAG = "ZENNAWU";
	public static final String EZ_TAG = "E-ZENNAWU";
	public static final String XML_NAME_SPACE = null;
	public static DBContoller controller;
	public static Context context;
	
	public static final class Db{
		public static final String  DB_SM = "zennadatabase";
		public static final String TABLE_SM = "zenna";
		public static final String  TABLE_ID_COLM = "id";
		public static final String  TABLE_TITLE_COLM = "title";
		public static final String  TABLE_CONTENT_COLM = "full_content";
		public static final String  TABLE_IMAGE_URL_COLM = "image_url";
		public static final String  TABLE_IMAGE_COLM = "image";
		public static final String  TABLE_TUMBLE_IMAGE_URL_COLM = "tumble_image_url";
		public static final String  TABLE_TUMBLE_IMAGE_COLM = "tumble_image";
		public static final String  TABLE_URL_COLM = "url";
		public static final String  TABLE_CHANNEL_COLM = "channel";
	}
	public static final class Server {
		public static final String  IP_ONE = "192.168.1.255";
	}

}
