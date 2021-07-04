package com.my.model;

public class Person {
	private int age;
	private float height;
	private String name;

	public Person(int age, float height, String name) {
		super();
		this.age = age;
		this.height = height;
		this.name = name;
	}

	@Override
	// 计算索引时调用
	public int hashCode() {
		int ageHash = Integer.hashCode(age);
		int heightHash = Float.hashCode(height);
		int nameHash = name != null ? name.hashCode() : 0;

		int hashCode = (ageHash * 31 + heightHash) * 31 + nameHash;

		return hashCode;
	}

	@Override
	// 哈希冲突时，比较两个key是否相等
	public boolean equals(Object obj) {
		// 内存地址
		if (this == obj) {
			return true;
		}

		// 为null 或 类型不一样
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}

		// 比较成员变量
		Person person = (Person) obj;
		return person.age == age 
				&& person.height == height
				&& (person.name == null ? name == null : person.name.equals(name));
	}
}
