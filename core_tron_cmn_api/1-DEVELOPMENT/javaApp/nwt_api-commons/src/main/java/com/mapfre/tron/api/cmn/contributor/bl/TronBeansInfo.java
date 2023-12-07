package com.mapfre.tron.api.cmn.contributor.bl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class TronBeansInfo implements ApplicationContextAware {

    private static final String API = "_api_";
    private static final String API_BE = "_api_be";
    ApplicationContext currentContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	currentContext = applicationContext;

    }

    public TronInfoBeansStats invoke() {
	TronInfoBeansStats stats = new TronInfoBeansStats();

	// Add local stats
	stats.setTronLocalServicesStats(getTronLocalServicesStats());
	// Add custom stats
	stats.setTronNamingBeansStats(getTronNamingBeansStats());
	// Add quality stats
	stats.setTronCustomMethodsStats(getTronCustomMethodsStats());
	return stats;
    }

    private TronLocalServicesStats getTronLocalServicesStats() {

	RequestMappingHandlerMapping requestMappingHandlerMapping = currentContext
		.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
	Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

	ArrayList<ServiceInfo> core = new ArrayList<>();
	ArrayList<ServiceInfo> local = new ArrayList<>();
	int localCount = 0;
	int coreCount = 0;
	// Recorrer objeto handlerMethods para contar servicios de core y locales
	for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
	    RequestMappingInfo requestMappingInfo = entry.getKey();
	    HandlerMethod handlerMethod = entry.getValue();

	    // Obtener el nombre del paquete
	    // Obtener el nombre del jar
	    String jarName = handlerMethod.getBeanType().getProtectionDomain().getCodeSource().getLocation().getPath();
	    List<String> urlPatterns = new ArrayList<>(requestMappingInfo.getPatternsCondition().getPatterns());

	    ServiceInfo serviceInfo = new ServiceInfo(handlerMethod.getMethod().getName(), urlPatterns.toString());
	    
	    jarName = getJarName(jarName);
	    
	    handlerMethod.getShortLogMessage();
	    if (jarName.contains(API_BE) || jarName.contains("nwt_api-commons")
		    || jarName.contains("nwt_cmn_api-") && !jarName.contains("-gdc")) {
		core.add(serviceInfo);
		coreCount++;
	    } else if (jarName.contains("_be")) {
		local.add(serviceInfo);
		localCount++;
	    }

	}

	TronLocalServicesStats localStats = new TronLocalServicesStats();
	localStats.setLocalServicesCount(localCount);
	localStats.setCoreServicesCount(coreCount);
	localStats.setPercentage(Math.round(((Float.valueOf(localStats.getLocalServicesCount()) / (Float.valueOf(localStats.getCoreServicesCount())
		+ Float.valueOf(localStats.getLocalServicesCount()))) * 100) * 100f) / 100f);
	localStats.setCore(core);
	localStats.setLocal(local);
	return localStats;
    }

    private String getJarName(String jarName) {
	if (FilenameUtils.getName(jarName) != null && !FilenameUtils.getName(jarName).isEmpty()) {
	    jarName = FilenameUtils.getName(jarName);
	} else {
	    int jarExtensionIndex = jarName.lastIndexOf(".jar");
	    int lastSlashIndex = jarName.lastIndexOf('/', jarExtensionIndex);

	    if (jarExtensionIndex != -1 && lastSlashIndex != -1) {
		jarName = jarName.substring(lastSlashIndex + 1, jarExtensionIndex + 4);
	    } else {
		int index = jarName.indexOf("DEVELOPMENT");
		if (index != -1) {
		    jarName = jarName.substring(index + "DEVELOPMENT".length());
		}
	    }
	}
	return jarName;
    }

    private TronNamingBeansStats getTronNamingBeansStats() {
	TronNamingBeansStats stats = new TronNamingBeansStats();
	stats.setMalformedBeans(new ArrayList<>());
	stats.setCustomBeansMalformedCount(0);
	stats.setCustomBeansCount(0);
	Set<ConfigurableApplicationContext> contexts = new LinkedHashSet<>();
	ApplicationContext context = currentContext;

	while (context != null) {
	    if (context instanceof ConfigurableApplicationContext) {
		contexts.add((ConfigurableApplicationContext) context);
	    }
	    context = context.getParent();
	}

	for (ConfigurableApplicationContext ctx : contexts) {
	    ConfigurableListableBeanFactory bf = ctx.getBeanFactory();
	    String[] beanNames = bf.getBeanDefinitionNames();

	    for (String beanName : beanNames) {


		Class<?> beanClass = bf.getType(beanName);
		String packageName = null;
		String jarName = null;
		if (beanClass != null) {
		    try {
			packageName = beanClass.getPackage().getName();
			jarName = beanClass.getProtectionDomain().getCodeSource().getLocation().getPath();
			jarName = FilenameUtils.getName(jarName);
		    } catch (Exception e) {
			//do nothing
		    }
		}

		if (jarName != null) {

		    TronNamingBeanInfo beanInfo = new TronNamingBeanInfo();
		    String codigoPais = null;
		    if (jarName != null && !jarName.contains(API_BE)) {
			codigoPais = obtenerCodigoPais(jarName);
		    }
		    boolean error = false;
		    if (codigoPais != null && jarName.contains(API + codigoPais + "_be")) {

			if (beanClass.getName().contains("$$")) {
			    beanInfo.setClassName(beanClass.getName().substring(0, beanClass.getName().indexOf("$$")));
			} else {
			    beanInfo.setClassName(beanClass.getName());
			}
			if (codigoPais != null && !packageName.contains("com.mapfre.tron." + codigoPais + ".api")) {
			    error = true;
			    beanInfo.getErrors().add(
				    "Error de paquetería: no tiene el formato adecuado com.mapfre.tron.{COD_PAIS}.api.*.{CAPA}");
			}
			if (jarName.contains("sr-impl")) {
			    stats.setCustomBeansCount(stats.getCustomBeansCount() + 1);
			    if (!esImplementacionController(beanClass)) {
				error = true;
				beanInfo.getErrors()
					.add("Error de nomenclatura: el nombre de la clase no termina en 'Controller'"
						+ jarName);
			    }
			} else if (jarName.contains("bl") && !jarName.contains("impl")) {
			    stats.setCustomBeansCount(stats.getCustomBeansCount() + 1);
			    if (!esInterfazBL(beanClass)) {
				error = true;
				beanInfo.getErrors()
					.add("Error de nomenclatura: el nombre de la clase no empieza por IBl");
			    }
			} else if (jarName.contains("bl") && jarName.contains("impl")) {
			    stats.setCustomBeansCount(stats.getCustomBeansCount() + 1);
			    if (!esImplementacionBL(beanClass)) {
				error = true;
				beanInfo.getErrors().add(
					"Error de nomenclatura: el nombre de la clase no empieza por Bl o TwBl o NwtBl");
			    }
			} else if (jarName.contains("dl") && !jarName.contains("impl")) {
			    stats.setCustomBeansCount(stats.getCustomBeansCount() + 1);
			    if (!esInterfazDL(beanClass)) {
				error = true;
				beanInfo.getErrors()
					.add("Error de nomenclatura: el nombre de la clase no empieza por IDl");
			    }
			} else if (jarName.contains("dl") && jarName.contains("impl")) {
			    stats.setCustomBeansCount(stats.getCustomBeansCount() + 1);
			    if (!esImplementacionDL(beanClass)) {
				error = true;
				beanInfo.getErrors()
					.add("Error de nomenclatura: el nombre de la clase no empieza por Dl");
			    }
			}

		    }

		    if (error) {
			stats.getMalformedBeans().add(beanInfo);
			stats.setCustomBeansMalformedCount(stats.getCustomBeansMalformedCount() + 1);
		    }
		}
	    }
	}

	if (!Float.valueOf(stats.getCustomBeansCount()).equals(Float.valueOf(0))) {

	    stats.setPercentage(Math.round(((Float.valueOf(Float.valueOf(stats.getCustomBeansCount()) - stats.getCustomBeansMalformedCount())
		    / Float.valueOf(stats.getCustomBeansCount())) * 100) * 100f) / 100f);
	} else {
	    stats.setPercentage(Float.valueOf(0));
	}

	return stats;
    }

    private TronCustomMethodsStats getTronCustomMethodsStats() {
	TronCustomMethodsStats tronCustomBeansStats = new TronCustomMethodsStats();

	Set<ConfigurableApplicationContext> contexts = new LinkedHashSet<>();
	ApplicationContext context = currentContext;

	while (context != null) {
	    if (context instanceof ConfigurableApplicationContext) {
		contexts.add((ConfigurableApplicationContext) context);
	    }
	    context = context.getParent();
	}

	for (ConfigurableApplicationContext ctx : contexts) {
	    ConfigurableListableBeanFactory bf = ctx.getBeanFactory();
	    String[] beanNames = bf.getBeanDefinitionNames();

	    for (String beanName : beanNames) {


		Class<?> beanClass = bf.getType(beanName);
		if (beanClass != null) {
		    Package beanPackage = beanClass.getPackage();
		    String jarName = null;
		    
		    if (!beanClass.getName().contains("$$") && beanPackage != null
			    && beanPackage.getName().startsWith("com.mapfre.tron")) {

			try {
			    jarName = beanClass.getProtectionDomain().getCodeSource().getLocation().getPath();
			    jarName = FilenameUtils.getName(jarName);
			} catch (Exception e) {
			    // do nothing
			}

			if (jarName != null) {
			    String codigoPais = null;
			    if (jarName != null && !jarName.contains(API_BE)) {
				codigoPais = obtenerCodigoPais(jarName);
			    }
			    Method[] methods = beanClass.getDeclaredMethods();
			    for (Method method : methods) {
				if (method.isBridge() || method.isSynthetic()) {
				    continue;
				}
				if (codigoPais != null && jarName.contains(API + codigoPais)
				    && isOverriddenMethod(method)) {
					tronCustomBeansStats.getCustomMethods().add(method.toString());
				    
				} else {
				    tronCustomBeansStats.getCoreMethods().add(method.toString());
				}
			    }

			}
		    }

		}
	    }
	    if (tronCustomBeansStats.getCustomMethodsCount() != 0 && tronCustomBeansStats.getCoreMethodsCount() != 0) {

		tronCustomBeansStats.setPercentage(Math.round(
			(Float.valueOf(tronCustomBeansStats.getCustomMethodsCount())
			/ (Float.valueOf(tronCustomBeansStats.getCoreMethodsCount()) + Float.valueOf(tronCustomBeansStats.getCustomMethodsCount()))
			* 100) * 100f) / 100f);

	    } else {

		tronCustomBeansStats.setPercentage(0f);

	    }
	}
	return tronCustomBeansStats;
    }

    private static boolean isOverriddenMethod(Method method) {

	try {
	    Class clazz = method.getDeclaringClass();
	    Class clazz2 = method.getClass();
	    return !clazz.equals(clazz2);
	} catch (Exception e) {
	    // do nothing
	}

	return false;
    }

    private String obtenerCodigoPais(String jarName) {
	if (jarName.contains(API)) {
	    int startIndex = jarName.indexOf(API) + 5;
	    int endIndex = startIndex + 2;
	    if (endIndex <= jarName.length()) {
		return jarName.substring(startIndex, endIndex);
	    }
	}
	return null;
    }

    private boolean esImplementacionController(Class<?> beanClass) {
	String className = beanClass.getSimpleName();
	return className.endsWith("Controller");
    }

    private boolean esInterfazBL(Class<?> beanClass) {
	String className = beanClass.getSimpleName();
	return className.startsWith("IBl");
    }

    private boolean esImplementacionBL(Class<?> beanClass) {
	String className = beanClass.getSimpleName();
	return className.startsWith("Bl") || className.startsWith("TwBl") || className.startsWith("NwtBl");
    }

    private boolean esInterfazDL(Class<?> beanClass) {
	String className = beanClass.getSimpleName();
	return className.startsWith("IDl");
    }

    private boolean esImplementacionDL(Class<?> beanClass) {
	String className = beanClass.getSimpleName();
	return className.startsWith("Dl") || className.startsWith("TwDl") || className.startsWith("NwtDl");
    }

}
