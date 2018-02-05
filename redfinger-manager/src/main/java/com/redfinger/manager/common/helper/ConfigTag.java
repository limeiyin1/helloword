package com.redfinger.manager.common.helper;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.redfinger.manager.common.domain.SysConfig;


public class ConfigTag extends TagSupport {


	protected String code;
	protected String number;
	protected String precision;
	protected String  readonly;
	protected Boolean  textarea;
	protected String dataOptions;
	


	

	public Boolean getTextarea() {
		return textarea;
	}

	public void setTextarea(Boolean textarea) {
		this.textarea = textarea;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		SysConfig config=ConfigHelper.getConfigByCode(code);
		String clazz = "easyui-textbox";
		String preci="";
		String text="";
		String  height="";
		if(number!=null  || precision!=null ){
			clazz="easyui-numberbox";
		}
		if(null!=textarea&&textarea ){
			text=",multiline:true";
			height="style=\"height: 180px\"";
		}
		
		if(StringUtils.isNotEmpty(precision) && Boolean.parseBoolean(precision)){
			preci=",precision:2";
		}
		/** easy-ui的data-options配置*/
		dataOptions = StringUtils.isNotBlank(dataOptions) ? ",".concat(dataOptions) : "";
		try {
			
			out.println("<tr>");
			out.println("<td class=\"td1\">");
			out.println(config.getConfigLabel()+":");
			out.println("</td>");
			out.println("<td class=\"td2\">");
			if(readonly!=null){
		     out.println(config.getConfigValue());
			}else{
			out.println("<input "+height+" class='"+clazz+"' type='text' name=\"map['"+config.getConfigCode()+"']\" value='"+config.getConfigValue()+"' data-options=\"required:true"+dataOptions+preci+text+"\"/>");
			}
			out.println("</td>");
			out.println("</tr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}






	/**
	 * 
	 */
	private static final long serialVersionUID = 4828245983067707636L;

}
