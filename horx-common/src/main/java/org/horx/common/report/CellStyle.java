package org.horx.common.report;

import java.io.Serializable;

import org.horx.common.report.enums.Align;
import org.horx.common.report.enums.BorderStyle;
import org.horx.common.report.enums.VerticalAlign;

/**
 * 单元格样式。
 * @since 1.0
 */
public class CellStyle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Align align;
	private VerticalAlign verticalAlign;
	private Font font;
	private BorderStyle leftBorder;
	private BorderStyle rightBorder;
	private BorderStyle topBorder;
	private BorderStyle bottomBorder;
	private short leftBorderColor;
	private short rightBorderColor;
	private short topBorderColor;
	private short bottomBorderColor;
	private short backgroundColor;
	private boolean wrapText;
	private String dataFormat;
	
	public Align getAlign() {
		return align;
	}
	public void setAlign(Align align) {
		this.align = align;
	}
	public VerticalAlign getVerticalAlign() {
		return verticalAlign;
	}
	public void setVerticalAlign(VerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public BorderStyle getLeftBorder() {
		return leftBorder;
	}
	public void setLeftBorder(BorderStyle leftBorder) {
		this.leftBorder = leftBorder;
	}
	public BorderStyle getRightBorder() {
		return rightBorder;
	}
	public void setRightBorder(BorderStyle rightBorder) {
		this.rightBorder = rightBorder;
	}
	public BorderStyle getTopBorder() {
		return topBorder;
	}
	public void setTopBorder(BorderStyle topBorder) {
		this.topBorder = topBorder;
	}
	public BorderStyle getBottomBorder() {
		return bottomBorder;
	}
	public void setBottomBorder(BorderStyle bottomBorder) {
		this.bottomBorder = bottomBorder;
	}
	public short getLeftBorderColor() {
		return leftBorderColor;
	}
	public void setLeftBorderColor(short leftBorderColor) {
		this.leftBorderColor = leftBorderColor;
	}
	public short getRightBorderColor() {
		return rightBorderColor;
	}
	public void setRightBorderColor(short rightBorderColor) {
		this.rightBorderColor = rightBorderColor;
	}
	public short getTopBorderColor() {
		return topBorderColor;
	}
	public void setTopBorderColor(short topBorderColor) {
		this.topBorderColor = topBorderColor;
	}
	public short getBottomBorderColor() {
		return bottomBorderColor;
	}
	public void setBottomBorderColor(short bottomBorderColor) {
		this.bottomBorderColor = bottomBorderColor;
	}
	public short getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(short backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public boolean isWrapText() {
		return wrapText;
	}
	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	
	/**
	 * 设置边框风格。
	 * @param borderStyle
	 */
	public void setBorder(BorderStyle borderStyle) {
		setLeftBorder(borderStyle);
		setRightBorder(borderStyle);
		setTopBorder(borderStyle);
		setBottomBorder(borderStyle);
	}
	
	/**
	 * 设置边框风格。
	 * @param borderStyle
	 * @param color
	 */
    public void setBorder(BorderStyle borderStyle, short color) {
    	setBorder(borderStyle);
    	setBorderColor(color);
	}
	
    /**
     * 设置边框颜色。
     * @param color
     */
    public void setBorderColor(short color) {
    	setLeftBorderColor(color);
		setRightBorderColor(color);
		setTopBorderColor(color);
		setBottomBorderColor(color);
	}
}
