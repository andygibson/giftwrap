package org.fluttercode.giftwrap.elements;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fluttercode.giftwrap.AbstractArchiveElement;
import org.fluttercode.giftwrap.DeploymentContext;

public class ClassElement extends AbstractArchiveElement {

	public enum DependencyType {
		FIELDS, INTERFACES, ANCESTORS, METHODS, CUSTOM
	};

	private boolean recursive;

	private static List<String> globallyExemptClasses = new ArrayList<String>();

	private List<Class<?>> exemptions = new ArrayList<Class<?>>();

	static {
		globallyExemptClasses.add(Double.class.getName());
		globallyExemptClasses.add(Integer.class.getName());
		globallyExemptClasses.add(Boolean.class.getName());
		globallyExemptClasses.add(Object.class.getName());
		globallyExemptClasses.add(Date.class.getName());
		globallyExemptClasses.add(BigDecimal.class.getName());
		globallyExemptClasses.add(Number.class.getName());
		globallyExemptClasses.add("int");
	}

	private Class<? extends Object> clazz;

	private Set<DependencyType> dependencyTypes = new HashSet<DependencyType>();

	public ClassElement(Class<? extends Object> clazz, boolean recursive) {
		this.clazz = clazz;
		this.recursive = recursive;
	}

	public ClassElement(Class<? extends Object> clazz) {
		this(clazz, true);
	}

	public Set<DependencyType> getDependencyTypes() {
		return dependencyTypes;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		List<Class<?>> classesAdded = new ArrayList<Class<?>>();
		addClass(clazz, classesAdded);

		for (Class<?> c : classesAdded) {
			context.addClass(c);
		}
	}

	public boolean includeDependency(DependencyType type) {
		return dependencyTypes.contains(type) || dependencyTypes.isEmpty();

	}

	public List<Class<?>> getExemptions() {
		return exemptions;
	}

	private void addClass(Class<?> classToAdd, List<Class<?>> classesAdded) {

		if (!classesAdded.contains(classToAdd)
				&& !exemptions.contains(classToAdd)
				&& !globallyExemptClasses.contains(classToAdd.getName())) {

			classesAdded.add(classToAdd);
			if (recursive) {
				addDependencies(classToAdd, classesAdded);
			}
		}
	}

	private void addDependencies(Class<?> originClass,
			List<Class<?>> classesAdded) {
		if (includeDependency(DependencyType.FIELDS)) {
			addFieldDependencies(originClass, classesAdded);
		}

		if (includeDependency(DependencyType.ANCESTORS)) {
			addSuperClassDependencies(originClass, classesAdded);
		}

		if (includeDependency(DependencyType.INTERFACES)) {
			addInterfaces(originClass, classesAdded);
		}

		if (includeDependency(DependencyType.METHODS)) {
			addMethodDependencies();
		}

		if (includeDependency(DependencyType.CUSTOM)) {
			doCustomDependencies();
		}
	}

	/**
	 * override to add addition dependency management code
	 */
	protected void doCustomDependencies() {
		// do nothing here
	}

	private void addMethodDependencies() {

	}

	private void addInterfaces(Class<?> originClass, List<Class<?>> classesAdded) {
		Class<?>[] interfaces = originClass.getInterfaces();
		for (Class<?> interfaceClass : interfaces) {
			addClass(interfaceClass, classesAdded);
		}
	}

	private void addSuperClassDependencies(Class<?> originClass,
			List<Class<?>> classesAdded) {
		Class<?> parent = originClass.getSuperclass();
		if (parent != null && parent != Object.class) {
			addClass(parent, classesAdded);
		}
	}

	private void addFieldDependencies(Class<?> originClass,
			List<Class<?>> classesAdded) {
		Field[] fields = originClass.getDeclaredFields();
		for (Field field : fields) {
			addClass(field.getType(), classesAdded);
		}
	}

}
