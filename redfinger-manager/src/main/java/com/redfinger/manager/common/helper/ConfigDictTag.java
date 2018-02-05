package com.redfinger.manager.common.helper;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.domain.SysDict;

public class ConfigDictTag extends SimpleTagSupport{
	
	/** sys_config的config_code值*/
	private String code;
	/** 是否只读*/
	private boolean readonly =false;
	/** 字典类型*/
	private String category;
	/** 显示的key值*/
	private String keys;
	/** select的宽度*/
	private Integer width = 280;
	/** select的高度*/
	private Integer height =22;
	/** id*/
	private String id ="";
	/** 类型名称*/
	private String clazz ="";
	/** easy-ui的data-options配置*/
	private String dataOptions;
	/** 是否禁用*/
	private boolean disabled ;
	/** 自定义名称*/
	private String codeName = "";

    @Override
    public void doTag() throws JspException, IOException {
    	/**获取页面输出流 */
    	JspWriter out = this.getJspContext().getOut();
    	/** 根据code查询配置信息*/
    	SysConfig config=ConfigHelper.getConfigByCode(code);
    	/** 查询字典*/
		List<SysDict> dictList= DataInitProcessor.dictCategoryMap.get(category);
		List<String> keyList=null;
		/** 通过,切割要显示的内容*/
		if(StringUtils.isNotEmpty(keys)){
			keyList=Lists.newArrayList(keys.split(","));
		}
		/** easy-ui的data-options配置*/
		dataOptions = StringUtils.isNotBlank(dataOptions) ? ",".concat(dataOptions) : "";
		/** 输出html代码*/
		out.println("<tr>");
		out.println("<td class=\"td1\">");
		out.println((StringUtils.isNotBlank(codeName) ? codeName :config.getConfigLabel())+":");
		out.println("</td>");
		out.println("<td class=\"td2\">");
		
		/** 只读, 输出其值*/
		if(readonly){
			if(!CollectionUtils.isEmpty(dictList)){
				String dictName = config.getConfigValue();
				for(SysDict dict: dictList){
					if(StringUtils.isNotEmpty(dictName) && dict.getDictValue().equals(dictName)){
						dictName = dict.getDictName();
						break;
					}
				}
				out.println(dictName);
			}
			out.println();
		}else{
			/** 输出select*/
			out.print("<select id='"+id+"' class='easyui-combobox "+clazz+"'  editable='false' name=\"map['"+config.getConfigCode()+"']\" data-options='required:true,readonly:"+disabled+",disabled:"+disabled+",width:"+width+",height:"+height+dataOptions+"'>");
			if(!CollectionUtils.isEmpty(dictList)){
				for(SysDict dict: dictList){
					String selected="";
					/** 判断是否选中*/
					if(StringUtils.isNotEmpty(config.getConfigValue()) && dict.getDictValue().equals(config.getConfigValue())){
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
			out.println("</select>");
		}
		out.println("</td>");
		out.println("</tr>");
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	
	
}
