package com.redfinger.manager.modules.assess.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessContentDetailExample;
import com.redfinger.manager.common.domain.AssessContentExample;
import com.redfinger.manager.common.domain.AssessContentListExample;
import com.redfinger.manager.common.domain.AssessProject;
import com.redfinger.manager.common.domain.AssessProjectExample;
import com.redfinger.manager.common.domain.AssessProjectOptionExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.AssessContentDetailMapper;
import com.redfinger.manager.common.mapper.AssessContentListMapper;
import com.redfinger.manager.common.mapper.AssessContentMapper;
import com.redfinger.manager.common.mapper.AssessProjectMapper;
import com.redfinger.manager.common.mapper.AssessProjectOptionMapper;

/**
 * 考核表的Service
 * 
 * @ClassName: AssessProjectService
 * @author tluo
 * @date 2016年3月23日 下午3:14:42
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class AssessProjectService extends BaseService<AssessProject, AssessProjectExample, AssessProjectMapper> {
	@Autowired
	AssessProjectMapper projectMapper;
	@Autowired
	AssessProjectOptionMapper projectOptionMapper;
	@Autowired
	AssessContentListMapper contentListMapper;
	@Autowired
	AssessContentDetailMapper contentDetailMapper;
	@Autowired
	AssessContentMapper contentMapper;
	
	
	
	/**
	 * 获取系统公钥
	 * 
	 * @Title: getPublicKey
	 * @return String 返回类型
	 * @return
	 */
	public  String getPublicKey() {
		return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPoJt0wEm1RPrXtzhJfYEZ4vRSKrQHWLF+LObh"
				+ "A50r6+uE5gffeeaY6nNP8SU5Se/VFIHLs6Y/nA2XYNOklKlhIh0h8ZcOC/5ImBSTmGyEd8vBYAzl"
				+ "npaXHziT5/IDUkM1mWPPzyCPrFQlg+hMyAIYBHL6rdlIMzmCdTum85DieQIDAQAB";
	}

	/**
	 * 获取系统私钥
	 * 
	 * @Title: getPrivateKey
	 * @return String 返回类型
	 * @return
	 */
	public  String getPrivateKey() {
		return "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+gm3TASbVE+te3OEl9gRni9FIq"
				+ "tAdYsX4s5uEDnSvr64TmB9955pjqc0/xJTlJ79UUgcuzpj+cDZdg06SUqWEiHSHxlw4L/kiYFJOY"
				+ "bIR3y8FgDOWelpcfOJPn8gNSQzWZY8/PII+sVCWD6EzIAhgEcvqt2UgzOYJ1O6bzkOJ5AgMBAAEC"
				+ "gYAsObQkmyEXJAppag1286JRKkU5F9UfffwJciIVn1tCLv7yiTJbadnLtEWVlBd2MIIdBpeA9ex3"
				+ "IcI9np8Myons3L/wXUN6aPfOQq58Qc0Xd3ZAjnkA2iVNPwpIEr5XLKIrX4aNDnRngBIxmknrLL6v"
				+ "ZUE+rS4s6CsUjQ9yG4d2EQJBAMZkEjqAApjcFWfgBgT6LxxDwWIbEBim9vwcdP+BecMAjU3tQa16"
				+ "JEZBONQnss3RaEJ0W8HUn2OLalu36HkfTt8CQQC5VYokv5FBEpSvdcWKzZrEclUUTXwsza+cCYJa"
				+ "EvE9v+RNd+r6nCUDAGHLxMtrEgkrE20pqReN3bukwRpwLHGnAkAy/Ib+x+Vi+bT6pEWHw/CVmAg8"
				+ "OW5Sl56EPqAHBnSPnDW0oFQvzGNENwDu7WDzqmzcH2FxmD56a9sixUoWzugLAkBQrNulg35HRT9T"
				+ "4YBMG5PzT5GZdOFI34BB/CGx8+zvZEiNMFYpIS87Tz9C5DdoNEGpbptmNyT5rDuyTBymF7KhAkAC"
				+ "Iqa/Bsi7PzGq2A68TObxoPw6PYp8d06n2vtfH4l92K1kWdRU4upg4WQ4uc57TfsJ6+UVOEXmOtHD" 
				+ "QFK7Zcd9";
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_remove)
	public void afterRemove(HttpServletRequest request, AssessProject bean){
		String[] ids=bean.getIds().split(",");
		for(String i : ids){
			Integer id=Integer.parseInt(i);
			//删除考核项目后，删除 考核对应题目中间表 对应数据
			AssessProjectOptionExample example=new AssessProjectOptionExample();
			example.createCriteria().andProjectIdEqualToIgnoreNull(id);
			projectOptionMapper.deleteByExample(example);
			//删除考核项目后，删除 明细表 的对应数据
			AssessContentDetailExample contentDetailExample=new AssessContentDetailExample();
			contentDetailExample.createCriteria().andProjectIdEqualToIgnoreNull(id);
			contentDetailMapper.deleteByExample(contentDetailExample);
			//删除考核项目后，删除 针对某个人提交考核结果表 的对应数据
			AssessContentListExample contentListExample=new AssessContentListExample();
			contentListExample.createCriteria().andProjectIdEqualToIgnoreNull(id);
			contentListMapper.deleteByExample(contentListExample);
			//删除考核项目后，删除 考核结果表 的对应数据
			AssessContentExample contentExample=new AssessContentExample();
			contentExample.createCriteria().andProjectIdEqualToIgnoreNull(id);
			contentMapper.deleteByExample(contentExample);
		}
		
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_save)
	public void afterSave(HttpServletRequest request, AssessProject bean) {
		bean=this.get(bean.getId());
		bean.setEnableStatus("0");
		try {
			this.update(request, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
