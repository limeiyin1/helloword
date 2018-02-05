package com.redfinger.manager.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.base.TreeDomain;


public class ListSortUtils {

//	/**
//	 * treeGrid使用
//	 * @param list 输出
//	 * @param sourcelist 输入
//	 * @param parentId 根节点
//	 */
//	public static <E> void sortList(List<E> list, List<E> source, String parentId,String pk,String parentKey) {
//		for(E current:source){
//			String currentParentId=Reflections.getFieldValue(current, parentKey).toString();
//			String currentId=Reflections.getFieldValue(current, pk).toString();
//			if(currentParentId.equals(parentId)){
//				list.add(current);
//				sortList(list, source, currentId,pk,parentKey);
//			}
//		}
//	}
	
	/**
	 * 获取当前记录所有父节点
	 * @param list
	 * @param source
	 * @param obj 当前对象
	 * @param pk
	 * @param parentKey
	 */
	public static <E> void getParentList(List<E> list, List<E> source, E obj,String pk,String parentKey){
		if(!list.contains(obj)){
			list.add(0, obj);
		}
		for(E current:source){
			Object currentId=Reflections.getFieldValue(current, pk);
			Object targetParentId=Reflections.getFieldValue(obj, parentKey);
			if(currentId.equals(targetParentId)){
				list.add(0, current);
				getParentList(list, source, current,pk,parentKey);
				break;
			}
		}
	}
	public static <E> void getChildList(List<E> list, List<E> source, E obj,String pk,String parentKey){
		if(!list.contains(obj)){
			list.add(obj);
		}
		for(E current:source){
			Object currentParentId=Reflections.getFieldValue(current, parentKey);
			Object targetId=Reflections.getFieldValue(obj, pk);
			if(currentParentId.equals(targetId)){
				list.add(current);
				getChildList(list, source, current,pk,parentKey);
			}
		}
	}
	
	
	public static <E> TreeDomain sortListToTree(List<E> source,String pk,String parentKey,String text){
		TreeDomain treeDomain=sortListToTree(source, null,pk,parentKey,text);
		return treeDomain;
	}
	
	private static <E> TreeDomain sortListToTree(List<E> source, TreeDomain domain,String pk,String parentKey,String text){
		if(domain==null){
			domain=getTop();
		}
		List<TreeDomain> children = new ArrayList<TreeDomain>();
		domain.setChildren(children);
		for(E current:source){
			String currentParentId=Reflections.getFieldValue(current, parentKey).toString();
			if(currentParentId.equals(domain.getId())){
				TreeDomain child = new TreeDomain();
				child.setIconCls("");
				child.setId(Reflections.getFieldValue(current, pk).toString());
				child.setText(Reflections.getFieldValue(current,text).toString());
				child.setChecked((boolean)Reflections.getFieldValue(current, "checked"));
				if(Reflections.getAccessibleField(current, "attributes")!=null){
					Object value=Reflections.getFieldValue(current, "attributes");
					if(value!=null){
						child.setAttributes(Reflections.getFieldValue(current, "attributes").toString());
					}
				}
				children.add(child);
				sortListToTree(source,child,pk, parentKey, text);
			}
		}
		return domain;
	}
	private static TreeDomain getTop(){
		TreeDomain treeDomain=new TreeDomain();
		treeDomain.setId("0");
		treeDomain.setText("[全部]");
		return treeDomain;
	}
	
}
