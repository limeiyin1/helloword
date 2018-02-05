package com.redfinger.manager.common.helper;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.domain.SysDict;

public class DictTag extends TagSupport {

	protected String category;
	protected String value;
	protected String keys;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		List<SysDict> dictList= DataInitProcessor.dictCategoryMap.get(category);
		List<String> keyList=null;
		if(StringUtils.isNotEmpty(keys)){
			keyList=Lists.newArrayList(keys.split(","));
		}
		if(!CollectionUtils.isEmpty(dictList)){
			for(SysDict dict: dictList){
				String selected="";
				if(StringUtils.isNotEmpty(value) && dict.getDictValue().equals(value)){
					selected="selected";
				}
				try {
					if(keyList!=null){
						
						if(keyList.contains(dict.getDictCode())){
							out.println("<option value='"+dict.getDictValue()+"' "+selected+">"+dict.getDictName()+"</option>");
						}
					}else{
						out.println("<option value='"+dict.getDictValue()+"' "+selected+">"+dict.getDictName()+"</option>");
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
	
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 4730783699697592749L;

}
