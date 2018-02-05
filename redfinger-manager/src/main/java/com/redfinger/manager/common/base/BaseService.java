package com.redfinger.manager.common.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.helper.SpringContextHolder;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.Reflections;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;

/**
 * @author PennTom
 *
 * @param <T> domain
 * @param <E> example
 * @param <K> mapper
 */
@Transactional
@Service
@SuppressWarnings({"unchecked","null"})
public class BaseService<T extends Serializable, E extends Object, K extends Object> {

	protected static Logger log = LoggerFactory.getLogger(BaseService.class);
	private Object mapper;
	private static ThreadLocal<Object> localExample = new ThreadLocal<Object>();
	public static ThreadLocal<Map<String,Object>> cache=new ThreadLocal<Map<String,Object>>();
	

	/******************/
	/**** 基础方法开始 *****/
	/******************/

	/**
	 * 每个select查询之前必须调用此方法
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public BaseService<T, E, K> initQuery(BaseDomain bean)  {
		if (bean == null) {
			try {
				bean = bean.getClass().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("初始化查询出错");
			} 
		}
		localExample.remove();
		localExample.set(this.createExample());
		return this;
	}
	public BaseService<T, E, K> initQuery()  {
		localExample.remove();
		localExample.set(this.createExample());
		return this;
	}

	/**
	 * 获取example,多例
	 * 
	 * @return
	 */
	protected Object createExample() {
		Object example = null;
		Class<K> clazz = (Class<K>) Reflections.getClassGenricType(getClass(), 1);
		try {
			example = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建example失败:[" + clazz.getName() + "]");
		}
		return example;
	}

	/**
	 * 获取criteria,一个example下单例
	 * 
	 * @param example
	 * @return
	 */
	protected Object getCriteria() {
		if (Reflections.invokeMethodByName(localExample.get(), "getOredCriteria", null) != null) {
			List<Object> list = (List<Object>) Reflections.invokeMethodByName(localExample.get(), "getOredCriteria", null);
			if (!CollectionUtils.isEmpty(list)) {
				return list.get(0);
			}
		}
		return Reflections.invokeMethodByName(localExample.get(), "createCriteria", null);
	}

	/**
	 * 获取mapper，单例
	 * 
	 * @return
	 */
	public Object getMapper() {
		if (this.mapper == null) {
			Class<K> clazz = (Class<K>) Reflections.getClassGenricType(getClass(), 2);
			Object mapperCopy = SpringContextHolder.getBean(clazz);
			this.mapper = mapperCopy;
		}
		return this.mapper;
	}
	
	/**
	 * 获取example，必须初始化
	 * @return
	 */
	public Object getExample(){
		return localExample.get();
	}
	
	
	protected Class<K> getDomainClass(){
		Class<K> clazz = (Class<K>) Reflections.getClassGenricType(getClass(), 0);
		return clazz;
	}
	

	/******************/
	/**** 查询方法开始 *****/
	/******************/

	/**
	 * 根据主键查询实体
	 * 
	 * @param id
	 * @return
	 */
	public T get(Object id) {
		return (T) this.getById(this.getMapper(), new Object[] { id });
	}
	
	public T load(Object id){
		String key=this.getDomainClass().getName()+"@"+id.toString();
		if(cache.get()==null){
			Map<String,Object> cacheMap = Maps.newHashMap();
			cache.set(cacheMap);
		}
		Object obj=cache.get().get(key);
		if(obj!=null){
			return (T)obj;
		}else{
			T t = this.get(id);
			cache.get().put(key, t);
			return t;
		}
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.findByExample();
	}

	/**
	 * 分页查询所有
	 * 
	 * @return
	 */
	public List<T> pageAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.findByExample();
	}
	
	/**
	 * 计数所有
	 * @return
	 */
	public int countAll() {
		return this.countByExample();
	}
	
	/**
	 * 查询一条
	 * 
	 * @return
	 */
	public List<T> singleAll() {
		PageHelper.startPage(1, 1);
		return this.findByExample();
	}
	

	/**
	 * 查询所有未删除数据
	 * 
	 * @return
	 */
	public List<T> findDelTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		return this.findByExample();
	}

	/**
	 * 分页查询所有未删除数据
	 * 
	 * @return
	 */
	public List<T> pageDelTrue(int pageNum, int pageSize) {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		PageHelper.startPage(pageNum, pageSize);
		return this.findByExample();
	}
	
	/**
	 * 计数所有未删除数据
	 * 
	 * @return
	 */
	public int countDelTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		return this.countByExample();
	}
	
	/**
	 * 查询一条所有未删除数据
	 * 
	 * @return
	 */
	public List<T> singleDelTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		PageHelper.startPage(1, 1);
		return this.findByExample();
	}

	/**
	 * 查询所有正常数据
	 * 
	 * @return
	 */
	public List<T> findStopTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		this.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		return this.findByExample();
	}

	/**
	 * 分页查询所有正常数据
	 * 
	 * @return
	 */
	public List<T> pageStopTrue(int pageNum, int pageSize) {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		this.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		PageHelper.startPage(pageNum, pageSize);
		return this.findByExample();
	}
	
	/**
	 * 计数所有正常数据
	 * 
	 * @return
	 */
	public int countStopTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		this.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		return this.countByExample();
	}
	
	/**
	 * 查询一条所有正常数据
	 * 
	 * @return
	 */
	public List<T> singleStopTrue() {
		this.andEqualTo("status", ConstantsDb.globalStatusNomarl());
		this.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		PageHelper.startPage(1, 1);
		return this.findByExample();
	}

	/**** 加入查询参数 ****/

	public BaseService<T, E, K> andEqualTo(String field, Object value) {
		return this.addQueryParam("EqualTo", field, value);
	}

	public BaseService<T, E, K> andNotEqualTo(String field, Object value) {
		return this.addQueryParam("NotEqualTo", field, value);
	}

	public BaseService<T, E, K> andGreaterThan(String field, Object value) {
		return this.addQueryParam("GreaterThan", field, value);
	}

	public BaseService<T, E, K> andGreaterThanOrEqualTo(String field, Object value) {
		return this.addQueryParam("GreaterThanOrEqualTo", field, value);
	}

	public BaseService<T, E, K> andLessThan(String field, Object value) {
		return this.addQueryParam("LessThan", field, value);
	}

	public BaseService<T, E, K> andLessThanOrEqualTo(String field, Object value) {
		return this.addQueryParam("LessThanOrEqualTo", field, value);
	}

	public BaseService<T, E, K> andLike(String field, String value) {
		return this.addQueryParam("Like", field, StringUtils.isBlank(value) ? value : "%" + value + "%");
	}

	public BaseService<T, E, K> andLikePrefix(String field, String value) {
		return this.addQueryParam("Like", field, StringUtils.isBlank(value) ? value : "%" + value);
	}

	public BaseService<T, E, K> andLikeSuffix(String field, String value) {
		return this.addQueryParam("Like", field, StringUtils.isBlank(value) ? value : value + "%");
	}

	public BaseService<T, E, K> andIsNull(String field) {
		Reflections.invokeMethodByName(this.getCriteria(), "and" + StringUtils.firstCharUpper(field) + "IsNull", null);
		return this;
	}

	public BaseService<T, E, K> andIsNotNull(String field) {
		Reflections.invokeMethodByName(this.getCriteria(), "and" + StringUtils.firstCharUpper(field) + "IsNotNull", null);
		return this;
	}

	public BaseService<T, E, K> andIn(String field, Object[] value) {
		return this.addQueryParam("In", field, Lists.newArrayList(value));
	}
	
	public BaseService<T, E, K> andIn(String field, List<?> list) {
		return this.addQueryParam("In", field, list);
	}

	public BaseService<T, E, K> andNotIn(String field, Object[] value) {
		return this.addQueryParam("NotIn", field, Lists.newArrayList(value));
	}
	public BaseService<T, E, K> andNotIn(String field, List<?> list) {
		return this.addQueryParam("NotIn", field, list);
	}

	/**** 加入排序参数,累加 ****/
	public BaseService<T, E, K> addOrderClause(String field, String type) {
		Object example = localExample.get();
		Object order = Reflections.invokeMethodByName(example, "getOrderByClause", null);
		field=StringUtils.toUnderScoreCase(field);
		if (order == null) {
			Reflections.invokeMethodByName(example, "setOrderByClause", new Object[] { field + " " + type });
		} else {
			Reflections.invokeMethodByName(example, "setOrderByClause", new Object[] { order.toString() + " , " + field + " " + type });
		}
		return this;
	}
	/**** 加入排序参数，强制排序 ****/
	public BaseService<T, E, K> addOrderForce(String field, String type) {
		if(StringUtils.isNoneBlank(field,type)){
			Object example = localExample.get();
			Reflections.invokeMethodByName(example, "setOrderByClause", new Object[] { StringUtils.toUnderScoreCase(field) + " " + type });
		}
		return this;
	}
	

	/******************/
	/*** 持久化方法开始 ****/
	/******************/

	/**
	 * 保存
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void save(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_save, this, request, bean);

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		this.setDefaultValueToField(bean, "enableStatus", ConstantsDb.globalEnableStatusNomarl());
		this.setDefaultValueToField(bean, "status", ConstantsDb.globalStatusNomarl());
		this.setDefaultValueToField(bean, "createTime", currentDate);
		this.setDefaultValueToField(bean, "creater", userId);
		this.insert(this.getMapper(), new Object[] { bean });
		
		this.executeAopMethod(ServiceMethod.after_save, this, request, bean);
	}
	
	/**
	 * 更新
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void update(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_update, this, request, bean);

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		this.setDefaultValueToField(bean, "modifyTime", currentDate);
		this.setDefaultValueToField(bean, "modifier", userId);
		this.update(this.getMapper(), new Object[]{bean});
		
		this.executeAopMethod(ServiceMethod.after_update, this, request, bean);
	}

	/**
	 * 启用数据
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void start(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_start, this, request, bean);
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			Object objectBean=bean.getClass().newInstance();
			this.setDefaultValueToField(objectBean, pk, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
			this.setDefaultValueToField(objectBean, "enableStatus", ConstantsDb.globalEnableStatusNomarl());
			this.setDefaultValueToField(objectBean, "modifyTime", currentDate);
			this.setDefaultValueToField(objectBean, "modifier", userId);
			this.update(this.getMapper(), new Object[] { objectBean });
		}
		this.executeAopMethod(ServiceMethod.after_start, this, request, bean);
	}

	/**
	 * 禁用数据
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void stop(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_stop, this, request, bean);

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			Object objectBean=bean.getClass().newInstance();
			this.setDefaultValueToField(objectBean, pk, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
			this.setDefaultValueToField(objectBean, "enableStatus", ConstantsDb.globalEnableStatusStop());
			this.setDefaultValueToField(objectBean, "modifyTime", currentDate);
			this.setDefaultValueToField(objectBean, "modifier", userId);
			this.update(this.getMapper(), new Object[] { objectBean });
		}

		this.executeAopMethod(ServiceMethod.after_stop, this, request, bean);
	}

	/**
	 * 逻辑删除数据
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_delete, this, request, bean);

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			Object objectBean=bean.getClass().newInstance();
			this.setDefaultValueToField(objectBean, pk, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
			this.setDefaultValueToField(objectBean, "status", ConstantsDb.globalStatusDelete());
			this.setDefaultValueToField(objectBean, "modifyTime", currentDate);
			this.setDefaultValueToField(objectBean, "modifier", userId);
			this.update(this.getMapper(), new Object[] { objectBean });
		}

		this.executeAopMethod(ServiceMethod.after_delete, this, request, bean);
	}

	/**
	 * 彻底物理删除数据，中间表使用，实体表不可使用
	 * 
	 * @param request
	 * @param id
	 * @throws Exception
	 */
	public void remove(HttpServletRequest request, Object id) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_remove_single, this, request, id);
		this.remove(this.getMapper(), new Object[] { id });
		this.executeAopMethod(ServiceMethod.after_remove_single, this, request, id);
	}
	
	public void remove(HttpServletRequest request, BaseDomain bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_remove, this, request, bean);
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			this.remove(request, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
		}
		this.executeAopMethod(ServiceMethod.after_remove, this, request, bean);
	}

	/******************/
	/**** 辅助方法开始 *****/
	/******************/

	/**
	 * 判断当前domain是否含有某个属性
	 * 
	 * @param name
	 * @return
	 */
//	private boolean hasField(String name) {
//		Class<T> clazz = (Class<T>) Reflections.getClassGenricType(getClass(), 0);
//		Field field = null;
//		try {
//			field = clazz.getDeclaredField(name);
//		} catch (Exception e) {
//		}
//		return field == null ? false : true;
//	}


	/**
	 * 强制填充value到domain的name字段
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 */
	private void setDefaultValueToField(Object bean, String name, Object value) {
		if (value != null && Reflections.getAccessibleField(bean, name) != null) {
			Reflections.setFieldValue(bean, name, value);
		}
	}

	/**
	 * 执行AOP方法
	 * 
	 * @param serviceMethod
	 * @param obj
	 * @param request
	 * @param bean
	 */
	private void executeAopMethod(ServiceMethod serviceMethod, Object obj, HttpServletRequest request, Object bean) {
		Method method = Reflections.getAnnotationMethod(this, serviceMethod);
		if (method != null) {
			Object result = null;
			try {
				result = method.invoke(this, request, bean);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (result != null) {
				throw new BusinessException(result.toString());
			}
		}
	}

	private BaseService<T, E, K> addQueryParam(String operate, String field, Object value) {
		if (value != null) {
			if(value instanceof String){
				if(StringUtils.isNotBlank(value.toString())){
					Reflections.invokeMethodByName(this.getCriteria(), "and" + StringUtils.firstCharUpper(field) + operate, new Object[] { value });
				}
			}else if(value instanceof Collection<?>){
				if(!Collections3.isEmpty((Collection<?>)value)){
					Reflections.invokeMethodByName(this.getCriteria(), "and" + StringUtils.firstCharUpper(field) + operate, new Object[] { value });
				}
			}else{
				Reflections.invokeMethodByName(this.getCriteria(), "and" + StringUtils.firstCharUpper(field) + operate, new Object[] { value });
			}
		}
			
		return this;
	}

	/**
	 * 新增
	 * 
	 * @param mapper
	 * @param args
	 */
	private void insert(final Object mapper, final Object[] args) {
		Reflections.invokeMethodByName(mapper, "insertSelective", args);
	}

	/**
	 * 修改
	 * 
	 * @param mapper
	 * @param args
	 */
	private void update(final Object mapper, final Object[] args) {
		Reflections.invokeMethodByName(mapper, "updateByPrimaryKeySelective", args);
	}

	/**
	 * 删除
	 * 
	 * @param mapper
	 * @param args
	 */
	private void remove(final Object mapper, final Object[] args) {
		try{
			Reflections.invokeMethodByName(mapper, "deleteByPrimaryKey", args);
		}catch (Exception e){
			if(e.getMessage().contains("foreign key constraint")){
				throw new BusinessException("外键冲突，不能删除!");
			}else{
				throw e;
			}
		}
		
	}

	@Transactional(readOnly = true)
	private T getById(final Object mapper, final Object[] args) {
		return (T) Reflections.invokeMethodByName(mapper, "selectByPrimaryKey", args);
	}

	/**
	 * 所有查询最终调用此方法
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	private List<T> findByExample() {
		List<T> list = (List<T>) Reflections.invokeMethodByName(this.getMapper(), "selectByExample", new Object[] { localExample.get() });
		localExample.remove();
		return list;
	}
	
	@Transactional(readOnly = true)
	private int countByExample() {
		int count = (int) Reflections.invokeMethodByName(this.getMapper(), "countByExample", new Object[] { localExample.get() });
		localExample.remove();
		return count;
	}
	
	/**
	 * 将一个list按照一定数量分割成多个
	 * @param targe
	 * @param size
	 * @return
	 */
	public static List<List<String>>  createList(List<String> targe,int size) {  
        List<List<String>> listArr = new ArrayList<List<String>>();  
        //获取被拆分的数组个数  
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;  
        for(int i=0;i<arrSize;i++) {  
            List<String>  sub = new ArrayList<String>();  
            //把指定索引数据放入到list中  
            for(int j=i*size;j<=size*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
    } 
	
	/**
	 * 判断数据是否重复
	 * @param list
	 * @return
	 */
	public boolean isRepeat(List<String> list){
          
        String temp = "";
        for (int i = 0; i < list.size() - 1; i++)
        {
            temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++)
            {
                if (temp.equals(list.get(j)))
                {
                    return true;
                }
            }
        }
        return false;
	}

	/**
	 * 判断数据是否重复
	 * @param list
	 * @return
	 */
	public List<String> cleanRepeat(List<String> li){
        List<String> list = new ArrayList<String>();
        for(int i=0; i<li.size(); i++){
            String str = li.get(i);  //获取传入集合对象的每一个元素
            if(!list.contains(str)){   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
    }
}
