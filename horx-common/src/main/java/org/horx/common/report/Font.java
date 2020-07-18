package org.horx.common.report;

import java.io.Serializable;

/**
 * 字体。
 * @since 1.0
 */
public class Font implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean bold;
	private Short size;
	private Short color;
	private String fontName;
	private boolean italic;
	private boolean underline;
	
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public Short getSizeInPoints() {
		return size;
	}
	public void setSizeInPoints(Short size) {
		this.size = size;
	}
	public Short getColor() {
		return color;
	}
	public void setColor(Short color) {
		this.color = color;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	public boolean isUnderline() {
		return underline;
	}
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
	
	
}
