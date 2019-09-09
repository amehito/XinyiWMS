package com.xinyi.test;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

public class JSONTree {
	private String value;
	private JSONTree[] child;
	static final String name = "text";
	static final String children = "nodes";
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public JSONTree[] getChild() {
		return child;
	}
	public void setChild(JSONTree[] child) {
		this.child = child;
	}

	
	@Test
	public void test() {
		JSONTree jSONTree = new JSONTree();
		JSONTree child = new JSONTree();
		child.setValue("直流马达");
		JSONTree child1 = new JSONTree();
		child1.setValue("电动马达");
		jSONTree.setValue("马达");
		jSONTree.setChild(new JSONTree[] {child,child1});
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		toJson(builder, jSONTree);
		builder.append("]");
		System.out.println(builder.toString());
	}
	public void toJson(StringBuilder builder,JSONTree tree) {
		
		builder.append("{"+name+":\""+tree.value+"\"");
		System.out.println(builder.toString());
		
		if(tree.child == null) {
			builder.append("}");
			return ;
			
		}
		
			
		builder.append(","+children+":[");
		System.out.println(builder.toString());
		for(int i = 0;i<tree.child.length;i++) {
			
			toJson(builder,tree.child[i] );
			if(i!=tree.child.length-1)
				builder.append(",");
			
		}
		builder.append("]");
		builder.append("}");
		System.out.println(builder.toString());
		
		System.out.println(builder.toString());
		
	}
}
