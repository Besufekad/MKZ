package com.tsegaab.apps.ahun.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.http.util.ByteArrayBuffer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.tsegaab.apps.ahun.Consts;

import android.util.Log;
import android.util.Xml;

public class Xml2ZennaParser {


	public Xml2ZennaParser() {
	}

	public ArrayList<Zenna> parse(InputStream in)
			throws XmlPullParserException, IOException {
		try {
			Log.d(Consts.Z_TAG, "Parssing .. " + in.toString());
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			Log.d(Consts.Z_TAG, parser.toString());
			return getParsedZenawoch(parser);
		}
		finally {
			in.close();
		}
	}

	private ArrayList<Zenna> getParsedZenawoch(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<Zenna> zennawoch = new ArrayList<Zenna>();

		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE, "xml");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			Log.d(Consts.Z_TAG, "Frist tag name = " + name);
			// Starts by looking for the entry tag
			if (name.equals("item")) {
				zennawoch.add(readSingleZenna(parser));
			} else {
				skip(parser);
			}
		}
		return zennawoch;
	}

	private byte[] getImage(String url) {
		try {
			URL imageUrl = new URL(url);
			URLConnection ucon = imageUrl.openConnection();

			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			return baf.toByteArray();
		} catch (Exception e) {
			Log.d("ImageManager", "Error: " + e.toString());
		}
		return null;
	}

	private Zenna readSingleZenna(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE, "item");
		String title = null;
		String content = null;
		String link = null;
		String url = null;
		String imageUrl = null;
		String channel = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				Log.d(Consts.Z_TAG, "inside item title found");
				title = readTitle(parser);
			} else if (name.equals("content")) {
				Log.d(Consts.Z_TAG, "inside item content found");
				content = readContent(parser);
			} else if (name.equals("url")) {
				Log.d(Consts.Z_TAG, "inside item url found");
				url = readUrl(parser);
			} else if (name.equals("channel")) {
				Log.d(Consts.Z_TAG, "inside item channel found");
				channel = readChannel(parser);
			} else if (name.equals("image_url")) {
				Log.d(Consts.Z_TAG, "inside item image_url found");
				imageUrl = readImageUrl(parser);
			} else {
				Log.d(Consts.Z_TAG, "inside item skiping");
				skip(parser);
			}
		}
		return new Zenna(title, content, url, channel, imageUrl);
	}

	// Processes title tags in the feed.
	private String readTitle(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "title");
		return title;
	}

	private String readContent(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE,
				"content");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "content");
		return content;
	}

	private String readUrl(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE, "url");
		String url = readText(parser);
		parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "url");
		return url;
	}

	private String readImageUrl(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE,
				"image_url");
		String imageurl = readText(parser);
		parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "image_url");
		return imageurl;
	}

	// Processes link tags in the feed.
	// private String readLink(XmlPullParser parser) throws IOException,
	// XmlPullParserException {
	// String link = "";
	// parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE, "link");
	// String tag = parser.getName();
	// String relType = parser.getAttributeValue(null, "rel");
	// if (tag.equals("link")) {
	// if (relType.equals("alternate")) {
	// link = parser.getAttributeValue(null, "href");
	// parser.nextTag();
	// }
	// }
	// parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "link");
	// return link;
	// }

	// Processes summary tags in the feed.
	private String readChannel(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, Consts.XML_NAME_SPACE,
				"channel");
		String channel = readText(parser);
		parser.require(XmlPullParser.END_TAG, Consts.XML_NAME_SPACE, "channel");
		return channel;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
