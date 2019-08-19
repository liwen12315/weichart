package com.lw.entity.vo;

import java.io.Serializable;
import java.util.List;


/**
 *  功能树
 *  数据结构参照 https://www.cnblogs.com/tangzeqi/p/8021637.html
 */
public class TreeView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String text;
	
	private String color="#000000";
	
	private String backColor="#FFFFFF";
	
	private String href;
	
	private List<TreeView> nodes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<TreeView> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeView> nodes) {
		this.nodes = nodes;
	}

	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}
	
	

	public TreeView(Integer id, String text, String color, String backColor, String href, List<TreeView> nodes) {
		this.id = id;
		this.text = text;
		this.color = color;
		this.backColor = backColor;
		this.href = href;
		this.nodes = nodes;
	}

	public TreeView() {

	}
}
